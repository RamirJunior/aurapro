package br.gov.ma.aurapro.controller;

import br.gov.ma.aurapro.dtos.AudioResponse;
import br.gov.ma.aurapro.service.AudioService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/aurapro")
public class AudioController {

    private final AudioService service;

    @GetMapping("/pagina")
    public String showForm(Model model) {
        model.addAttribute("mensagem", "Envie seu áudio para transcrição:");
        return "audio-form"; // Vai carregar templates/audio-form.html
    }

    @PostMapping("/process")
    public ResponseEntity<AudioResponse> processAudio(
            @RequestParam("audioFile") @NotNull MultipartFile audioFile,
            @RequestParam(defaultValue = "true") boolean summarize) {

        if (audioFile.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new AudioResponse("Arquivo de áudio ausente ou inválido", null));
        }

        var response = service.process(audioFile, summarize);
        return ResponseEntity.ok(response);
    }

}
