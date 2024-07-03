package com.example.fiurinee.domain.recommendFlower.dto;

import com.example.fiurinee.domain.flower.entity.Flower;
import com.example.fiurinee.domain.inputMessage.entity.InputMessage;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.recommendComment.entity.RecommendComment;
import com.example.fiurinee.domain.recommendFlower.entity.RecommendFlower;
import lombok.Getter;

import java.net.URL;
import java.util.List;

@Getter
public class RecommendFlowerDto {

    private Long order;
    private String recommendFlower;
    private String period;
    private String flower_language;
    private String explain;
    private URL image;
    private String inputMessage;
    private String create_at;
    private String recommendMessage;
    private boolean prefer;

    public RecommendFlowerDto(){}

    public RecommendFlowerDto(Long order, String recommendFlower, String period, String flower_language, String explain, URL image, String inputMessage, String create_at, String recommendMessage, boolean prefer) {
        this.order = order;
        this.recommendFlower = recommendFlower;
        this.period = period;
        this.flower_language = flower_language;
        this.explain = explain;
        this.image = image;
        this.inputMessage = inputMessage;
        this.create_at = create_at;
        this.recommendMessage = recommendMessage;
        this.prefer = prefer;
    }

    public static RecommendFlowerDto of(Long order, Member member){
        List<RecommendFlower> recommendFlowers = member.getRecommendFlowers();
        if(recommendFlowers.size() < order){
            return null;
        }
        RecommendFlower recommendFlower = recommendFlowers.get(recommendFlowers.size()-order.intValue());
        Flower flower = recommendFlower.getFlower();

        RecommendFlowerDto recommendFlowerDto = new RecommendFlowerDto();
        recommendFlowerDto.order = order;
        recommendFlowerDto.recommendFlower = flower.getName();
        if(flower.getPeriod() < 1000){
            Long p = flower.getPeriod() / 10;
        }else{
            Long p = flower.getPeriod() / 100;
        }

        recommendFlowerDto.period = flower.getPeriod().toString();
        recommendFlowerDto.flower_language = flower.getFlowerLanguage();
        recommendFlowerDto.explain = flower.getExplain();
        recommendFlowerDto.image = flower.getImage();

        List<InputMessage> inputMessages = member.getInputMessages();
        InputMessage inputMessage = inputMessages.get(inputMessages.size() - order.intValue());

        recommendFlowerDto.inputMessage = inputMessage.getContent();
        recommendFlowerDto.create_at = inputMessage.getCreateAt().toString().substring(0,10);

        List<RecommendComment> recommendComments = member.getRecommendComments();
        RecommendComment recommendComment = recommendComments.get(recommendComments.size() - order.intValue());

        recommendFlowerDto.recommendMessage = recommendComment.getContent();
        recommendFlowerDto.prefer = recommendFlower.getPrefer();

        return recommendFlowerDto;
    }
}
