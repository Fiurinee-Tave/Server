package com.example.fiurinee.domain.recommendComment.service;

import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.recommendComment.entity.RecommendComment;
import com.example.fiurinee.domain.recommendComment.repository.RecommendCommentRepository;
import com.example.fiurinee.domain.recommendFlower.entity.RecommendFlower;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class RecommendCommentService {

    private final RecommendCommentRepository recommendCommentRepository;

    @Transactional
    public void saveRecommendComment(Member member, String ment){

        RecommendComment comment = RecommendComment.builder()
                .prefer(false)
                .member(member)
                .content(ment)
                .build();
        recommendCommentRepository.save(comment);
    }
}
