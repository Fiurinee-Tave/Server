package com.example.fiurinee.domain.oauth2.service;

import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.member.entity.PrincipalDetail;
import com.example.fiurinee.domain.member.entity.Role;
import com.example.fiurinee.domain.member.dto.MemberDto;
import com.example.fiurinee.domain.member.repository.MemberRepository;
import com.example.fiurinee.domain.oauth2.entity.KakaoUserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuth2AccessToken accessToken = userRequest.getAccessToken();

        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(attributes);
        String socialId = kakaoUserInfo.getSocialId();
        String name = kakaoUserInfo.getName();
        String email = kakaoUserInfo.getEmail();

        Optional<Member> bySocialId = memberRepository.findBySocialId(socialId);
        Member member = bySocialId.orElseGet(() -> saveSocialMember(socialId, name,email, accessToken.getTokenValue()));
        updateAccessToken(member, accessToken.getTokenValue());
        MemberDto memberDto = new MemberDto(member.getId(), member.getEmail(), member.getName(), member.getSocialId(), member.getRole(), member.getPhoneNumber());

        return new PrincipalDetail(
                memberDto,
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getValue())),
                attributes
        );
    }

    public Member saveSocialMember(String socialId, String name, String email, String kakaoAccessToken) {
        int defaultProfileImage = 11;
        boolean defaultAlarm = false;
        Member newMember = Member.createMember(email, name, socialId, Role.USER,kakaoAccessToken, defaultProfileImage, defaultAlarm);
        return memberRepository.save(newMember);
    }

    public void updateAccessToken(Member member, String accessToken){
        member.updateKakaoToken(accessToken);
    }
}
