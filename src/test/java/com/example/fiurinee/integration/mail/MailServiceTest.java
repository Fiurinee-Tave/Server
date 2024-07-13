package com.example.fiurinee.integration.mail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Map;

import com.example.fiurinee.domain.mail.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.context.Context;

import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.anniversary.entity.Anniversary;
import org.thymeleaf.spring6.SpringTemplateEngine;

public class MailServiceTest {
    @Mock
    private JavaMailSender mailSender;

    @Mock
    private SpringTemplateEngine templateEngine;

    @InjectMocks
    private MailService mailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmail_validData_sendsEmail() throws MessagingException {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(any(String.class), any(Context.class))).thenReturn("HTML content");

        mailService.sendEmail("qormoon@naver.com", "Subject", "templateName", Map.of("key", "value"));

        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void sendAnniversaryEmail_validData_sendsEmail() throws MessagingException {
        Member member = Member.builder()
                .email("qormoon@naver.com")
                .name("백지현")
                .build();
        Anniversary anniversary = Anniversary.builder()
                .name("누구 기념일")
                .build();

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(any(String.class), any(Context.class))).thenReturn("HTML content");

        mailService.sendAnniversaryEmail(member, anniversary);

        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void sendPreAnniversaryEmail_validData_sendsEmail() throws MessagingException {
        Member member = Member.builder()
                .email("qormoon@naver.com")
                .name("백지현")
                .build();
        Anniversary anniversary = Anniversary.builder()
                .name("누구누구 기념일")
                .build();

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(any(String.class), any(Context.class))).thenReturn("HTML content");

        mailService.sendPreAnniversaryEmail(member, anniversary);

        verify(mailSender, times(1)).send(mimeMessage);
    }
}
