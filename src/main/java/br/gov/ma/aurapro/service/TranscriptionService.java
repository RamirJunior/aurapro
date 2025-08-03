package br.gov.ma.aurapro.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TranscriptionService {

//    public String transcript(File audioFile) {
//        // TODO chamada WHISPER
//        System.out.println("Transcrevendo arquivo: " + audioFile.getAbsolutePath());
//
//        // TODO: implementar chamada real ao whisper.cpp ou outra IA local
//        return "TRANSCRICAP do áudio: " + audioFile.getName() + "TExto em loren ipsum" +
//                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of de Finibus Bonorum et Malorum (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, Lorem ipsum dolor sit amet.., comes from a line in section 1.10.32.";
//    }


//    private static final String WHISPER_EXECUTABLE = "/home/juniorribeiro/whisper.cpp/build/bin/whisper-cli";
//    private static final String WHISPER_MODEL = "/home/juniorribeiro/whisper.cpp/models/ggml-tiny.bin";
//
//    public String transcript(File audioFile) {
//        try {
//            ProcessBuilder pb = new ProcessBuilder(WHISPER_EXECUTABLE, "-m", WHISPER_MODEL, "-f", audioFile.getAbsolutePath(), "-otxt");
//
//            pb.redirectErrorStream(true);
//            Process process = pb.start();
//
//            StringBuilder output = new StringBuilder();
//            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    output.append(line).append("\n");
//                }
//            }
//
//            int exitCode = process.waitFor();
//            if (exitCode != 0) {
//                throw new RuntimeException("Erro na transcrição: código de saída " + exitCode);
//            }
//
//            File transcriptFile = new File(audioFile.getParent(), getBaseName(audioFile) + ".txt");
//            return readFileContent(transcriptFile);
//
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException("Falha ao executar transcrição com whisper", e);
//        }
//    }
//
//    private String getBaseName(File file) {
//        String nome = file.getName();
//        return nome.substring(0, nome.lastIndexOf('.'));
//    }
//
//    private String readFileContent(File file) throws IOException {
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//            StringBuilder content = new StringBuilder();
//            String linha;
//            while ((linha = br.readLine()) != null) {
//                content.append(linha).append("\n");
//            }
//            return content.toString().trim();
//        }
//    }

    private final String SSH_USER = "juniorribeiro";
    private final String SSH_HOST = "192.168.8.175";
    private final String REMOTE_DIR = "/home/juniorribeiro/idox-temp";
    private final String WHISPER_CMD = "/home/juniorribeiro/whisper.cpp/build/bin/whisper-cli -m /home/juniorribeiro/whisper.cpp/models/ggml-tiny.bin -f"; // ajuste o caminho
    private final String WHISPER_LANG = "--language Portuguese";
    private final String SSH_IDENTITY_FILE = "/home/juniorribeiro/.ssh/id_rsa"; // chave privada

    public String transcript(File audioFile) {
        try {
            String token = UUID.randomUUID().toString();
            String remoteAudio = REMOTE_DIR + "/" + token + "-" + audioFile.getName();
            String remoteTxt = remoteAudio.replaceAll("\\.(mp3|wav|m4a|ogg)$", ".txt");



            ProcessBuilder builder = new ProcessBuilder(
                    "ssh", "-i", "C:/Users/User/.ssh/id_ed25519",
                    "juniorribeiro@192.168.8.175", "echo", "ping"
            );

            System.out.println("Usuário Java: " + System.getProperty("user.name"));

            builder.redirectErrorStream(true);
            Process pingSSH = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(pingSSH.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("RESPOSTA: " + line);
            }

            int exitCode = pingSSH.waitFor();
            System.out.println("Código de saída: " + exitCode);


            // 1. Copiar o arquivo para o servidor
            Process scpUp = new ProcessBuilder(
                    "scp", "-i", SSH_IDENTITY_FILE,
                    audioFile.getAbsolutePath(),
                    SSH_USER + "@" + SSH_HOST + ":" + remoteAudio
            ).start();
            if (scpUp.waitFor() != 0) throw new RuntimeException("Erro ao enviar arquivo");

            // 2. Rodar o whisper remotamente
            String command = WHISPER_CMD + " " + remoteAudio + " " + WHISPER_LANG +" -otxt";
            Process sshExec = new ProcessBuilder(
                    "ssh", "-i", SSH_IDENTITY_FILE,
                    SSH_USER + "@" + SSH_HOST, command
            ).start();


            // Thread para ler stdout
            Thread stdoutReader = new Thread(() -> {
                try (BufferedReader read = new BufferedReader(new InputStreamReader(sshExec.getInputStream()))) {
                    String lin;
                    while ((lin = read.readLine()) != null) {
                        System.out.println("[WHISPER STDOUT] " + lin);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Thread para ler stderr
            Thread stderrReader = new Thread(() -> {
                try (BufferedReader read = new BufferedReader(new InputStreamReader(sshExec.getErrorStream()))) {
                    String lin;
                    while ((lin = read.readLine()) != null) {
                        System.err.println("[WHISPER STDERR] " + lin);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            stdoutReader.start();
            stderrReader.start();

            int eCode = sshExec.waitFor();

            stdoutReader.join();
            stderrReader.join();

            if (eCode != 0) {
                throw new RuntimeException("Erro ao executar whisper");
            }

            if (sshExec.waitFor() != 0) throw new RuntimeException("Erro ao executar whisper");

            // Espera até o arquivo existir no servidor
            String waitForTxtCommand = "while [ ! -f \"" + remoteTxt + "\" ]; do sleep 1; done";
            Process waitForFile = new ProcessBuilder(
                    "ssh", "-i", SSH_IDENTITY_FILE,
                    SSH_USER + "@" + SSH_HOST, waitForTxtCommand
            ).start();
            if (waitForFile.waitFor() != 0) throw new RuntimeException("Erro ao aguardar criação do arquivo .txt");




            // 3. Copiar o .txt de volta
            File localTxt = File.createTempFile("transcricao-", ".txt");
            Process scpDown = new ProcessBuilder(
                    "scp", "-i", SSH_IDENTITY_FILE,
                    SSH_USER + "@" + SSH_HOST + ":" + remoteTxt,
                    localTxt.getAbsolutePath()
            ).start();
            if (scpDown.waitFor() != 0) throw new RuntimeException("Erro ao baixar transcrição");

            return Files.readString(localTxt.toPath());

        } catch (Exception e) {
            throw new RuntimeException("Falha na transcrição remota", e);
        }
    }

    private String readStream(InputStream input) throws IOException {
        return new BufferedReader(new InputStreamReader(input))
                .lines()
                .collect(Collectors.joining("\n"));
    }

}
