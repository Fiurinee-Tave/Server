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
    void testGetSeasonFlowers() {
        Flower flower = Flower.builder()
                .name("장미")
                .period(601L)
                .flowerLanguage("사랑")
                .explain("꽃이 아름다워요!")
                .image(toURL("https://example.com/rose.jpg"))
                .build();

        when(flowerRepository.findByPeriodMonth(600L, 699L)).thenReturn(Collections.singletonList(flower));

        List<FlowerResponseDTO> flowers = flowerService.getSeasonFlowers();

        assertThat(flowers).isNotEmpty();
        assertThat(flowers.get(0).getFlower()).isEqualTo("장미");
    }

    @Test
    void testGetTodayFlower() {
        Flower flower = Flower.builder()
                .name("꽃")
                .period(709L)
                .flowerLanguage("꽃말")
                .explain("꽃이 이뻐요")
                .image(toURL("https://example.com/lily.jpg"))
                .build();

        when(flowerRepository.findByPeriodMonth(709L, 709L)).thenReturn(Collections.singletonList(flower));

        FlowerResponseDTO flowerResponse = flowerService.getTodayFlower();

        assertThat(flowerResponse).isNotNull();
        assertThat(flowerResponse.getFlower()).isEqualTo("꽃");
    }

    @Test
    void testFindByNameAndFlowerLanguage() {
        String name = "Rose";
        String flowerLanguage = "Love";
        Flower flower = Flower.builder()
                .name(name)
                .period(601L)
                .flowerLanguage(flowerLanguage)
                .explain("A symbol of love")
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
