package com.example.fiurinee.domain.inputMessage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlowerColorDto {

    @JsonProperty("꽃")
    private String flowerName;

    @JsonProperty("선택한 색상")
    private String selectedColor;

    public FlowerColorDto(){}

    public FlowerColorDto(String flower, String selectedColor) {
        this.flowerName = flower;
        this.selectedColor = selectedColor;
    }
}
