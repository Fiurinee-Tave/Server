package com.example.fiurinee.integration.preferlist;

import com.example.fiurinee.domain.member.entity.Member;
import com.example.fiurinee.domain.preferList.entity.PreferList;
import com.example.fiurinee.domain.preferList.repository.PreferListRepository;
import com.example.fiurinee.domain.preferList.service.PreferListService;
import com.example.fiurinee.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PreferlistServiceTest {
    @Mock
    private PreferListRepository preferListRepository;

    @InjectMocks
    private PreferListService preferListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        // Given
        PreferList preferList = PreferList.builder()
                .build();

        // When
        preferListService.save(preferList);

        // Then
        verify(preferListRepository, times(1)).save(preferList);
    }

    @Test
    void testFindByMemberAndOrder() {
        // Given
        Member member = Member.builder()
                .build();
        Long order = 1L;

        PreferList preferList = PreferList.builder()
                .member(member)
                .preferOrder(order)
                .build();

        when(preferListRepository.findByMemberAndPreferOrder(member, order)).thenReturn(Optional.of(preferList));

        // When
        PreferList foundPreferList = preferListService.findByMemberAndOrder(member, order);

        // Then
        assertThat(foundPreferList).isNotNull();
        assertThat(foundPreferList).isEqualTo(preferList);
    }

    @Test
    void testFindByMemberAndOrderThrowsException() {
        // Given
        Member member = Member.builder()
                .build();
        Long order = 1L;

        when(preferListRepository.findByMemberAndPreferOrder(member, order)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomException.class, () -> preferListService.findByMemberAndOrder(member, order));
    }

    @Test
    void testDelete() {
        // Given
        PreferList preferList = PreferList.builder()
                .build();

        // When
        preferListService.delete(preferList);

        // Then
        verify(preferListRepository, times(1)).delete(preferList);
    }
}
