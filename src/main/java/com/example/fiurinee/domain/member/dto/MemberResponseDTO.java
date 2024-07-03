package com.example.fiurinee.domain.member.dto;

import com.example.fiurinee.domain.anniversary.service.AnniversaryService;
import com.example.fiurinee.domain.member.entity.Member;
import lombok.*;

import java.net.URL;
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
                        .map(anniversary -> {
                            List<Map<String, Integer>> dDays = anniversaryService.calculateDDay(anniversary);
                            return Map.of(
                                    "id", anniversary.getId(),
                                    "name", anniversary.getName(),
                                    "anniversaryDate", anniversary.getAnniversaryDate().toString().substring(0, 10),
                                    "type", anniversary.getType().name(),
                                    "dDays", dDays
                            );
                        })
                        .sorted((a1, a2) -> {
                            Integer dDay1 = (Integer) ((Map<String, Integer>) ((List<?>) a1.get("dDays")).get(0)).values().iterator().next();
                            Integer dDay2 = (Integer) ((Map<String, Integer>) ((List<?>) a2.get("dDays")).get(0)).values().iterator().next();
                            return dDay1.compareTo(dDay2);
                        })
                        .collect(Collectors.toList()))
                .build();
    }
}
