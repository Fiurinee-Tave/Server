package com.example.fiurinee.domain.sms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateDto {

    private String phoneNumber;
    private String certificateNum;

    public CertificateDto(){}

    public CertificateDto(String phoneNumber, String certificateNum) {
        this.phoneNumber = phoneNumber;
        this.certificateNum = certificateNum;
    }
}
