package com.example.fiurinee.integration.recommendFlower;

import com.example.fiurinee.domain.recommendFlower.entity.RecommendFlower;
import com.example.fiurinee.domain.recommendFlower.repository.RecommendFlowerRepository;
import com.example.fiurinee.domain.recommendFlower.service.RecommendFlowerService;
import com.example.fiurinee.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RecommendFlowerServiceTest {
    @Mock
    private RecommendFlowerRepository recommendFlowerRepository;

    @InjectMocks
    private RecommendFlowerService recommendFlowerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRecommendFlower() {
        RecommendFlower recommendFlower = RecommendFlower.builder()
                .recommendFlowerId(1L)
                .prefer(true)
                .build();

        recommendFlowerService.saveRecommendFlower(recommendFlower);

        verify(recommendFlowerRepository, times(1)).save(recommendFlower);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        RecommendFlower recommendFlower = RecommendFlower.builder()
                .recommendFlowerId(id)
                .prefer(true)
                .build();

        when(recommendFlowerRepository.findById(id)).thenReturn(Optional.of(recommendFlower));

        RecommendFlower foundFlower = recommendFlowerService.findById(id);

        assertThat(foundFlower).isNotNull();
        assertThat(foundFlower).isEqualTo(recommendFlower);
    }

    @Test
    void testFindByIdThrowsException() {
        Long id = 1L;
        when(recommendFlowerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> recommendFlowerService.findById(id));
    }

    @Test
    void testEditPrefer() {
        RecommendFlower recommendFlower = mock(RecommendFlower.class);
        Boolean value = true;

        recommendFlowerService.editPrefer(recommendFlower, value);

        verify(recommendFlower, times(1)).editPrefer(value);
    }
}
