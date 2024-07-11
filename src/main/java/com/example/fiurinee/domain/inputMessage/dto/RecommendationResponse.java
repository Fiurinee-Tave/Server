package com.example.fiurinee.domain.inputMessage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class RecommendationResponse {
    @JsonProperty("recommendations")
    private List<ModelMentResponseDto> recommendations;

    public RecommendationResponse() {}

    public RecommendationResponse(List<ModelMentResponseDto> recommendations) {
        this.recommendations = recommendations;
    }
}

