package com.example.fiurinee.integration.inputMessage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.fiurinee.domain.inputMessage.entity.InputMessage;
import com.example.fiurinee.domain.inputMessage.repository.InputMessageRepository;
import com.example.fiurinee.domain.inputMessage.service.InputMessageService;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.member.entity.Role;
import com.example.fiurinee.domain.member.repository.MemberRepository;
import com.example.fiurinee.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

public class InputMessageServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private InputMessageRepository inputMessageRepository;

    @InjectMocks
    private InputMessageService inputMessageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveInputMessage_validMember_savesMessage() {
        Long memberId = 1L;
        String message = "메롱 메롱 ";

        Member member = Member.builder()
                .email("qormoon@sookmyung.ac.kr")
                .name("백지현")
                .socialId("12345")
                .role(Role.USER)
                .kakaoAccessToken("token")
                .profileImage(1)
                .alarm(false)
                .build();

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(inputMessageRepository.save(any(InputMessage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = inputMessageService.saveInputMessage(memberId, message);

        assertTrue(result);
        verify(memberRepository, times(1)).findById(memberId);
        verify(inputMessageRepository, times(1)).save(any(InputMessage.class));
    }

    @Test
    void saveInputMessage_invalidMember_throwsException() {
        Long memberId = 1L;
        String message = "메롱 메롱 ";

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> inputMessageService.saveInputMessage(memberId, message));
        verify(memberRepository, times(1)).findById(memberId);
        verify(inputMessageRepository, never()).save(any(InputMessage.class));
    }
}
