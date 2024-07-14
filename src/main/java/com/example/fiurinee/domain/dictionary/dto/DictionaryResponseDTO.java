package com.example.fiurinee.domain.dictionary.dto;

import com.example.fiurinee.domain.flower.entity.Flower;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DictionaryResponseDTO {
    private String flower;
    private String period;
    private String flowerLanguage;
    private String explain;
    private String image;

    public static DictionaryResponseDTO of(Flower flower) {
        return DictionaryResponseDTO.builder()
                .flower(flower.getName())
                .period(String.format("%02d", flower.getPeriod() / 100))
                .flowerLanguage(flower.getFlowerLanguage())
                .explain(flower.getExplain())
                .image(flower.getImage().toString())
                .build();
    }
}
