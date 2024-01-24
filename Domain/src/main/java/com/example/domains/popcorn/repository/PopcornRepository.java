package com.example.domains.popcorn.repository;

import com.example.domains.popcorn.entity.Popcorn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopcornRepository extends JpaRepository<Popcorn,Long> {
}
