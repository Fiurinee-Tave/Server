package com.example.fiurinee.integration.matchingFlower;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.fiurinee.domain.matchingFlower.entity.MatchingFlower;
import com.example.fiurinee.domain.matchingFlower.repository.MatchingFlowerRepository;
import com.example.fiurinee.domain.matchingFlower.service.MatchingFlowerService;

public class MatchingFlowerServiceTest {
    @Mock
    private MatchingFlowerRepository matchingFlowerRepository;

    @InjectMocks
    private MatchingFlowerService matchingFlowerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        MatchingFlower matchingFlower = MatchingFlower.createTestInstance();
        matchingFlowerService.save(matchingFlower);
        verify(matchingFlowerRepository, times(1)).save(matchingFlower);
    }
}
