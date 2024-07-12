package com.example.fiurinee.domain.flower.service;

import com.example.fiurinee.domain.flower.dto.FlowerResponseDTO;
import com.example.fiurinee.domain.flower.entity.Flower;
import com.example.fiurinee.domain.flower.repository.FlowerRepository;
import com.example.fiurinee.global.exception.CustomException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class FlowerService {
    @Autowired
    private FlowerRepository flowerRepository;

    public void parseAndSaveCsv(String filePath) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filePath), Charset.forName("EUC-KR")))) {
            List<String[]> records = reader.readAll();
            System.out.println("CSV 파일 읽기 성공: " + records.size() + "개의 레코드 발견");
            for (String[] record : records) {
                System.out.println("레코드: " + String.join(", ", record)); // 디버깅 로그 추가
                if (record.length < 8) { // 필드 개수 확인
                    System.err.println("컬럼이 부족한 레코드 건너뜀: " + String.join(", ", record));
                    continue; // 컬럼이 부족한 경우 건너뜁니다.
                }

                Long period = computePeriod(record[2], record[3]);
                if (period == null) {
                    System.err.println("유효하지 않은 period 값, 레코드 건너뜀: " + String.join(", ", record));
                    continue;
                }

                URL imageUrl = toURL(record[7]);
                if (imageUrl == null) {
                    System.err.println("유효하지 않은 URL 값, 레코드 건너뜁: " + String.join(", ", record));
                    continue;
                }

                Flower flower = Flower.builder()
                        .name(record[1])
                        .period(period)
                        .flowerLanguage(record[5]) // 꽃말 위치 변경
                        .explain(record[6]) // 설명 위치 변경
                        .image(imageUrl)
                        .build();

                flowerRepository.save(flower);
                System.out.println("저장 성공: " + flower.getName()); // 디버깅 로그 추가
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private Long computePeriod(String month, String day) {
        try {
            if (month != null && !month.isEmpty()) {
                System.out.println("month: " + month); // 디버깅 로그 추가
                Long period = Long.parseLong(month) * 100;
                if (day != null && !day.isEmpty()) {
                    System.out.println("day: " + day); // 디버깅 로그 추가
                    period += Long.parseLong(day);
                }
                return period;
            }
        } catch (NumberFormatException e) {
            System.err.println("숫자 형식 오류: " + e.getMessage());
        }
        return null;
    }

    private URL toURL(String urlString) {
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            System.err.println("유효하지 않은 URL: " + urlString);
            return null;
        }
    }

    public List<FlowerResponseDTO> getSeasonFlowers() {
        int currentMonth = LocalDate.now().getMonthValue();
        Long startPeriod = (long) (currentMonth * 100);
        Long endPeriod = startPeriod + 99; // 현재 월의 마지막 날을 포함하도록 설정
        List<Flower> flowers = flowerRepository.findByPeriodMonth(startPeriod, endPeriod);

        if (flowers.isEmpty()) {
            System.out.println("No flowers found for period month: " + currentMonth);
            return List.of();
        }

        // '카네이션'을 포함한 꽃 필터링
        List<Flower> carnationFlowers = flowers.stream()
                .filter(flower -> flower.getName().contains("카네이션"))
                .collect(Collectors.toList());

        // '장미'를 포함한 꽃 필터링
        List<Flower> roseFlowers = flowers.stream()
                .filter(flower -> flower.getName().contains("장미"))
                .collect(Collectors.toList());

        // '카네이션' 또는 '장미'를 포함하지 않은 나머지 꽃
        List<Flower> otherFlowers = flowers.stream()
                .filter(flower -> !flower.getName().contains("카네이션") && !flower.getName().contains("장미"))
                .collect(Collectors.toList());

        Random random = new Random();
        List<Flower> randomFlowers = new ArrayList<>();

        // 카네이션 중 하나를 무작위로 선택 (존재하는 경우)
        if (!carnationFlowers.isEmpty()) {
            randomFlowers.add(carnationFlowers.get(random.nextInt(carnationFlowers.size())));
        }

        // 장미 중 하나를 무작위로 선택 (존재하는 경우)
        if (!roseFlowers.isEmpty()) {
            randomFlowers.add(roseFlowers.get(random.nextInt(roseFlowers.size())));
        }

        // 나머지 꽃들 중에서 무작위로 선택하여 5개가 되도록 채움
        int remainingCount = 5 - randomFlowers.size();
        if (remainingCount > 0) {
            List<Flower> selectedOtherFlowers = otherFlowers.stream()
                    .skip(random.nextInt(otherFlowers.size() - remainingCount + 1)) // random skip
                    .limit(remainingCount)
                    .collect(Collectors.toList());
            randomFlowers.addAll(selectedOtherFlowers);
        }

        // 무작위로 선택된 꽃 리스트를 셔플
        Collections.shuffle(randomFlowers);

        return randomFlowers.stream()
                .map(FlowerResponseDTO::of)
                .collect(Collectors.toList());
    }

    public FlowerResponseDTO getTodayFlower() {
        LocalDate today = LocalDate.now();
        Long todayPeriod = (long) (today.getMonthValue() * 100 + today.getDayOfMonth());
        List<Flower> flowers = flowerRepository.findByPeriodMonth(todayPeriod, todayPeriod);

        if (flowers.isEmpty()) {
            System.out.println("No flowers found for period: " + todayPeriod);
            return null;
        }

        Random random = new Random();
        Flower randomFlower = flowers.get(random.nextInt(flowers.size()));

        return FlowerResponseDTO.of(randomFlower);
    }

    public Flower findByNameAndFlowerLanguage(String name,String flowerLangauge){

        name = name.trim();
        flowerLangauge = flowerLangauge.trim();

        log.info("Searching for flower with name: '{}' and flowerLanguage: '{}'", name, flowerLangauge);
        Flower flower = flowerRepository.findByNameAndFlowerLanguage(name,flowerLangauge).orElseThrow(
                () -> new CustomException("꽃 이름 또는 꽃말이 존재하지 않습니다."));

        return flower;
    }

    public Flower findByName(String name){

        name = name.trim();

        return flowerRepository.findByName(name).get(0);

    }

    public Flower findById(Long id){
        Flower flower = flowerRepository.findById(id).orElseThrow(
                () -> new CustomException("id가 일치하는 꽃이 존재하지 않습니다."));

        return flower;
    }
}
