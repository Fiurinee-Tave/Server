package com.example.fiurinee.domain.recommendFlower.service;

import com.example.fiurinee.domain.recommendFlower.entity.RecommendFlower;
import com.example.fiurinee.domain.recommendFlower.repository.RecommendFlowerRepository;
import com.example.fiurinee.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class RecommendFlowerService {

    private final RecommendFlowerRepository recommendFlowerRepository;

    @Transactional
    public void saveRecommendFlower(RecommendFlower recommendFlower){
        recommendFlowerRepository.save(recommendFlower);
    }

    public RecommendFlower findById(Long id){
        RecommendFlower recommendFlower = recommendFlowerRepository.findById(id).orElseThrow(
                () -> new CustomException("해당 추천 꽃 아이디가 존재하지 않습니다."));

        return recommendFlower;

    }

    @Transactional
    public void editPrefer(RecommendFlower recommendFlower, Boolean value){
        Boolean prefer = recommendFlower.getPrefer();

        prefer = value;

    }
}
