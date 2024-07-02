package com.example.fiurinee.domain.alarm.service;

import com.example.fiurinee.domain.alarm.dto.AlarmResponseDTO;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlarmService {

    private final MemberRepository memberRepository;

    public AlarmService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Transactional
    public AlarmResponseDTO updateAlarmStatus(Long memberId, boolean alarm) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + memberId));
        member.setAlarm(alarm);
        memberRepository.save(member);
        return AlarmResponseDTO.of(member);
    }
}
