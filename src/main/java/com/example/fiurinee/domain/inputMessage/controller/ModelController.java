package com.example.fiurinee.domain.inputMessage.controller;


import com.example.fiurinee.domain.flower.entity.Flower;
import com.example.fiurinee.domain.flower.service.FlowerService;
import com.example.fiurinee.domain.inputMessage.controller.api.ModelApi;
import com.example.fiurinee.domain.inputMessage.dto.*;
import com.example.fiurinee.domain.inputMessage.service.InputMessageService;
import com.example.fiurinee.domain.matchingFlower.entity.MatchingFlower;
import com.example.fiurinee.domain.matchingFlower.service.MatchingFlowerService;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.member.service.MemberService;
import com.example.fiurinee.domain.recommendComment.service.RecommendCommentService;
import com.example.fiurinee.domain.recommendFlower.entity.RecommendFlower;
import com.example.fiurinee.domain.recommendFlower.service.RecommendFlowerService;
import com.example.fiurinee.global.api.service.CallApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/model")
public class ModelController implements ModelApi {

    private final InputMessageService inputMessageService;
    private final FlowerService flowerService;
    private final MemberService memberService;
    private final RecommendFlowerService recommendFlowerService;
    private final MatchingFlowerService matchingFlowerService;
    private final RecommendCommentService recommendCommentService;

    @PostMapping("/{id}/ment")
    public ResponseEntity<List<ResponseMentDto>> inputMent(@PathVariable("id") Long id,
                                                     @RequestBody RequestMentDto mentDto){
        String ment = mentDto.ment();

        String url = "http://3.106.186.161/api/recommend";

        int value = LocalDateTime.now().getMonth().getValue();

        List<ModelMentResponseDto> re  = CallApiService.mentApi(url, new MentDto(ment, value));

        Flower byName0 = flowerService.findByNameAndFlowerLanguage(re.get(0).getName(),re.get(0).getFlowerLanguage());
        Flower byName1 = flowerService.findByNameAndFlowerLanguage(re.get(1).getName(),re.get(1).getFlowerLanguage());
        Flower byName2 = flowerService.findByNameAndFlowerLanguage(re.get(2).getName(),re.get(2).getFlowerLanguage());

        List<ResponseMentDto> re2 = new ArrayList<>();
        ResponseMentDto responseMentDto0 = new ResponseMentDto(byName0);
        ResponseMentDto responseMentDto1 = new ResponseMentDto(byName1);
        ResponseMentDto responseMentDto2 = new ResponseMentDto(byName2);

        re2.add(responseMentDto0);
        re2.add(responseMentDto1);
        re2.add(responseMentDto2);


        return ResponseEntity.ok(re2);

    }

    @PostMapping("/ment")
    public ResponseEntity<List<ResponseMentDto>> inputMentNotMember(@RequestBody RequestMentDto mentDto){

        String ment = mentDto.ment();
        String url = "http://3.106.186.161/api/recommend";

        int value = LocalDateTime.now().getMonth().getValue();

        List<ModelMentResponseDto> re  = CallApiService.mentApi(url, new MentDto(ment, value));

        Flower byName0 = flowerService.findByNameAndFlowerLanguage(re.get(0).getName(),re.get(0).getFlowerLanguage());
        Flower byName1 = flowerService.findByNameAndFlowerLanguage(re.get(1).getName(),re.get(1).getFlowerLanguage());
        Flower byName2 = flowerService.findByNameAndFlowerLanguage(re.get(2).getName(),re.get(2).getFlowerLanguage());


        List<ResponseMentDto> re2 = new ArrayList<>();
        ResponseMentDto responseMentDto0 = new ResponseMentDto(byName0);
        ResponseMentDto responseMentDto1 = new ResponseMentDto(byName1);
        ResponseMentDto responseMentDto2 = new ResponseMentDto(byName2);

        re2.add(responseMentDto0);
        re2.add(responseMentDto1);
        re2.add(responseMentDto2);


        return ResponseEntity.ok(re2);

    }

    @PostMapping("/{memberId}/{flowerId}")
    public ResponseEntity<ResponseHarmonyWitnMentDto> selectFlower(@PathVariable ("memberId") Long memberId,
                                                   @PathVariable ("flowerId") Long flowerId,
                                                   @RequestBody RequestMentDto mentDto){

        String ment = mentDto.ment();

        inputMessageService.saveInputMessage(memberId,ment);

        String url = "http://3.106.186.161/api/flower_color";
        Member byId = memberService.findById(memberId);
        Flower flower = flowerService.findById(flowerId);

        RequestHarmonyDto requestHarmonyDto = new RequestHarmonyDto(flower.getName(), flower.getFlowerLanguage());

        List<FlowerColorDto> flowerColorDtos = CallApiService.harmonyApi(url, requestHarmonyDto);

        String flowerName0 = flowerColorDtos.get(0).getFlowerName();
        String flowerName1 = flowerColorDtos.get(1).getFlowerName();

        Flower byName0 = flowerService.findByName(flowerName0);
        Flower byName1 = flowerService.findByName(flowerName1);

        //추천 받은 꽃과 어울리는 꽃 정보를 멤버 엔티티에 저장

        RecommendFlower saveRecommendFlower = recommendFlowerService.saveRecommendFlower(byId,flower);

        matchingFlowerService.save(saveRecommendFlower,byName0);
        matchingFlowerService.save(saveRecommendFlower,byName1);

        ResponseHarmonyDto responseHarmonyDto0 = new ResponseHarmonyDto(byName0);
        ResponseHarmonyDto responseHarmonyDto1 = new ResponseHarmonyDto(byName1);

        List<ResponseHarmonyDto> re = new ArrayList<>();
        re.add(responseHarmonyDto0);
        re.add(responseHarmonyDto1);

        ///모델 서버에서 멘트 추천 받아오기
        url = "http://3.106.186.161/api/ment";

        RequestGptDto requestGptDto = new RequestGptDto(ment, flower.getName(), flower.getFlowerLanguage());

        ResponseGptDto responseGptDto = CallApiService.gptApi(url, requestGptDto);

        recommendCommentService.saveRecommendComment(byId,responseGptDto.getFlower_ment());

        return ResponseEntity.ok(new ResponseHarmonyWitnMentDto(responseGptDto.getFlower_ment(),re));
    }

    @PostMapping("/{flowerId}/non")
    public ResponseEntity<ResponseHarmonyWitnMentDto> selectFlowerNonMember(@PathVariable ("flowerId") Long flowerId,
                                                                            @RequestBody RequestMentDto mentDto){
        String ment = mentDto.ment();

        String url = "http://3.106.186.161/api/flower_color";
        Flower flower = flowerService.findById(flowerId);

        RequestHarmonyDto requestHarmonyDto = new RequestHarmonyDto(flower.getName(), flower.getFlowerLanguage());

        List<FlowerColorDto> flowerColorDtos = CallApiService.harmonyApi(url, requestHarmonyDto);

        String flowerName0 = flowerColorDtos.get(0).getFlowerName();
        String flowerName1 = flowerColorDtos.get(1).getFlowerName();

        Flower byName0 = flowerService.findByName(flowerName0);
        Flower byName1 = flowerService.findByName(flowerName1);

        ResponseHarmonyDto responseHarmonyDto0 = new ResponseHarmonyDto(byName0);
        ResponseHarmonyDto responseHarmonyDto1 = new ResponseHarmonyDto(byName1);

        List<ResponseHarmonyDto> re = new ArrayList<>();
        re.add(responseHarmonyDto0);
        re.add(responseHarmonyDto1);

        ///모델 서버에서 멘트 추천 받아오기
        url = "http://3.106.186.161/api/ment";

        RequestGptDto requestGptDto = new RequestGptDto(ment, flower.getName(), flower.getFlowerLanguage());

        ResponseGptDto responseGptDto = CallApiService.gptApi(url, requestGptDto);

        return ResponseEntity.ok(new ResponseHarmonyWitnMentDto(responseGptDto.getFlower_ment(),re));
    }
}
