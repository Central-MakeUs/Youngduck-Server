package com.example.domains.screening.repository;

import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.enums.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface ScreeningRepositoryCustom {
    Slice<Screening> querySliceScreening(String title, Category  category, Pageable pageable);
}
