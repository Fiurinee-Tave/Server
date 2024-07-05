package com.example.fiurinee.domain.anniversary.dto;

import com.example.fiurinee.domain.anniversary.entity.Anniversary;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnniversaryResponseDTO {
    private Long id;
    private String name;
    private String anniversaryDate;
    private String type;

    @JsonProperty("dDays")
    private List<Map<String, Integer>> dDays;

    public static AnniversaryResponseDTO of(Anniversary anniversary, List<Map<String, Integer>> dDays) {
        String fullDate = anniversary.getAnniversaryDate().toString();
        String formattedDate = fullDate.substring(0, 10); // "yyyy-MM-dd" 형식으로 자르기

        return AnniversaryResponseDTO.builder()
                .id(anniversary.getId())
                .name(anniversary.getName())
                .anniversaryDate(formattedDate)
                .type(anniversary.getType().name())
                .dDays(dDays)
                .build();
    }

    public static AnniversaryResponseDTO empty() {
        Map<String, Integer> nullDay = new HashMap<>();
        nullDay.put("year", null);

        List<Map<String, Integer>> dDays = new ArrayList<>();
        dDays.add(nullDay);

        return AnniversaryResponseDTO.builder()
                .id(null)
                .name(null)
                .anniversaryDate(null)
                .type(null)
                .dDays(dDays)
                .build();
    }
}
