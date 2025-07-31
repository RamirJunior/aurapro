package br.gov.ma.aurapro.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AudioResponse {
    private String transcription;
    private String summary;
}
