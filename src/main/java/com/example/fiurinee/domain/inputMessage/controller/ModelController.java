package com.example.fiurinee.domain.inputMessage.controller;


import com.example.fiurinee.domain.flower.entity.Flower;
import com.example.fiurinee.domain.flower.service.FlowerService;
import com.example.fiurinee.domain.inputMessage.controller.api.ModelApi;
import com.example.fiurinee.domain.inputMessage.dto.*;
import com.example.fiurinee.domain.inputMessage.service.InputMessageService;
import com.example.fiurinee.global.api.service.CallApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{id}/ment")
    public ResponseEntity<List<ResponseMentDto>> inputMent(@PathVariable("id") Long id,
                                                     @RequestBody RequestMentDto mentDto){
        String ment = mentDto.ment();
        inputMessageService.saveInputMessage(id, ment);
        String url = "http://localhost:8080/model/test";

        int value = LocalDateTime.now().getMonth().getValue();
        System.out.println("value = " + value);

        List<ModelMentResponseDto> re  = CallApiService.mentApi(url, new MentDto(ment, value));
        System.out.println("re.get(0).getName() = " + re.get(0).getName());
        System.out.println("re.get(0).getFlowerLanguage() = " + re.get(0).getFlowerLanguage());

        Flower byName0 = flowerService.findByNameAndFlowerLanguage(re.get(0).getName(),re.get(0).getFlowerLanguage());
        Flower byName1 = flowerService.findByNameAndFlowerLanguage(re.get(1).getName(),re.get(1).getFlowerLanguage());

        List<ResponseMentDto> re2 = new ArrayList<>();
        ResponseMentDto responseMentDto0 = new ResponseMentDto(byName0);
        ResponseMentDto responseMentDto1 = new ResponseMentDto(byName1);

        re2.add(responseMentDto0);
        re2.add(responseMentDto1);


        return ResponseEntity.ok(re2);

    }

    @PostMapping("/ment")
    public ResponseEntity<List<ResponseMentDto>> inputMentNotMember(@RequestBody String ment){
        String url = "http://localhost:8080/model/test";

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
                                                   @RequestBody String ment){
        ResponseHarmonyDto responseHarmonyDto0 = new ResponseHarmonyDto(flowerService.findByNameAndFlowerLanguage("토마토", "완성된 아름다움"));
        ResponseHarmonyDto responseHarmonyDto1 = new ResponseHarmonyDto(flowerService.findByNameAndFlowerLanguage("토레니아", "가련한 욕망"));

        List<ResponseHarmonyDto> re = new ArrayList<>();
        re.add(responseHarmonyDto0);
        re.add(responseHarmonyDto1);

        return ResponseEntity.ok(new ResponseHarmonyWitnMentDto("추천은 recommend, recommend는 영어로 추천, 내 너무 피곤하다,, 인생이란 뭘까",re));
    }

    @PostMapping("/{flowerId}/non")
    public ResponseEntity<ResponseHarmonyWitnMentDto> selectFlowerNonMember(@PathVariable ("flowerId") Long flowerId,
                                                                            @RequestBody String ment){
        ResponseHarmonyDto responseHarmonyDto0 = new ResponseHarmonyDto(flowerService.findByNameAndFlowerLanguage("토마토", "완성된 아름다움"));
        ResponseHarmonyDto responseHarmonyDto1 = new ResponseHarmonyDto(flowerService.findByNameAndFlowerLanguage("토레니아", "가련한 욕망"));

        List<ResponseHarmonyDto> re = new ArrayList<>();
        re.add(responseHarmonyDto0);
        re.add(responseHarmonyDto1);

        return ResponseEntity.ok(new ResponseHarmonyWitnMentDto("추천은 recommend, recommend는 영어로 추천, 내 너무 피곤하다,, 인생이란 뭘까",re));
    }

    @PostMapping("/test")
    public List<ModelMentResponseDto> modelTest(@RequestBody MentDto mentDto) {
        List<ModelMentResponseDto> re = new ArrayList<>();
        ModelMentResponseDto modelMentResponseDto0 = new ModelMentResponseDto("토레니아", "가련한 욕망");
        ModelMentResponseDto modelMentResponseDto1 = new ModelMentResponseDto("토마토", "완성된 아름다움");
        ModelMentResponseDto modelMentResponseDto2 = new ModelMentResponseDto("겹 캄파눌라", "따뜻한 사람");


        re.add(modelMentResponseDto0);
        re.add(modelMentResponseDto1);
        re.add(modelMentResponseDto2);

        return re;

    }
}
