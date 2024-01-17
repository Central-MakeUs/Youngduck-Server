package com.example.domains.screening.repository;

import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.enums.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Screening, Long>, ScreeningRepositoryCustom {
}
