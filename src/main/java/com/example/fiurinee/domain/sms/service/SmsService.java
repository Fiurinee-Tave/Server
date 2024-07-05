package com.example.fiurinee.domain.sms.service;

import com.example.fiurinee.domain.anniversary.entity.Anniversary;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.global.exception.CustomException;
import com.example.fiurinee.global.redis.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final RedisUtil redisUtil;

    @Value("${SMS_API_KEY}")
    private String apiKey;

    @Value("${SMS_SECRET_KEY}")
    private String apiSecret;

    private String createRandomNumber() {
        Random rand = new Random();
        String randomNum = "";
        for (int i = 0; i < 4; i++) {
            String random = Integer.toString(rand.nextInt(10));
            randomNum += random;
        }

        return randomNum;
    }

    private HashMap<String, String> makeParams(String to, String randomNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("from", "01051467622");
        params.put("type", "SMS");
        params.put("app_version", "Fiurinee 1.0");
        params.put("to", to);
        params.put("text", "[Fiurinee] 인증번호"+ "[" + randomNum + "]를 입력해 주세요.");
        return params;
    }

    // 인증번호 전송하기
    @Transactional
    public void certificateSMS(String phoneNumber) {
        Message coolsms = new Message(apiKey, apiSecret);

        // 랜덤한 인증 번호 생성
        String randomNum = createRandomNumber();

        //인증 번호를 redis에 저장 만료시간은 5분
        redisUtil.set(phoneNumber, randomNum,5);

        // 발신 정보 설정
        HashMap<String, String> params = makeParams(phoneNumber, randomNum);

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Transactional
    // 기념일 당일 전송하기
    public void sendSMS(Member member, Anniversary anniversary) {
        Message coolsms = new Message(apiKey, apiSecret);

        HashMap<String, String> params = new HashMap<>();

        params.put("from", "01051467622");
        params.put("type", "SMS");
        params.put("app_version", "Fiurinee 1.0");
        params.put("to", member.getPhoneNumber());
        params.put("text", "[Fiurinee] 안녕하세요 "+ member.getName()+"님!\n오늘은 "+anniversary.getName()+"기념일 입니다!");

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Transactional
    // 기념일 3일전  전송하기
    public void preSendSMS(Member member, Anniversary anniversary) {
        Message coolsms = new Message(apiKey, apiSecret);

        HashMap<String, String> params = new HashMap<>();

        params.put("from", "01051467622");
        params.put("type", "SMS");
        params.put("app_version", "Fiurinee 1.0");
        params.put("to", member.getPhoneNumber());
        params.put("text", "[Fiurinee] 안녕하세요 "+ member.getName()+"님!\n오늘은 "+anniversary.getName()+"기념일 3일전입니다!");

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            throw new CustomException(e.getMessage());
        }
    }

}