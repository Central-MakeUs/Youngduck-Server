package com.example.domains.screening.repository;

import com.example.domains.common.util.SliceUtil;
import com.example.domains.screening.entity.QScreening;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.enums.Category;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

@RequiredArgsConstructor
public class ScreeningRepositoryImpl implements ScreeningRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public Slice<Screening> querySliceScreening(String title, Category category, Pageable pageable) {
        List<Screening> query = queryFactory.selectFrom(QScreening.screening)
                .where(
                        containsTitle(title),
                        hasCategory(category)
                )
                .orderBy(QScreening.screening.screeningStartDate.asc()) // Adjust the sorting as needed
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return SliceUtil.toSlice(query, pageable);
    }

    private BooleanExpression containsHostName(String hostName) {
        return hostName != null ? QScreening.screening.hostInfo.hostName.contains(hostName) : null;
    }

    private BooleanExpression containsTitle(String title) {
        return title != null ? QScreening.screening.title.eq(title) : null;
    }

    private BooleanExpression hasCategory(Category category) {
        return category != null ? QScreening.screening.category.stringValue().eq(category.getValue()) : null;
    }


}
