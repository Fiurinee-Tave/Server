package com.example.fiurinee.domain.inputMessage.dto;

import lombok.Getter;

@Getter
public class ResponseGptDto {

    private String flower_ment;

    public ResponseGptDto(){}

    public ResponseGptDto(String flower_ment) {
        this.flower_ment = flower_ment;
    }
}
