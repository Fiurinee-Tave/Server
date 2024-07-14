package com.example.fiurinee.domain.dictionary.controller.api;

import com.example.fiurinee.domain.dictionary.dto.DictionaryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Dictionary", description = "사전 관련 API")
@RequestMapping("/dictionary")
public interface DictionaryApi {
    @Operation(
            summary = "모든 꽃 조회",
            description = "페이지 단위로 모든 꽃을 조회합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "꽃 조회 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @GetMapping
    ResponseEntity<List<DictionaryResponseDTO>> getAllFlowers(@RequestParam int page);

    @Operation(
            summary = "꽃 이름으로 검색",
            description = "이름에 특정 단어가 포함된 꽃을 검색합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "꽃 검색 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @GetMapping("/search")
    ResponseEntity<List<DictionaryResponseDTO>> searchFlowers(@RequestParam String name);

    @Operation(
            summary = "총 페이지 수 조회",
            description = "전체 꽃 목록의 총 페이지 수를 조회합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "총 페이지 수 조회 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @GetMapping("/total-pages")
    ResponseEntity<Integer> getTotalPages(@RequestParam int size);
}

