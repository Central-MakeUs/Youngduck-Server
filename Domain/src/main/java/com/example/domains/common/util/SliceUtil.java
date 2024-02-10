package com.example.domains.common.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class SliceUtil {
    public static <T> Slice<T> toSlice(List<T> contents, Pageable pageable) {
        boolean hasNext = hasNext(contents, pageable);
        return new SliceImpl<>(
                hasNext ? getContent(contents, pageable) : contents, pageable, hasNext);
    }

    // 다음 페이지 있는지 확인
    private static <T> boolean hasNext(List<T> content, Pageable pageable) {
        return pageable.isPaged() && content.size() > pageable.getPageSize();
    }

    // 데이터 1개 빼고 반환
    private static <T> List<T> getContent(List<T> content, Pageable pageable) {
        return content.subList(0, pageable.getPageSize());
    }
}
