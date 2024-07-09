package com.example.fiurinee.domain.flower.controller;
import com.example.fiurinee.domain.flower.controller.api.FlowerApi;
import com.example.fiurinee.domain.flower.dto.FlowerResponseDTO;
import com.example.fiurinee.domain.flower.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class FlowerController implements FlowerApi {

    @Autowired
    private FlowerService flowerService;

    @Override
    public ResponseEntity<List<FlowerResponseDTO>> getSeasonFlowers() {
        List<FlowerResponseDTO> flowers = flowerService.getSeasonFlowers();
        return ResponseEntity.ok(flowers);
    }

    @Override
    public ResponseEntity<FlowerResponseDTO> getTodayFlower() {
        FlowerResponseDTO flower = flowerService.getTodayFlower();
        return ResponseEntity.ok(flower);
    }

}
