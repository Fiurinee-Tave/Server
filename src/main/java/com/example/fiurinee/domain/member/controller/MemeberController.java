package com.example.fiurinee.domain.member.controller;

import com.example.fiurinee.domain.member.controller.api.MemberApi;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemeberController implements MemberApi {

    @Autowired
    private MemberService memberService;

    @Override
    public ResponseEntity<Member> getMemberById(@PathVariable("id") Long id) {
        Member member = memberService.findById(id);
        return ResponseEntity.ok(member);
    }
}