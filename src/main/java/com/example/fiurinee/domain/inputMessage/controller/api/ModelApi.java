package com.example.fiurinee.domain.inputMessage.controller.api;

import com.example.fiurinee.domain.anniversary.dto.AnniversaryRequestDTO;
import com.example.fiurinee.domain.anniversary.entity.Anniversary;
import com.example.fiurinee.domain.inputMessage.dto.RequestMentDto;
import com.example.fiurinee.domain.inputMessage.dto.ResponseHarmonyWitnMentDto;
import com.example.fiurinee.domain.inputMessage.dto.ResponseMentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Model", description = "모델 관련 API")
public interface ModelApi {
    @Operation(
            summary = "회원 사용자의 멘트 입력",
            description = "사용자가 입력한 멘트를 보내주이소",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @ApiResponse(responseCode = "200", description = "꽃 추천 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @PostMapping("/{id}/ment")
    public ResponseEntity<List<ResponseMentDto>> inputMent(@Parameter(description = "회원 id") @PathVariable("id") Long id,
                                                           @Parameter(description = "유저가 입력한 멘트") @RequestBody RequestMentDto ment);

    @Operation(
            summary = "비회원의 멘트 입력",
            description = "비회원의 멘트 입력",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "꽃 추천 성공")
    @PostMapping("/ment")
    public ResponseEntity<List<ResponseMentDto>> inputMentNotMember(@Parameter(description = "입력한 멘트") @RequestBody String ment);

    @Operation(
            summary = "사용자가 꽃을 선택했을 시 호출",
            description = "사용자가 꽃을 선택했을 시 선택한 꽃의 id와 사용자 id 그리고 이전에 입력했던 사용자의 멘트를 보내주세요",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @ApiResponse(responseCode = "200", description = "멘트 추천 및 어울리는 꽃 추천 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @PostMapping("/{memberId}/{flowerId}")
    public ResponseEntity<ResponseHarmonyWitnMentDto> selectFlower(@Parameter(description = "회원의 아이디") @PathVariable ("memberId") Long memberId,
                                                                   @Parameter(description = "선택한 꽃의 아이디") @PathVariable ("flowerId") Long flowerId,
                                                                   @Parameter(description = "입력했던 멘트") @RequestBody String ment);
    @Operation(
            summary = "비회원 사용자가 꽃을 선택했을 시 호출",
            description = "꽃을 선택했을 시 선택한 꽃의 id 그리고 이전에 입력했던 사용자의 멘트를 보내주세요",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "멘트 추천 및 어울리는 꽃 추천 성공")
    @PostMapping("/{flowerId}/non")
    public ResponseEntity<ResponseHarmonyWitnMentDto> selectFlowerNonMember(@Parameter(description = "꽃의 아이디") @PathVariable ("flowerId") Long flowerId,
                                                                            @Parameter(description = "회원이 입력 했던 멘트") @RequestBody String ment);
}

