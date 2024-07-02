package com.example.fiurinee.domain.member.entity;

import com.example.fiurinee.domain.anniversary.entity.Anniversary;
import com.example.fiurinee.domain.inputMessage.entity.InputMessage;
import com.example.fiurinee.domain.preferList.entity.PreferList;
import com.example.fiurinee.domain.recommendComment.entity.RecommendComment;
import com.example.fiurinee.domain.recommendFlower.entity.RecommendFlower;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.net.URL;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String socialId;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String kakaoAccessToken;

    private int profileImage;

    private boolean alarm;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @OrderBy("preferOrder ASC")
    private List<PreferList> preferLists;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Anniversary> anniversaries;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<RecommendFlower> recommendFlowers;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<InputMessage> inputMessages;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RecommendComment> recommendComments;

    @Builder
    private Member(String email, String name, String socialId, Role role, String kakaoAccessToken, int profileImage, boolean alarm) {
        this.email = email;
        this.name = name;
        this.socialId = socialId;
        this.role = role;
        this.kakaoAccessToken = kakaoAccessToken;
        this.profileImage = profileImage;
        this.alarm = alarm;
    }

    public static Member createMember(String email, String name, String socialId, Role role, String kakaoAccessToken, int profileImage, boolean alarm) {
        return new Member(email, name, socialId, role, kakaoAccessToken,profileImage, alarm);
    }

    public void updateKakaoToken(String kakaoAccessToken){
        this.kakaoAccessToken = kakaoAccessToken;
    }

    public void updateProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }
}
