package com.imagine.another_arts.web.art;

import com.imagine.another_arts.domain.art.service.ArtService;
import com.imagine.another_arts.domain.art.service.dto.AuctionArtResponseDto;
import com.imagine.another_arts.exception.ArtNotFoundException;
import com.imagine.another_arts.web.art.dto.AuctionArtRegisterForm;
import com.imagine.another_arts.web.art.dto.GeneralArtRegisterForm;
import com.imagine.another_arts.web.art.dto.SortedArtResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@SuppressWarnings("unchecked")
public class ArtController {
    private final ArtService artService;
    private static final int SLICE_PER_PAGE = 20;

    @Data
    @AllArgsConstructor
    static class Success { // 간단한 boolean 응답 양식
        private boolean successResponse;
    }

    @PostMapping(value = "/register/auctionart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "경매 작품 등록 API", notes = "경매 전용 작품 등록 (multipart/form-data)")
    public Success registerAuctionArt(AuctionArtRegisterForm auctionArtRegisterForm) {
        artService.registerAuctionArt(auctionArtRegisterForm);
        return new Success(true);
    }

    @PostMapping(value = "/register/generalart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "일반 작품 등록 API", notes = "일반 판매용 작품 등록 (multipart/form-data)")
    public Success registerGeneralArt(GeneralArtRegisterForm generalArtRegisterForm) {
        artService.registerGeneralArt(generalArtRegisterForm);
        return new Success(true);
    }

    @GetMapping("/main/art")
    @ApiOperation(value = "메인페이지 작품 정렬 API", notes = "작품 데이터들을 페이징 개수만큼 응답 (정렬 기준 존재)")
    public SortedArtResponse<List<AuctionArtResponseDto>> mainArtList(
            @ApiParam(name = "sort", value = "정렬 기준 [date, rdate, price, rprice, count, rcount]", required = true)
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @ApiParam(name = "scroll", value = "스크롤 이벤트 발생 시 +1해서 요청", required = true)
            @RequestParam(value = "scroll", defaultValue = "0") Integer scroll
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
            @ApiParam(name = "hashtag", value = "검색 해시태그", required = true)
            @RequestParam(value = "hashtag") String hashtag,
            @ApiParam(name = "type", value = "작품 타입 [auction/general]", required = true)
            @RequestParam(value = "type") String type,
            @ApiParam(name = "sort", value = "정렬 기준 [AUCTION = date, rdate, price, rprice, count, rcount / GENERAL = date, rdate, price, rprice, like, rlike]", required = true)
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @ApiParam(name = "scroll", value = "스크롤 이벤트 발생 시 +1해서 요청", required = true)
            @RequestParam(value = "scroll", defaultValue = "0") Integer scroll
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
