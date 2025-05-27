package com.glody.glody_platform.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private List<T> items;
    private PageInfo pageInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageInfo {
        private int page;
        private int size;
        private int totalPages;
        private long totalElements;
        private boolean hasNext;
        private boolean hasPrevious;
    }
}
