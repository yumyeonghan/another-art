package com.imagine.another_arts.web.art;

import com.imagine.another_arts.domain.art.service.ArtService;
import com.imagine.another_arts.domain.art.service.dto.AuctionArtResponse;
import com.imagine.another_arts.exception.ArtNotFoundException;
import com.imagine.another_arts.web.SimpleSucessResponse;
import com.imagine.another_arts.web.art.dto.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@SuppressWarnings("unchecked")
public class ArtController {
    private final ArtService artService;
    private static final int SLICE_PER_PAGE = 20;

    @PostMapping(value = "/register/auctionart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "경매 작품 등록 API", notes = "경매 전용 작품 등록 (multipart/form-data) -> 폼 데이터 전부 작성 (NOT NULL)")
    public SimpleSucessResponse registerAuctionArt(AuctionArtRegisterRequest auctionArtRegisterRequest) {
        artService.registerAuctionArt(auctionArtRegisterRequest.toServiceDto());
        return new SimpleSucessResponse(true);
    }

    @PostMapping(value = "/register/generalart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "일반 작품 등록 API", notes = "일반 판매용 작품 등록 (multipart/form-data) -> 폼 데이터 전부 작성 (NOT NULL)")
    public SimpleSucessResponse registerGeneralArt(GeneralArtRegisterRequest generalArtRegisterRequest) {
        artService.registerGeneralArt(generalArtRegisterRequest.toServiceDto());
        return new SimpleSucessResponse(true);
    }

    @GetMapping("/art/{artId}")
    @ApiOperation(value = "작품 조회 API", notes = "PathVariable로 작품의 PK를 보내고 그에 따른 작품 조회")
    public <T> SpecificArtResponse<T> getArt(@PathVariable Long artId) {
        return new SpecificArtResponse<>(true, artService.getSpecificArt(artId));
    }

    @PutMapping("/edit/art/{artId}")
    @ApiOperation(value = "작품 정보 변경 API", notes = "작품명, 작품 설명 변경")
    public SimpleSucessResponse editArt(
            @PathVariable Long artId,
            @ModelAttribute ArtEditRequest artEditRequest
    ) {
        artService.editArt(artId, artEditRequest.toServiceDto());
        return new SimpleSucessResponse(true);
    }

    @DeleteMapping("/delete/art/{artId}")
    @ApiOperation(value = "작품 삭제 API", notes = "작품 삭제 (경매 작품은 삭제 불가능)")
    public SimpleSucessResponse deleteArt(@PathVariable Long artId) {
        artService.deleteArt(artId);
        return new SimpleSucessResponse(true);
    }

    @PutMapping("/add/hashtag/{artId}")
    @ApiOperation(value = "작품 해시태그 추가 API", notes = "일반 판매용 작품 등록 (multipart/form-data)")
    public SimpleSucessResponse addHashtag(
            @PathVariable Long artId,
            @Valid @ModelAttribute HashtagListRequest hashtagListRequest
    ) {
        artService.addHashtag(artId, hashtagListRequest.getHashtagList());
        return new SimpleSucessResponse(true);
    }

    @DeleteMapping("/delete/hashtag/{artId}")
    @ApiOperation(value = "작품 해시태그 삭제 API", notes = "작품의 해시태그 리스트중에 삭제할 해시태그를 삭제 요청")
    public SimpleSucessResponse deleteHashtag(
            @PathVariable Long artId,
            @RequestParam @ApiParam(value = "삭제할 해시태그", required = true) String hashtag
    ) {
        artService.deleteHashtag(artId, hashtag);
        return new SimpleSucessResponse(true);
    }

    @GetMapping("/main/art")
    @ApiOperation(value = "메인페이지 작품 정렬 API", notes = "작품 데이터들을 페이징 개수만큼 응답 (정렬 기준 존재)")
    public SortedArtResponse<List<AuctionArtResponse>> mainArtList(
            @ApiParam(name = "sort", value = "정렬 기준 [date, rdate, price, rprice, count, rcount]", required = true)
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @ApiParam(name = "scroll", value = "스크롤 이벤트 발생 시 +1해서 요청", required = true)
            @RequestParam(value = "scroll", defaultValue = "0") Integer scroll
    ) {
        PageRequest pageRequest = PageRequest.of(scroll, SLICE_PER_PAGE);
        List<AuctionArtResponse> sortedAuctionArtList = artService.getArtListTypeAuction(sort, pageRequest);

        if (sortedAuctionArtList.size() == 0) {
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
