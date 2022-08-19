package com.example.demo.repositories;

import com.example.demo.data.BenchmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BenchmarkRepository extends JpaRepository<BenchmarkEntity, Long> {

    @Query("SELECT b FROM BenchmarkEntity b WHERE b.name = ?1")
    Optional<BenchmarkEntity> findByName(String name);
}
