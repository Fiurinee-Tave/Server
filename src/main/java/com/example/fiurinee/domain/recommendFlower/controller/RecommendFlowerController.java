package com.example.fiurinee.domain.recommendFlower.controller;

import com.example.fiurinee.domain.flower.entity.Flower;
import com.example.fiurinee.domain.flower.service.FlowerService;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.member.service.MemberService;
import com.example.fiurinee.domain.preferList.entity.PreferList;
import com.example.fiurinee.domain.recommendComment.entity.RecommendComment;
import com.example.fiurinee.domain.recommendComment.repository.RecommendCommentRepository;
import com.example.fiurinee.domain.recommendComment.service.RecommendCommentService;
import com.example.fiurinee.domain.recommendFlower.controller.api.RecommendFlowerApi;
import com.example.fiurinee.domain.recommendFlower.dto.RecommendFlowerDto;
import com.example.fiurinee.domain.recommendFlower.entity.RecommendFlower;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class RecommendFlowerController implements RecommendFlowerApi {

    private final MemberService memberService;
    private final FlowerService flowerService;
    private final RecommendFlowerService recommendFlowerService;
    private final RecommendCommentService recommendCommentService;

    @GetMapping("/{id}/recommend/recent")
    public ResponseEntity<List<RecommendFlowerDto>> responseRecommendFlowerRecent(@PathVariable("id") Long id){
        Member byId = memberService.findById(id);

        List<RecommendFlowerDto> re = new ArrayList<>();

        for(long i = 1 ; i<4;i++){
            RecommendFlowerDto recommendFlowerDto = RecommendFlowerDto.of(i, byId);
            if(recommendFlowerDto == null){
            }else{
                re.add(recommendFlowerDto);
            }
        }

        return ResponseEntity.ok(re);
    }

    @GetMapping("/{id}/recommend")
    public ResponseEntity<List<RecommendFlowerDto>> responseRecommendFlower(@PathVariable("id") Long id){
        Member byId = memberService.findById(id);

        List<RecommendFlowerDto> re = new ArrayList<>();

        int size = byId.getRecommendFlowers().size();

        for(long i = 1 ; i<=size;i++){
            RecommendFlowerDto recommendFlowerDto = RecommendFlowerDto.of(i, byId);
            if(recommendFlowerDto == null){
            }else{
                re.add(recommendFlowerDto);
            }
        }

        return ResponseEntity.ok(re);
    }

    @GetMapping("/{id}/prefer/recommend")
    public ResponseEntity<List<RecommendFlowerDto>> preferFlower(@PathVariable("id") Long id){
        Member byId = memberService.findById(id);
        List<PreferList> preferLists = byId.getPreferLists();

        List<RecommendFlowerDto> re = new ArrayList<>();

        for (PreferList preferList : preferLists) {
            RecommendFlowerDto recommendFlowerDto = RecommendFlowerDto.preferOf(preferList.getPreferOrder(), byId);
            if(recommendFlowerDto == null){
            }else{
                re.add(recommendFlowerDto);
            }
        }

        return ResponseEntity.ok(re);
    }

    @GetMapping("/check")
    public ResponseEntity<?> healthyCheck(){
        return ResponseEntity.ok().build();
    }
//-----------------------------------------------------------------------------------------------------------------
//    @GetMapping("/save/test")
//    public void dd(){
//        RecommendFlower build = RecommendFlower.builder()
//                .flower(flowerService.findByNameAndFlowerLanguage("감국", "그윽한 향기"))
//                .prefer(false)
//                .member(memberService.findById(2L))
//                .build();
//
//
//        recommendFlowerService.saveRecommendFlower(build);
//
////        RecommendFlower build1 = RecommendFlower.builder()
////                .flower(flowerService.findByNameAndFlowerLanguage("나도풍란", "인내"))
////                .prefer(true)
////                .member(memberService.findById(2L))
////                .build();
////
////
////        recommendFlowerService.saveRecommendFlower(build1);
////
////        RecommendFlower build2 = RecommendFlower.builder()
////                .flower(flowerService.findByNameAndFlowerLanguage("군자란", "고귀"))
////                .prefer(true)
////                .member(memberService.findById(2L))
////                .build();
////
////        recommendFlowerService.saveRecommendFlower(build2);
//
//    }

//    @GetMapping("/test/test")
//    public void pp(){
//
//        RecommendComment comment = RecommendComment.builder()
//                .content("44444444")
//                .member(memberService.findById(2L))
//                .prefer(false)
//                .build();
//
//        recommendCommentService.saveRecommendComment(comment);
//
////        RecommendComment comment1 = RecommendComment.builder()
////                .content("222222222222")
////                .member(memberService.findById(2L))
////                .prefer(false)
////                .build();
////
////        recommendCommentService.saveRecommendComment(comment1);
////
////        RecommendComment comment2 = RecommendComment.builder()
////                .content("333333333333")
////                .member(memberService.findById(2L))
////                .prefer(false)
////                .build();
////
////        recommendCommentService.saveRecommendComment(comment2);
//    }
}
