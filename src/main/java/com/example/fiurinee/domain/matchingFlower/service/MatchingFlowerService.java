package com.example.fiurinee.domain.matchingFlower.service;

import com.example.fiurinee.domain.flower.entity.Flower;
import com.example.fiurinee.domain.matchingFlower.entity.MatchingFlower;
import com.example.fiurinee.domain.matchingFlower.repository.MatchingFlowerRepository;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.recommendFlower.entity.RecommendFlower;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class MatchingFlowerService {

    private final MatchingFlowerRepository matchingFlowerRepository;

    @Transactional
    public void save(RecommendFlower recommendFlower, Flower flower){

        MatchingFlower matchingFlower = MatchingFlower.builder()
                .recommendFlower(recommendFlower)
                .flower(flower)
                .build();

        matchingFlowerRepository.save(matchingFlower);
    }
}
