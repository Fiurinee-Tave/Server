package com.example.fiurinee.integration.alarm;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import com.example.fiurinee.domain.alarm.dto.AlarmResponseDTO;
import com.example.fiurinee.domain.alarm.service.AlarmService;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.member.entity.Role;
import com.example.fiurinee.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class AlarmServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private AlarmService alarmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateAlarmStatus_memberExists_updatesStatus() {
        Long memberId = 1L;
        boolean newAlarmStatus = true;

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
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AlarmResponseDTO result = alarmService.updateAlarmStatus(memberId, newAlarmStatus);

        assertNotNull(result);
        assertTrue(result.isAlarm());
        assertEquals(newAlarmStatus, member.isAlarm());
        verify(memberRepository, times(1)).findById(memberId);
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void updateAlarmStatus_memberDoesNotExist_throwsException() {
        Long memberId = 1L;
        boolean newAlarmStatus = true;

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> alarmService.updateAlarmStatus(memberId, newAlarmStatus));
        verify(memberRepository, times(1)).findById(memberId);
        verify(memberRepository, never()).save(any(Member.class));
    }
}
