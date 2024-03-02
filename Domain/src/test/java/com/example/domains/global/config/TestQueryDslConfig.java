package com.example.domains.global.config;

import com.example.domains.diverseMovie.repository.DiverseMovieQueryRepository;
import com.example.domains.recommendedPopcorn.adaptor.RecommendedPopcornAdaptor;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import com.example.domains.recommendedPopcorn.repository.RecommendedPopcornQueryRepository;
import com.example.domains.recommendedPopcorn.repository.RecommendedPopcornRepository;
import com.example.domains.recommendedPopcorn.service.RecommendedPopcornService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Bean
    public RecommendedPopcornQueryRepository recommendedPopcornQueryRepository() {
        return new RecommendedPopcornQueryRepository(jpaQueryFactory());
    }
}