package com.example.fiurinee.domain.member.dto;

import com.example.fiurinee.domain.anniversary.service.AnniversaryService;
import com.example.fiurinee.domain.member.entity.Member;
import lombok.*;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {
    private Long memberId;
    private String email;
    private String nickname;
    private int profileImage;
    private boolean alarm;
    private List<Map<String, Object>> anniversaries;

    private static AnniversaryService anniversaryService = new AnniversaryService();

    public static MemberResponseDTO of(Member member) {
        return MemberResponseDTO.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getName())
                .profileImage(member.getProfileImage())
                .alarm(member.isAlarm())
                .anniversaries(member.getAnniversaries().stream()
                        .sorted(Comparator.comparingInt(anniversary ->
                                anniversaryService.calculateDDay(anniversary).get(0).values().iterator().next()))
                        .map(anniversary -> Map.<String, Object>of(
                                "id", anniversary.getId(),
                                "name", anniversary.getName(),
                                "anniversaryDate", anniversary.getAnniversaryDate().toString().substring(0,10),
                                "type", anniversary.getType().name(),
                                "dDays", anniversaryService.calculateDDay(anniversary)
                        ))
                        .collect(Collectors.toList()))
                .build();
    }
}
