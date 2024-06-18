package com.example.fiurinee.domain.flower.controller;
import com.example.fiurinee.domain.flower.controller.api.FlowerApi;
import com.example.fiurinee.domain.flower.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/main")
public class FlowerController implements FlowerApi {

    @Autowired
    private FlowerService flowerService;

    @Override
    public ResponseEntity<List<Map<String, Object>>> getSeasonFlowers() {
        List<Map<String, Object>> flowers = flowerService.getSeasonFlowers();
        return ResponseEntity.ok(flowers);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getTodayFlower() {
        Map<String, Object> flower = flowerService.getTodayFlower();
        return ResponseEntity.ok(flower);
    }




}