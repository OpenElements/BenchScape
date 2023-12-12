package com.openelements.benchscape.server.store.util;

import jakarta.persistence.EntityManager;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SpringTestSupportService {

    private final EntityManager entityManager;


    @Autowired
    public SpringTestSupportService(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager, "entityManager must not be null");
    }

    public void clearDatabase() {
        entityManager.createQuery("DELETE FROM Benchmark").executeUpdate();
        entityManager.createQuery("DELETE FROM Environment").executeUpdate();
    }
}
