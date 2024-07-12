package com.example.fiurinee.global.api.service;

import com.example.fiurinee.domain.inputMessage.dto.MentDto;
import com.example.fiurinee.domain.inputMessage.dto.ModelMentResponseDto;
import com.example.fiurinee.domain.inputMessage.dto.RecommendationResponse;
import com.example.fiurinee.domain.inputMessage.dto.ResponseMentDto;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.oauth2.dto.KakaoLogoutDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CallApiService {

    private final WebClient webClient;

    public CallApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public static ClientResponse checkKakaoToken(String kakaoAccessToken) {

        WebClient webClient = WebClient.builder().build();

        return webClient
                .get()
                .uri("https://kapi.kakao.com/v1/user/access_token_info")
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .exchange()
                .block();
    }

    public static KakaoLogoutDto logoutKakao(Member member, String kakaoAccessToken) {

        WebClient webClient = WebClient.builder().build();

        return webClient
                .post()
                .uri("https://kapi.kakao.com/v1/user/logout")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .retrieve()
                .bodyToMono(KakaoLogoutDto.class)
                .block();



    }

    public static KakaoLogoutDto resignKakao(String kakaoAccessToken) {

        WebClient webClient = WebClient.builder().build();

        return webClient
                .post()
                .uri("https://kapi.kakao.com/v1/user/unlink")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization","Bearer "+kakaoAccessToken)
                .retrieve()
                .bodyToMono(KakaoLogoutDto.class)
                .block();

    }

    public static List<ModelMentResponseDto> mentApi(String url, MentDto mentDto) {

        WebClient webClient = WebClient.builder().build();

        RecommendationResponse response = webClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(mentDto)
                .retrieve()
                .bodyToMono(RecommendationResponse.class)
                .block();
        return Objects.requireNonNull(response).getRecommendations();

    }
}
