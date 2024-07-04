package com.example.fiurinee.global.security.filter;

import com.example.fiurinee.domain.jwt.exception.CustomExpiredJwtException;
import com.example.fiurinee.domain.jwt.utils.JwtConstants;
import com.example.fiurinee.domain.jwt.utils.JwtUtils;
import com.example.fiurinee.global.exception.CustomException;
import com.example.fiurinee.global.redis.utils.RedisUtil;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtVerifyFilter extends OncePerRequestFilter {
    // 소셜 로그인 관련 URI 추가
    private static final String[] whitelist = {"/signUp", "/login", "/refresh", "/", "/index.html", "/oauth2/login", "/login/oauth2/code/*", "/oauth2/authorization/kakao","/member/*/refresh","/model/test",
            //Swagger
            "/swagger-ui/index.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/v3/api-docs",
            //비회원 전용 api
            "/main/today","/main/season","/model/ment","/model/*/non"};
    private final RedisUtil redisUtil;


    private static void checkAuthorizationHeader(String header) {
        if (header == null) {
            throw new CustomException("토큰이 전달되지 않았습니다");
        } else if (!header.startsWith(JwtConstants.JWT_TYPE)) {
            throw new CustomException("BEARER 로 시작하지 않는 올바르지 않은 토큰 형식입니다");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        return PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(JwtConstants.JWT_HEADER);

        try {
            checkAuthorizationHeader(authHeader);
            String token = JwtUtils.getTokenFromHeader(authHeader);
            System.out.println("token = " + token);
            if (redisUtil.hasKeyBlackList(token)){
                throw new CustomException("로그아웃된 사용자 입니다.");
            }
            Authentication authentication = JwtUtils.getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);    // 다음 필터로 이동
        } catch (Exception e) {
            Gson gson = new Gson();
            String json = "";
            if (e instanceof CustomExpiredJwtException) {
                json = gson.toJson(Map.of("Token_Expired", e.getMessage()));
                response.setStatus(401);
            } else {
                json = gson.toJson(Map.of("error", e.getMessage()));
                response.setStatus(500);
            }

            response.setContentType("application/json; charset=UTF-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(json);
            printWriter.close();
        }
    }
}
