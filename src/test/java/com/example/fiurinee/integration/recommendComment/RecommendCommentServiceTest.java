package com.example.fiurinee.integration.recommendComment;


import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.recommendComment.entity.RecommendComment;
import com.example.fiurinee.domain.recommendComment.repository.RecommendCommentRepository;
import com.example.fiurinee.domain.recommendComment.service.RecommendCommentService;
import com.example.fiurinee.domain.recommendFlower.entity.RecommendFlower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class RecommendCommentServiceTest {

    @Mock
    private RecommendCommentRepository recommendCommentRepository;

    @InjectMocks
    private RecommendCommentService recommendCommentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRecommendComment() {
        // Given
        Member member = Member.builder()
                .build();

        RecommendComment recommendComment = RecommendComment.builder()
                .recommendCommentId(1L)
                .member(member)
                .content("내 이름은 문희, 탐정이죠")
                .prefer(true)
                .build();

        // When
        recommendCommentService.saveRecommendComment(recommendComment);

        // Then
        verify(recommendCommentRepository, times(1)).save(recommendComment);
    }

}
