package com.example.fiurinee.domain.alarm.controller;

import com.example.fiurinee.domain.alarm.controller.api.AlarmApi;
import com.example.fiurinee.domain.alarm.dto.AlarmRequestDTO;
import com.example.fiurinee.domain.alarm.dto.AlarmResponseDTO;
import com.example.fiurinee.domain.alarm.service.AlarmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlarmController implements AlarmApi {
    private final AlarmService alarmService;

    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @Override
    public ResponseEntity<AlarmResponseDTO> updateAlarmStatus(Long id, AlarmRequestDTO alarmRequestDTO) {
        AlarmResponseDTO responseDTO = alarmService.updateAlarmStatus(id, alarmRequestDTO.isAlarm());
        return ResponseEntity.ok(responseDTO);
    }

}
