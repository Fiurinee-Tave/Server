package com.example.fiurinee.domain.dictionary.service;


import com.example.fiurinee.domain.dictionary.dto.DictionaryResponseDTO;
import com.example.fiurinee.domain.flower.entity.Flower;
import com.example.fiurinee.domain.flower.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final FlowerRepository flowerRepository;

    public List<DictionaryResponseDTO> getAllFlowers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Flower> flowers = flowerRepository.findDistinctAll(pageRequest);
        return flowers.stream()
                .map(DictionaryResponseDTO::of)
                .collect(Collectors.toList());
    }

    public List<DictionaryResponseDTO> searchFlowersByName(String name) {
        List<Flower> flowers = flowerRepository.findDistinctByNameContaining(name);
        return flowers.stream()
                .map(DictionaryResponseDTO::of)
                .collect(Collectors.toList());
    }
}
