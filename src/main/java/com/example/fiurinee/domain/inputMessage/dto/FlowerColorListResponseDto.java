package com.example.fiurinee.domain.inputMessage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FlowerColorListResponseDto {

    @JsonProperty("flower_color_list")
    private List<FlowerColorDto> flowerColorList;

    public FlowerColorListResponseDto(){}

    public FlowerColorListResponseDto(List<FlowerColorDto> flowerColorList) {
        this.flowerColorList = flowerColorList;
    }
}