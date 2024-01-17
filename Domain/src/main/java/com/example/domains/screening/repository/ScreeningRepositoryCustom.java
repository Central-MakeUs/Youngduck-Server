package com.example.domains.screening.repository;

import com.example.domains.common.util.SliceResponse;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.enums.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface ScreeningRepositoryCustom {
    SliceResponse<Screening> querySliceScreening(String title, Category  category, Pageable pageable);
    SliceResponse<Screening> querySliceScreeningByDate(String title, Category category, Pageable pageable);
}
