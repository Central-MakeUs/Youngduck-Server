package com.example.domains.global.config;

import com.example.domains.diverseMovie.repository.DiverseMovieQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestQueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public DiverseMovieQueryRepository diverseMovieQueryRepository() {
        return new DiverseMovieQueryRepository(jpaQueryFactory());
    }
}