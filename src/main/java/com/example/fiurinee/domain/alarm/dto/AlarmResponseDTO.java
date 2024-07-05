package com.example.fiurinee.domain.alarm.dto;

import com.example.fiurinee.domain.member.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlarmResponseDTO {
    private boolean alarm;

    public static AlarmResponseDTO of(Member member) {
        return AlarmResponseDTO.builder()
                .alarm(member.isAlarm())
                .build();
    }
}
