package com.example.fiurinee.domain.sms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsDto {

    private String phoneNumber;

    public SmsDto(){}

    public SmsDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
