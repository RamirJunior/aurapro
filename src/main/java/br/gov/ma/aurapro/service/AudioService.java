package br.gov.ma.aurapro.service;

import br.gov.ma.aurapro.dtos.AudioResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AudioService {

    private final TranscriptionService transcriptionService;
    //private final IASummarizerService iaService;

    public AudioResponse process(@NotNull MultipartFile audioFile, boolean summarize){

        if (isSizeValid(audioFile)) {
            File tempAudioFile = saveFileTemporarily(audioFile);
            String audioTranscription = transcriptionService.transcript(tempAudioFile);

            Optional<String> summary = Optional.empty();
            if (summarize){
      //          summary = iaService.generateSummary(audioTranscription);
            }

            return new AudioResponse(audioTranscription, summary.orElse(""));
        } else {
            throw new RuntimeException("Arquivo de áudio maior que 10Mb.");
        }
    }

    private boolean isSizeValid(MultipartFile file) {
        long maxSize = 10 * 1024 * 1024; // 10MB
        return file.getSize() <= maxSize;
    }

    private File saveFileTemporarily(MultipartFile audioFile) {
        try {
            String tempFileName = UUID.randomUUID() + "-" + audioFile.getOriginalFilename();
            File tempLocal = new File(System.getProperty("java.io.tmpdir"), tempFileName);
            audioFile.transferTo(tempLocal);
            return tempLocal;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar arquivo temporário", e);
        }
    }
}
