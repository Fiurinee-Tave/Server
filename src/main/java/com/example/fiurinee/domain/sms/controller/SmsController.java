package com.example.fiurinee.domain.sms.controller;

import com.example.fiurinee.domain.anniversary.dto.AnniversaryRequestDTO;
import com.example.fiurinee.domain.anniversary.entity.Anniversary;
import com.example.fiurinee.domain.anniversary.service.AnniversaryService;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.member.service.MemberService;
import com.example.fiurinee.domain.sms.controller.api.SmsApi;
import com.example.fiurinee.domain.sms.dto.CertificateDto;
import com.example.fiurinee.domain.sms.dto.SmsDto;
import com.example.fiurinee.domain.sms.service.SmsService;
import com.example.fiurinee.global.redis.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/sms")
public class SmsController implements SmsApi {

    private final SmsService smsService;
    private final RedisUtil redisUtil;
    private final MemberService memberService;
    private final AnniversaryService anniversaryService;

    @PostMapping("/send")
    public void SendSms(@RequestBody SmsDto sms){
        smsService.certificateSMS(sms.getPhoneNumber());
    }

    @PostMapping("/prove/{id}")
    public ResponseEntity<?> certificateSms(@RequestBody CertificateDto dto, @PathVariable("id") Long id){
        Object o = redisUtil.get(dto.getPhoneNumber());

        if(o.toString().equals(dto.getCertificateNum())){
            memberService.updatePhoneNumber(id,dto.getPhoneNumber());
            return ResponseEntity.status(200).build();
        }
        else{
            return ResponseEntity.status(400).build();
        }
    }

//    @GetMapping("/test")
//    public void test(){
//        Member byId = memberService.findById(1L);
//        AnniversaryRequestDTO anniversaryRequestDTO = new AnniversaryRequestDTO("이준범 생일", LocalDate.now(), "생일");
//        Anniversary anniversary = anniversaryService.addAnniversary(1L, anniversaryRequestDTO);
//        smsService.sendSMS(byId,anniversary);
//    }//
}
