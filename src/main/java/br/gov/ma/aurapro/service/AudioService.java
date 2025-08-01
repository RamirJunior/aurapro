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

    public AudioResponse process(@NotNull MultipartFile audioFile){

        if (isSizeValid(audioFile)) {
            File tempAudioFile = saveFileTemporarily(audioFile);
            String audioTranscription = transcriptionService.transcript(tempAudioFile);

            String summary = "There\n are many \nvariations of passages of Lorem " +
                    "Ipsum\n available, \nbut the majority have\n suffered alteration in some " +
                    "form,\n by\n injected\n humour, or \nrandomised\n words which don't " +
                    "look\n even\n slightly\n believable. \nIf you \nare going to use a\n passage " +
                    "of\n Lorem\n Ipsum, you\n need to be sure \nthere isn't \nanything " +
                    "embarrassing\n hidden \nin the middle\n of text.";

            return new AudioResponse(audioTranscription, summary);
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
