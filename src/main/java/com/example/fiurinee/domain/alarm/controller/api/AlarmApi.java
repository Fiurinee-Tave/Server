package com.example.fiurinee.domain.alarm.controller.api;

import com.example.fiurinee.domain.alarm.dto.AlarmRequestDTO;
import com.example.fiurinee.domain.alarm.dto.AlarmResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Alarm", description = "알람 관련 API")
@RequestMapping("/member")
public interface AlarmApi {
    @Operation(
            summary = "알람 상태 업데이트",
            description = "회원의 알람 상태를 업데이트합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "알람 상태 업데이트 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @PutMapping("/{id}/alarm")
    ResponseEntity<AlarmResponseDTO> updateAlarmStatus(@PathVariable Long id, @RequestBody AlarmRequestDTO alarmRequestDTO);
}
