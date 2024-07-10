package com.example.fiurinee.integration.flower;

import com.example.fiurinee.domain.flower.dto.FlowerResponseDTO;
import com.example.fiurinee.domain.flower.service.FlowerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FlowerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlowerService flowerService;

    @Test
    void testGetSeasonFlowers() throws Exception {
        FlowerResponseDTO flower = FlowerResponseDTO.builder()
                .flower("장미")
                .flower_language("사랑")
                .explain("꽃이 아름다워요!")
                .image("https://example.com/rose.jpg")
                .build();

        when(flowerService.getSeasonFlowers()).thenReturn(Collections.singletonList(flower));

        mockMvc.perform(get("/flowers/season")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"flower\":\"장미\",\"flowerLanguage\":\"사랑\",\"explain\":\"꽃이 아름다워요!\",\"image\":\"https://example.com/rose.jpg\"}]"));
    }

    @Test
    void testGetTodayFlower() throws Exception {
        FlowerResponseDTO flower = FlowerResponseDTO.builder()
                .flower("꽃")
                .flower_language("꽃말")
                .explain("꽃이 이뻐요")
                .image("https://example.com/flower.jpg")
                .build();

        when(flowerService.getTodayFlower()).thenReturn(flower);

        mockMvc.perform(get("/flowers/today")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"flower\":\"flower\",\"flowerLanguage\":\"꽃말\",\"explain\":\"꽃이 이뻐요!\",\"image\":\"https://example.com/flower.jpg\"}"));
    }
}
