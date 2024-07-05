package com.example.fiurinee.domain.mail;

import com.example.fiurinee.domain.anniversary.entity.Anniversary;
import com.example.fiurinee.domain.member.entity.Member;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendEmail(String to, String subject, String templateName, Map<String, Object> variables) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Context context = new Context();
        context.setVariables(variables);
        String html = templateEngine.process(templateName, context);

        helper.setFrom("Fiurinee <wlgusqor12@gmail.com>");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);

        mailSender.send(message);
    }

    public void sendAnniversaryEmail(Member member, Anniversary anniversary) throws MessagingException {
        String to = member.getEmail();
        String subject = "[Fiurinee]안녕하세요, Fiurinee입니다. 기념일을 축하드려요!";
        Map<String, Object> variables = Map.of(
                "name", member.getName(),
                "anniversaryName", anniversary.getName()
        );
        sendEmail(to, subject, "email", variables);
    }

    public void sendPreAnniversaryEmail(Member member, Anniversary anniversary) throws MessagingException {
        String to = member.getEmail();
        String subject = "[Fiurinee] 안녕하세요, Fiurinee입니다. 곧 다가올 기념일을 준비하세요!";
        Map<String, Object> variables = Map.of(
                "name", member.getName(),
                "anniversaryName", anniversary.getName()
        );
        sendEmail(to, subject, "pre-email", variables);
    }
}

