package com.openelements.jmh.store.v2.repositories;

import com.openelements.jmh.store.v2.entities.EnvironmentEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvironmentRepository extends JpaRepository<EnvironmentEntity, UUID> {
}
