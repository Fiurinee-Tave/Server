package com.example.fiurinee.domain.inputMessage.dto;

import lombok.Getter;

@Getter
public class RequestHarmonyDto {

    private String flower_name;
    private String flower_mean;

    public RequestHarmonyDto(){}

    public RequestHarmonyDto(String flower_name, String flower_mean) {
        this.flower_name = flower_name;
        this.flower_mean = flower_mean;
    }
}
