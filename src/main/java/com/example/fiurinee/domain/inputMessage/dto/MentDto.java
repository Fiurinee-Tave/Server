package com.example.fiurinee.domain.inputMessage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MentDto {

    private String user_input;
    private int user_month;

    public MentDto(){}

    public MentDto(String ment, int month) {
        this.user_input = ment;
        this.user_month = month;
    }
}
