package com.example.fiurinee.domain.inputMessage.dto;

import com.example.fiurinee.domain.flower.entity.Flower;
import lombok.Getter;

import java.net.URL;
import java.util.List;

@Getter
public class ResponseHarmonyDto{
    private Long id;
    private String harmonyFlower;
    private String period;
    private String flower_language;
    private String explain;
    private URL image;

    public ResponseHarmonyDto(){}
    public ResponseHarmonyDto(Flower flower) {
        this.id = flower.getFlowerId();
        this.harmonyFlower = flower.getName();
        this.period = String.format("%02d", flower.getPeriod() / 100);
        this.explain = flower.getExplain();
        this.flower_language = flower.getFlowerLanguage();
        this.image = flower.getImage();
    }
}

