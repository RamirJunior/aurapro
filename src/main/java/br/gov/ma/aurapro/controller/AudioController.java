package br.gov.ma.aurapro.controller;

import br.gov.ma.aurapro.service.AudioService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aurapro/audio")
public class AudioController {

    private final AudioService service;

    @PostMapping("/process")
    public ResponseEntity<AudioResponse> processAudio(
            @RequestParam("audioFile") @NotNull MultipartFile audioFile,
            @RequestParam(defaultValue = "true") boolean summarize) {

        // (Validações HTTP ficam aqui, se necessário – mas regra de negócio está no Service)
        if (audioFile.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new AudioResponse("Arquivo de áudio ausente ou inválido", null));
        }

        var response = service.process(audioFile, summarize);
        return ResponseEntity.ok(response);
    }

}
