package com.example.fiurinee.domain.member.service;

import com.example.fiurinee.domain.member.dto.MemberResponseDTO;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.member.repository.MemberRepository;
import com.example.fiurinee.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CustomException("존재하지 않는 회원 아이디 입니다."));
    }

    @Transactional
    public Boolean deleteMember(Long id){

        Member member = memberRepository.findById(id).orElseThrow(() -> new CustomException("존재하지 않는 회원 아이디 입니다."));

        memberRepository.delete(member);
        return true;
    }

    public MemberResponseDTO getMemberDtoById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        return MemberResponseDTO.of(member);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }


}
