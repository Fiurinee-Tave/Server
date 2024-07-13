package com.example.fiurinee.integration.flower;

import com.example.fiurinee.domain.flower.dto.FlowerResponseDTO;
import com.example.fiurinee.domain.flower.entity.Flower;
import com.example.fiurinee.domain.flower.repository.FlowerRepository;
import com.example.fiurinee.domain.flower.service.FlowerService;
import com.example.fiurinee.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class FlowerServiceTest {
    @Mock
    private FlowerRepository flowerRepository;

    @InjectMocks
    private FlowerService flowerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindByNameAndFlowerLanguage() {
        String name = "장미";
        String flowerLanguage = "사랑";
        Flower flower = Flower.builder()
                .name(name)
                .period(601L)
                .flowerLanguage(flowerLanguage)
                .explain("꽃이 아름다워요!")
                .image(toURL("https://example.com/rose.jpg"))
                .build();

        when(flowerRepository.findByNameAndFlowerLanguage(name, flowerLanguage)).thenReturn(Optional.of(flower));

        Flower foundFlower = flowerService.findByNameAndFlowerLanguage(name, flowerLanguage);

        assertThat(foundFlower).isNotNull();
        assertThat(foundFlower.getName()).isEqualTo(name);
    }

    @Test
    void testFindByNameAndFlowerLanguageThrowsException() {
        String name = "Unknown";
        String flowerLanguage = "Unknown";

        when(flowerRepository.findByNameAndFlowerLanguage(name, flowerLanguage)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> {
            flowerService.findByNameAndFlowerLanguage(name, flowerLanguage);
        });
    }

    private URL toURL(String urlString) {
        try {
            return new URL(urlString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
