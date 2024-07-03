package com.example.fiurinee.domain.matchingFlower.controller;

import com.example.fiurinee.domain.flower.service.FlowerService;
import com.example.fiurinee.domain.matchingFlower.controller.api.MatchingFlowerApi;
import com.example.fiurinee.domain.matchingFlower.dto.HarmonyRecentDto;
import com.example.fiurinee.domain.matchingFlower.entity.MatchingFlower;
import com.example.fiurinee.domain.matchingFlower.service.MatchingFlowerService;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.member.service.MemberService;
import com.example.fiurinee.domain.preferList.entity.PreferList;
import com.example.fiurinee.domain.recommendFlower.service.RecommendFlowerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MatchingFlowerController implements MatchingFlowerApi {

    private final MemberService memberService;
    private final MatchingFlowerService matchingFlowerService;
    private final FlowerService flowerService;
    private final RecommendFlowerService recommendFlowerService;

    @GetMapping("/{id}/harmony/recent")
    public ResponseEntity<List<HarmonyRecentDto>> harmonyRecent(@PathVariable("id") Long id){
        Member byId = memberService.findById(id);

        List<HarmonyRecentDto> re = new ArrayList<>();

        for(long i=1;i<4;i++){
            List<HarmonyRecentDto> harmonyRecentDtos = HarmonyRecentDto.of(i, byId);
            if(harmonyRecentDtos != null) {
                re.add(harmonyRecentDtos.get(0));
                re.add(harmonyRecentDtos.get(1));
            }
        }

        return ResponseEntity.ok(re);
    }

    @GetMapping("/{id}/harmony")
    public ResponseEntity<List<HarmonyRecentDto>> harmony(@PathVariable("id") Long id){
        Member byId = memberService.findById(id);

        List<HarmonyRecentDto> re = new ArrayList<>();

        int size = byId.getRecommendFlowers().size();

        for(long i=1;i<=size;i++){
            List<HarmonyRecentDto> harmonyRecentDtos = HarmonyRecentDto.of(i, byId);
            if(harmonyRecentDtos != null) {
                re.add(harmonyRecentDtos.get(0));
                re.add(harmonyRecentDtos.get(1));
            }
        }

        return ResponseEntity.ok(re);
    }

    @GetMapping("/{id}/prefer/harmony")
    public ResponseEntity<List<HarmonyRecentDto>> preferHarmony(@PathVariable("id") Long id){
        Member byId = memberService.findById(id);

        List<HarmonyRecentDto> re = new ArrayList<>();
        List<PreferList> preferLists = byId.getPreferLists();

        for (PreferList preferList : preferLists) {
            List<HarmonyRecentDto> harmonyRecentDtos = HarmonyRecentDto.of(preferList.getPreferOrder(), byId);
            if(harmonyRecentDtos != null) {
                re.add(harmonyRecentDtos.get(0));
                re.add(harmonyRecentDtos.get(1));
            }
        }

        return ResponseEntity.ok(re);
    }

    //----------------------------------------------------------------------------------------------------
//    @GetMapping("/harmony/test")
//    public boolean test(){
//        MatchingFlower build1 = MatchingFlower.builder()
//                .flower(flowerService.findByNameAndFlowerLanguage("검은포플라", "용기"))
//                .recommendFlower(recommendFlowerService.findById(1L))
//                .build();
//
//        matchingFlowerService.save(build1);
//
//        MatchingFlower build2 = MatchingFlower.builder()
//                .flower(flowerService.findByNameAndFlowerLanguage("군자란", "고귀"))
//                .recommendFlower(recommendFlowerService.findById(1L))
//                .build();
//
//        matchingFlowerService.save(build2);
//
//        MatchingFlower build3 = MatchingFlower.builder()
//                .flower(flowerService.findByNameAndFlowerLanguage("나도풍란", "인내"))
//                .recommendFlower(recommendFlowerService.findById(2L))
//                .build();
//
//        matchingFlowerService.save(build3);
//
//        MatchingFlower build4 = MatchingFlower.builder()
//                .flower(flowerService.findByNameAndFlowerLanguage("노랑 히야신", "승부"))
//                .recommendFlower(recommendFlowerService.findById(2L))
//                .build();
//
//        matchingFlowerService.save(build4);
//
//        MatchingFlower build5 = MatchingFlower.builder()
//                .flower(flowerService.findByNameAndFlowerLanguage("노랑수선화", "사랑에 답하여"))
//                .recommendFlower(recommendFlowerService.findById(3L))
//                .build();
//
//        matchingFlowerService.save(build5);
//
//        MatchingFlower build6 = MatchingFlower.builder()
//                .flower(flowerService.findByNameAndFlowerLanguage("노랑제비꽃", "수줍은 사랑"))
//                .recommendFlower(recommendFlowerService.findById(3L))
//                .build();
//
//        matchingFlowerService.save(build6);
//
//
//
//        return true;
//
//    }
}
