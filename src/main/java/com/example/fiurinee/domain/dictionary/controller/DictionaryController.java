package com.example.fiurinee.domain.dictionary.controller;

import com.example.fiurinee.domain.dictionary.controller.api.DictionaryApi;
import com.example.fiurinee.domain.dictionary.dto.DictionaryResponseDTO;
import com.example.fiurinee.domain.dictionary.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DictionaryController implements DictionaryApi {
    private final DictionaryService dictionaryService;

    @Override
    public ResponseEntity<List<DictionaryResponseDTO>> getAllFlowers(int page) {
        int size = 30; // 한 페이지에 보여줄 꽃의 개수
        List<DictionaryResponseDTO> flowers = dictionaryService.getAllFlowers(page, size);
        return ResponseEntity.ok(flowers);
    }

    @Override
    public ResponseEntity<List<DictionaryResponseDTO>> searchFlowers(String name) {
        List<DictionaryResponseDTO> flowers = dictionaryService.searchFlowersByName(name);
        return ResponseEntity.ok(flowers);
    }

    @Override
    public ResponseEntity<Integer> getTotalPages(int size) {
        int totalPages = dictionaryService.getTotalPages(size);
        return ResponseEntity.ok(totalPages);
    }

}

