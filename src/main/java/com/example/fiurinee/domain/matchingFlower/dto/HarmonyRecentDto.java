package com.example.fiurinee.domain.matchingFlower.dto;

import com.example.fiurinee.domain.flower.entity.Flower;
import com.example.fiurinee.domain.matchingFlower.entity.MatchingFlower;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.recommendFlower.entity.RecommendFlower;
import lombok.Getter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Getter
public class HarmonyRecentDto {
    private Long order;
    private String harmonyFlower;
    private String period;
    private String flower_language;
    private String explain;
    private URL image;

    public HarmonyRecentDto(){}

    public HarmonyRecentDto(Long order, String harmonyFlower, String period, String flower_language, String explain, URL image) {
        this.order = order;
        this.harmonyFlower = harmonyFlower;
        this.period = period;
        this.flower_language = flower_language;
        this.explain = explain;
        this.image = image;
    }

    public static List<HarmonyRecentDto> of(Long order, Member member){
        List<RecommendFlower> recommendFlowers = member.getRecommendFlowers();
        if(recommendFlowers.size() < order){
            return null;
        }
        RecommendFlower recommendFlower = recommendFlowers.get(recommendFlowers.size() - order.intValue());

        List<MatchingFlower> matchingFlowers = recommendFlower.getMatchingFlowers();
        if(matchingFlowers.size() < 2){
            return null;
        }

        List<HarmonyRecentDto> dtoList= new ArrayList<>();
        for(int i = 0 ;i<2;i++){
            HarmonyRecentDto harmonyRecentDto = new HarmonyRecentDto();
            Flower flower = matchingFlowers.get(i).getFlower();
            harmonyRecentDto.harmonyFlower = flower.getName();
            harmonyRecentDto.order = (long) (recommendFlowers.size() - order.intValue());
            harmonyRecentDto.period = String.format("%02d", flower.getPeriod() / 100);
            harmonyRecentDto.explain = flower.getExplain();
            harmonyRecentDto.image = flower.getImage();
            harmonyRecentDto.flower_language = flower.getFlowerLanguage();

            dtoList.add(harmonyRecentDto);

        }

        return dtoList;


    }

    public static List<HarmonyRecentDto> preferOf(Long order, Member member){
        List<RecommendFlower> recommendFlowers = member.getRecommendFlowers();
        if(recommendFlowers.size() < order){
            return null;
        }
        RecommendFlower recommendFlower = recommendFlowers.get(order.intValue());

        List<MatchingFlower> matchingFlowers = recommendFlower.getMatchingFlowers();
        if(matchingFlowers.size() < 2){
            return null;
        }

        List<HarmonyRecentDto> dtoList= new ArrayList<>();
        for(int i = 0 ;i<2;i++){
            HarmonyRecentDto harmonyRecentDto = new HarmonyRecentDto();
            Flower flower = matchingFlowers.get(i).getFlower();
            harmonyRecentDto.harmonyFlower = flower.getName();
            harmonyRecentDto.order = (long) (order.intValue());
            harmonyRecentDto.period = String.format("%02d", flower.getPeriod() / 100);
            harmonyRecentDto.explain = flower.getExplain();
            harmonyRecentDto.image = flower.getImage();
            harmonyRecentDto.flower_language = flower.getFlowerLanguage();

            dtoList.add(harmonyRecentDto);

        }

        return dtoList;


    }
}
