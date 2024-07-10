package com.example.fiurinee.global.security.handler;

import com.example.fiurinee.domain.jwt.utils.JwtConstants;
import com.example.fiurinee.domain.jwt.utils.JwtUtils;
import com.example.fiurinee.domain.member.entity.PrincipalDetail;
import com.example.fiurinee.global.redis.utils.RedisUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommonLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RedisUtil redisUtil;

    public CommonLoginSuccessHandler(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();

        log.info("authentication.getPrincipal() = {}", principal);

        String refreshToken = JwtUtils.generateToken(principal.getMemberInfo(), JwtConstants.REFRESH_EXP_TIME);
        String accessToken = JwtUtils.generateToken(principal.getMemberInfo(), JwtConstants.ACCESS_EXP_TIME);

        // principal에서 로그인하는 유저의 이메일을 기반으로 refresh 토큰을 redis에 저장
        redisUtil.set(principal.getMemberDto().email(),refreshToken,60*24);

        String redirectUri = null;

        if(principal.getMemberDto().phoneNumber() == null) {
            redirectUri = "http://localhost:3000/phone";
        }
        else{
            redirectUri = "http://localhost:3000/auth";
        }

        Long memberId = principal.getMemberDto().id();

        String redirectUrl = String.format("%s?member_id=%d&access_token=%s&refresh_token=%s", redirectUri, memberId, accessToken, refreshToken);
        response.sendRedirect(redirectUrl);
    }
}
