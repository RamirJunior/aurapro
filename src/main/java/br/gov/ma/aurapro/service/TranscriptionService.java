package br.gov.ma.aurapro.service;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class TranscriptionService {

    public String transcript(File audioFile) {
        // TODO chamada WHISPER
        System.out.println("Transcrevendo arquivo: " + audioFile.getAbsolutePath());

        // TODO: implementar chamada real ao whisper.cpp ou outra IA local
        return "Transcrição simulada do áudio: " + audioFile.getName();
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
