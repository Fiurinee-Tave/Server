package com.example.fiurinee.domain.sms.controller.api;

import com.example.fiurinee.domain.member.dto.MemberResponseDTO;
import com.example.fiurinee.domain.sms.dto.CertificateDto;
import com.example.fiurinee.domain.sms.dto.SmsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Sms", description = "문자 전송 및 휴대폰 번호 인증 API")
public interface SmsApi {
    @Operation(
            summary = "인증 번호 전송",
            description = "유저가 입력한 휴대폰 번호로 인증번호를 전송합니다. 인증 번호 만료 시간은 5분 입니다."
    )

    @ApiResponse(responseCode = "200", description = "성공")
    @PostMapping("/send")
    public void SendSms(@RequestBody SmsDto sms);

    @Operation(
            summary = "유저가 입력한 인증 번호가 일치하는지 확인하는 api",
            description = "유저가 입력한 인증 번호가 일치하는지 확인하고 일치 한다면 휴대폰 번호를 저장합니다."
    )

    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "인증 번호가 일치하지 않았어요~~")

    @PostMapping("/prove/{id}")
    public ResponseEntity<?> certificateSms(@RequestBody CertificateDto dto, @Parameter(description = "유저의 id 입니다.")@PathVariable("id") Long id);
}
