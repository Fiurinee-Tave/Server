package com.example.fiurinee.domain.member.dto;

import com.example.fiurinee.domain.member.entity.Role;

public record MemberDto(
        Long id,
        String email,
        String name,
        String socialId,
        Role role,
        String phoneNumber
) {
}
