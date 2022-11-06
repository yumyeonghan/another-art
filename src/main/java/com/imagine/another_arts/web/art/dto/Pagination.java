package com.imagine.another_arts.web.art.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pagination {
    public static final int RANGE_PER_PAGE = 10; // 페이지 상에서 range 범위
    private Long totalElements; // 전체 스터디 데이터 개수
    private int totalPages; // 전체 페이지 개수
    private int currentPage; // 현재 페이지
    private int rangeStartNumber; // 현재 범위 시작 번호
    private int rangeEndNumber; // 현재 범위 마지막 번호
    private boolean prev; // 이전 range 존재 여부
    private boolean next; // 다음 range 존재 여부

    public Pagination(Long totalElements, int totalPages, int currentPage) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;

        this.rangeStartNumber = RANGE_PER_PAGE * (currentPage / RANGE_PER_PAGE) + 1;
        this.rangeEndNumber = RANGE_PER_PAGE * (currentPage / RANGE_PER_PAGE) + RANGE_PER_PAGE;

        if (currentPage % RANGE_PER_PAGE == 0) {
            rangeStartNumber = currentPage - (RANGE_PER_PAGE - 1);
            rangeEndNumber = currentPage;
        } else if (rangeEndNumber > totalPages) {
            rangeEndNumber = totalPages;
        }

        this.prev = this.rangeStartNumber > RANGE_PER_PAGE;
        this.next = this.rangeEndNumber + 1 <= totalPages;
    }
}
