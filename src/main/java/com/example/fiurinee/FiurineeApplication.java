package com.example.fiurinee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.fiurinee.domain.flower.service.FlowerService;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FiurineeApplication implements CommandLineRunner {

	@Autowired
	private FlowerService flowerService;

	public static void main(String[] args) {
		SpringApplication.run(FiurineeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// CSV 파일 경로
//		String filePath = "src/main/resources/flowers.csv";
		// CSV 파일을 파싱하고 데이터베이스에 저장합니다.
//		flowerService.parseAndSaveCsv(filePath);
	}
}

//http://localhost:8080/oauth2/authorization/kakao