package com.example.fiurinee.domain.flower.repository;

import com.example.fiurinee.domain.flower.entity.Flower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long> {

    @Query("SELECT f FROM Flower f WHERE f.period BETWEEN :startPeriod AND :endPeriod")
    List<Flower> findByPeriodMonth(@Param("startPeriod") Long startPeriod, @Param("endPeriod") Long endPeriod);

    List<Flower> findByNameAndFlowerLanguage(String name,String flowerLanguage);

    List<Flower> findByName(String name);

    @Query("SELECT DISTINCT f FROM Flower f WHERE f.name LIKE %:name%")
    List<Flower> findDistinctByNameContaining(@Param("name") String name);

    @Query("SELECT DISTINCT f FROM Flower f")
    Page<Flower> findDistinctAll(Pageable pageable);
}
