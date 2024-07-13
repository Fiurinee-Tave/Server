package com.example.fiurinee.integration.member;

import com.example.fiurinee.domain.member.dto.MemberResponseDTO;
import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.member.repository.MemberRepository;
import com.example.fiurinee.domain.member.service.MemberService;
import com.example.fiurinee.domain.recommendFlower.entity.RecommendFlower;
import com.example.fiurinee.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        // Given
        Long id = 1L;
        Member member = Member.builder()
                .name("백지현")
                .build();

        when(memberRepository.findById(id)).thenReturn(Optional.of(member));

        // When
        Member foundMember = memberService.findById(id);

        // Then
        assertThat(foundMember).isNotNull();
        assertThat(foundMember).isEqualTo(member);
    }

    @Test
    void testFindByIdThrowsException() {
        // Given
        Long id = 1L;
        when(memberRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomException.class, () -> memberService.findById(id));
    }

    @Test
    void testDeleteMember() {
        // Given
        Long id = 1L;
        Member member = Member.builder()
                .name("백지현")
                .build();

        when(memberRepository.findById(id)).thenReturn(Optional.of(member));
        doNothing().when(memberRepository).delete(member);

        // When
        Boolean result = memberService.deleteMember(id);

        // Then
        assertThat(result).isTrue();
        verify(memberRepository, times(1)).delete(member);
    }

    @Test
    void testDeleteMemberThrowsException() {
        // Given
        Long id = 1L;
        when(memberRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomException.class, () -> memberService.deleteMember(id));
    }

    @Test
    void testGetMemberDtoById() {
        // Given
        Long id = 1L;
        Member member = Member.builder()
                .name("백지현")
                .build();
        member.setAnniversaries(new ArrayList<>());  // 초기화 추가
        member.setRecommendFlowers(new ArrayList<>());  // 초기화 추가

        when(memberRepository.findById(id)).thenReturn(Optional.of(member));

        // When
        MemberResponseDTO memberResponseDTO = memberService.getMemberDtoById(id);

        // Then
        assertThat(memberResponseDTO).isNotNull();
        assertThat(memberResponseDTO.getNickname()).isEqualTo("백지현");
    }

    @Test
    void testUpdatePhoneNumber() {
        // Given
        Long id = 1L;
        String newPhoneNumber = "01091935461";
        Member member = Member.builder()
                .name("백지현")
                .build();

        when(memberRepository.findById(id)).thenReturn(Optional.of(member));

        // When
        memberService.updatePhoneNumber(id, newPhoneNumber);

        // Then
        assertThat(member.getPhoneNumber()).isEqualTo(newPhoneNumber);
    }

    @Test
    void testFindAll() {
        // Given
        List<Member> members = List.of(
                Member.builder().name("백지현").build(),
                Member.builder().name("백지현").build()
        );

        when(memberRepository.findAll()).thenReturn(members);

        // When
        List<Member> foundMembers = memberService.findAll();

        // Then
        assertThat(foundMembers).isNotEmpty();
        assertThat(foundMembers.size()).isEqualTo(2);
        assertThat(foundMembers).isEqualTo(members);
    }

    @Test
    void testUpdateRecommendFlower() {
        // Given
        Member member = Member.builder()
                .name("백지현")
                .build();

        List<RecommendFlower> recommendFlowers = List.of(
                RecommendFlower.builder().recommendFlowerId(1L).build(),
                RecommendFlower.builder().recommendFlowerId(2L).build()
        );

        // When
        memberService.updateRecommendFlower(member, recommendFlowers);

        // Then
        assertThat(member.getRecommendFlowers()).isEqualTo(recommendFlowers);
    }
}
