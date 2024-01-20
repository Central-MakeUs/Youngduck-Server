package com.example.domains.screening.repository;

import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.enums.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long>, ScreeningRepositoryCustom {
    List<Screening> findByScreeningStartDate(LocalDateTime minusDays);
}
