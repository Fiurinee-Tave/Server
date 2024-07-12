package com.example.fiurinee.domain.inputMessage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ModelMentResponseDto {
    @JsonProperty("꽃")
    private String name;

    @JsonProperty("꽃말")
    private String flowerLanguage;

    @JsonProperty("유사도")
    private Double similarity;

    public ModelMentResponseDto(){}

    public ModelMentResponseDto(String name, String flowerLanguage) {
        this.name = name;
        this.flowerLanguage = flowerLanguage;
        this.similarity = 0.1;
    }
}
