package com.example.fiurinee.domain.inputMessage.dto;

import lombok.Getter;

@Getter
public class RequestGptDto {

    private String user_input;
    private String flower_name;
    private String flower_mean;

    public RequestGptDto(){}

    public RequestGptDto(String user_input, String flower_name, String flower_mean) {
        this.user_input = user_input;
        this.flower_name = flower_name;
        this.flower_mean = flower_mean;
    }
}
