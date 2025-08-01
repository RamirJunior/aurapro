package br.gov.ma.aurapro.service;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class TranscriptionService {

    public String transcript(File audioFile) {
        // TODO chamada WHISPER
        System.out.println("Transcrevendo arquivo: " + audioFile.getAbsolutePath());

        // TODO: implementar chamada real ao whisper.cpp ou outra IA local
        return "TRANSCRICAP do áudio: " + audioFile.getName() + "TExto em loren ipsum" +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of de Finibus Bonorum et Malorum (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, Lorem ipsum dolor sit amet.., comes from a line in section 1.10.32.";
    }


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

}
