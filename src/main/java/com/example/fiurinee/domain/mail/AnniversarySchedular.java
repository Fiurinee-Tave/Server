package com.example.fiurinee.domain.mail;

import com.example.fiurinee.domain.anniversary.entity.Anniversary;
import com.example.fiurinee.domain.anniversary.service.AnniversaryService;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.member.service.MemberService;
import com.example.fiurinee.domain.sms.service.SmsService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

@Service
public class AnniversarySchedular {
    private final AnniversaryService anniversaryService;
    private final MailService mailService;
    private final MemberService memberService;
    private final SmsService smsService;

    public AnniversarySchedular(AnniversaryService anniversaryService, MailService mailService, MemberService memberService,SmsService smsService) {
        this.anniversaryService = anniversaryService;
        this.mailService = mailService;
        this.memberService = memberService;
        this.smsService = smsService;
    }

    @Scheduled(cron = "0 30 14 * * *", zone = "Asia/Seoul")
    @Transactional
    public void sendAnniversaryEmails() {
        List<Member> members = memberService.findAll();
        for (Member member : members) {
            if (!member.isAlarm()) {
                continue;
            }
            List<Anniversary> anniversaries = member.getAnniversaries();
            for (Anniversary anniversary : anniversaries) {
                List<Map<String, Integer>> allDDays = anniversaryService.calculateDDay(anniversary);
                for (Map<String, Integer> dDay : allDDays) {
                    for (Map.Entry<String, Integer> entry : dDay.entrySet()) {
                        try {
                            if (entry.getValue() == 0) {
                                mailService.sendAnniversaryEmail(anniversary.getMember(), anniversary);
                                smsService.sendSMS(anniversary.getMember(), anniversary);
                            } else if (entry.getValue() == 3) {
                                mailService.sendPreAnniversaryEmail(anniversary.getMember(), anniversary);
                                smsService.preSendSMS(anniversary.getMember(), anniversary);
                            }
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
