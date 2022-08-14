package com.imagine.another_arts.web.art;

import com.imagine.another_arts.domain.art.service.ArtService;
import com.imagine.another_arts.domain.art.service.dto.AuctionArtResponseDto;
import com.imagine.another_arts.exception.ArtNotFoundException;
import com.imagine.another_arts.web.art.dto.SortedArtResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@SuppressWarnings("unchecked")
public class ArtController {
    private final ArtService artService;
    private static final int SLICE_PER_PAGE = 20;

    @GetMapping("/main/art")
    @ApiOperation(value = "메인페이지 작품 정렬 API", notes = "작품 데이터들을 페이징 개수만큼 응답 (정렬 기준 존재)")
    public SortedArtResponse<List<AuctionArtResponseDto>> mainArtList(
            @RequestParam(value = "sort", defaultValue = "date") @ApiParam(value = "정렬 기준 [date, rdate, price, rprice, count, rcount]") String sort,
            @RequestParam(value = "scroll", defaultValue = "0") @ApiParam(value = "스크롤 이벤트", example = "0") Integer scroll
    ){
        PageRequest pageRequest = PageRequest.of(scroll, SLICE_PER_PAGE);
        List<AuctionArtResponseDto> sortedAuctionArtList = artService.getArtListTypeAuction(sort, pageRequest);

        if(sortedAuctionArtList.size() == 0){
            throw new ArtNotFoundException("더이상 작품이 존재하지 않습니다");
        }

        return new SortedArtResponse<>(true, sortedAuctionArtList.size(), sortedAuctionArtList);
    }

    @GetMapping("/search/art")
    @ApiOperation(value = "해시태그 검색 API", notes = "해시태그로 검색된 작품 데이터들을 페이징 개수만큼 응답 (정렬 기준 존재)")
    public <T> SortedArtResponse<List<T>> searchArtList(
            @RequestParam(value = "hashtag") @ApiParam(value = "검색 해시태그") String hashtag,
            @RequestParam(value = "type") @ApiParam(value = "작품 타입 [AUCTION/GENERAL]") String type,
            @RequestParam(value = "sort", defaultValue = "date") @ApiParam(value = "정렬 기준 [AUCTION = date, rdate, price, rprice, count, rcount / GENERAL = date, rdate, price, rprice, like, rlike]") String sort,
            @RequestParam(value = "scroll", defaultValue = "0") @ApiParam(value = "스크롤 이벤트", example = "0") Integer scroll
    ) {
        PageRequest pageRequest = PageRequest.of(scroll, SLICE_PER_PAGE);
        List<T> sortedArtListBySearch;

        if (type.equals("auction")) {
            sortedArtListBySearch = (List<T>) artService.getArtListTypeAuctionSearchedByHashtag(hashtag, sort, pageRequest);
        } else {
            sortedArtListBySearch = (List<T>) artService.getArtListTypeGeneralSearchedByHashtag(hashtag, sort, pageRequest);
        }

        if(sortedArtListBySearch.size() == 0){
            throw new ArtNotFoundException("더이상 작품이 존재하지 않습니다");
        }

        return new SortedArtResponse<>(true, sortedArtListBySearch.size(), sortedArtListBySearch);
    }
}
