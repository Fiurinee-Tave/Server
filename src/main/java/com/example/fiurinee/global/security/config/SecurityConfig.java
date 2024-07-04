package com.example.fiurinee.global.security.config;

import com.example.fiurinee.domain.oauth2.service.OAuth2UserService;
import com.example.fiurinee.global.redis.utils.RedisUtil;
import com.example.fiurinee.global.security.filter.JwtVerifyFilter;
import com.example.fiurinee.global.security.handler.CommonLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2UserService oAuth2UserService;
    private final RedisUtil redisUtil;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // 모든 경로에 대해서 CORS 설정을 적용

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommonLoginSuccessHandler commonLoginSuccessHandler() {
        return new CommonLoginSuccessHandler(redisUtil);
    }

    @Bean
    public JwtVerifyFilter jwtVerifyFilter() {
        return new JwtVerifyFilter(redisUtil);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.NEVER);
        });

        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/oauth2/login", "/login/oauth2/code/**", "/oauth2/authorization/**","/login/oauth2/code/google","/member/*/refresh","/model/test",
                                "/swagger-ui/index.html", "/swagger-ui/**", "/v3/api-docs/**","/swagger-resources/**", "/v3/api-docs").permitAll()
                        //비회원 전용 api
                        .requestMatchers("/main/today","/main/season","/model/ment","/model/*/non").permitAll()
                        .anyRequest().authenticated());

        http.addFilterBefore(jwtVerifyFilter(), UsernamePasswordAuthenticationFilter.class);

        http.formLogin().disable();

        http.oauth2Login()
                .successHandler(commonLoginSuccessHandler())
                .userInfoEndpoint(userInfoEndpointConfig ->
                        userInfoEndpointConfig.userService(oAuth2UserService));

        return http.build();
    }
}
