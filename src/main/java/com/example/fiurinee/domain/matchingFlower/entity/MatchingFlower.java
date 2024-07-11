package com.example.fiurinee.domain.matchingFlower.entity;

import com.example.fiurinee.domain.flower.entity.Flower;
import com.example.fiurinee.domain.recommendFlower.entity.RecommendFlower;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MatchingFlower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchingFlowerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendFlowerId")
    private RecommendFlower recommendFlower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flowerId")
    private Flower flower;

    public static MatchingFlower createTestInstance() {
        return new MatchingFlower();
    }
}
