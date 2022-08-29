package com.imagine.another_arts.web.art;

import com.imagine.another_arts.domain.art.service.ArtService;
import com.imagine.another_arts.domain.art.service.dto.response.AuctionArtResponse;
import com.imagine.another_arts.domain.art.service.dto.response.GeneralArtResponse;
import com.imagine.another_arts.web.art.dto.request.*;
import com.imagine.another_arts.web.art.dto.response.SimpleArtSuccessResponse;
import com.imagine.another_arts.web.art.dto.response.SingleArtResponse;
import com.imagine.another_arts.web.art.dto.response.SortedArtResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/art", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "작품 등록 API", notes = "artType(auction, general)을 통해서 경매/일반 작품 등록 구분 -> 경매 작품일 경우 [경매 시작/종료 날짜] 반드시 기입")
    public ResponseEntity<SimpleArtSuccessResponse> artRegister(ArtRegisterRequest artRegisterRequest) {
        Long saveArtId;

        if (artRegisterRequest.getSaleType().equals("auction")) {
            saveArtId = artService.registerArt(artRegisterRequest.toAuctioArtDto());
        } else {
            saveArtId = artService.registerArt(artRegisterRequest.toGeneralArtDto());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Location", "/api/art/" + saveArtId);

        return new ResponseEntity<>(new SimpleArtSuccessResponse(saveArtId), headers, HttpStatus.CREATED);
    }

    @GetMapping("/art/{artId}")
    @ApiOperation(value = "작품 조회 API", notes = "PathVariable로 작품의 PK를 보내고 그에 따른 작품 조회")
    public <T> SingleArtResponse<T> getSingleArt(@PathVariable Long artId) {
        return new SingleArtResponse<>(artService.getSingleArt(artId));
    }

    @PatchMapping("/art/{artId}")
    @ApiOperation(value = "작품 정보 수정 API", notes = "작품명, 작품 설명 변경")
    public ResponseEntity<Void> editArt(
            @PathVariable Long artId,
            @ModelAttribute ArtEditRequest artEditRequest
    ) {
        artService.editArt(artId, artEditRequest.toServiceDto());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/art/{artId}")
    @ApiOperation(value = "작품 삭제 API", notes = "작품 삭제 (경매 작품은 삭제 불가능)")
    public ResponseEntity<Void> deleteArt(@PathVariable Long artId) {
        artService.deleteArt(artId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/hashtag/{artId}")
    @ApiOperation(value = "작품 해시태그 추가 API", notes = "일반 판매용 작품 등록 (multipart/form-data)")
    public ResponseEntity<Void> addHashtag(
            @PathVariable Long artId,
            @Valid @ModelAttribute HashtagUpdateRequest hashtagUpdateRequest
    ) {
        artService.addHashtag(artId, hashtagUpdateRequest.getHashtagList());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/hashtag/{artId}")
    @ApiOperation(value = "작품 해시태그 삭제 API", notes = "작품의 해시태그 리스트중에 삭제할 해시태그를 삭제 요청")
    public ResponseEntity<Void> deleteHashtag(
            @PathVariable Long artId,
            @RequestParam @ApiParam(value = "삭제할 해시태그", required = true) String hashtag
    ) {
        artService.deleteHashtag(artId, hashtag);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/main/arts")
    @ApiOperation(value = "메인페이지 경매 작품 조회 API", notes = "경매 작품 데이터들을 페이징 개수만큼 응답 (정렬 기준 존재)")
    public SortedArtResponse<List<AuctionArtResponse>> mainSortArtList(@ModelAttribute MainArtSortRequest mainArtSortRequest) {
        PageRequest pageRequest = PageRequest.of(mainArtSortRequest.getScroll(), SLICE_PER_PAGE);

        List<AuctionArtResponse> sortedAuctionArtList = artService.getSortedAuctionArtList(mainArtSortRequest.getSort(), pageRequest);
        return new SortedArtResponse<>(sortedAuctionArtList.size(), sortedAuctionArtList);
    }

    @GetMapping("/hashtag/arts")
    @ApiOperation(value = "해시태그를 통한 작품 조회 API", notes = "해시태그로 검색된 작품 데이터들을 페이징 개수만큼 응답 (정렬 기준 존재)")
    public <T> SortedArtResponse<T> hashtagSearchArtList(@ModelAttribute HashtagSearchArtRequest hashtagSearchArtRequest) {
        PageRequest pageRequest = PageRequest.of(hashtagSearchArtRequest.getScroll(), SLICE_PER_PAGE);

        if (hashtagSearchArtRequest.getType().equals("auction")) {
            List<AuctionArtResponse> auctionArtListSearchByHashtag = artService.getAuctionArtListSearchByHashtag(
                    hashtagSearchArtRequest.getHashtag(),
                    hashtagSearchArtRequest.getSort(),
                    pageRequest
            );
            return new SortedArtResponse<>(auctionArtListSearchByHashtag.size(), (T)auctionArtListSearchByHashtag);
        } else {
            List<GeneralArtResponse> generalArtListSearchByHashtag = artService.getGeneralArtListSearchByHashtag(
                    hashtagSearchArtRequest.getHashtag(),
                    hashtagSearchArtRequest.getSort(),
                    pageRequest
            );
            return new SortedArtResponse<>(generalArtListSearchByHashtag.size(), (T)generalArtListSearchByHashtag);
        }
    }

    @GetMapping("/keyword/arts")
    @ApiOperation(value = "키워드를 통한 작품 조회 API", notes = "키워드로 검색된 작품 데이터들을 페이징 개수만큼 응답 (정렬 기준 존재)")
    public <T> SortedArtResponse<T> keywordSearchArtList(@ModelAttribute KeywordSearchArtRequest keywordSearchArtRequest) {
        PageRequest pageRequest = PageRequest.of(keywordSearchArtRequest.getScroll(), SLICE_PER_PAGE);

        if (keywordSearchArtRequest.getType().equals("auction")) {
            List<AuctionArtResponse> auctionArtListSearchByKeyword = artService.getAuctionArtListSearchByKeyword(
                    "%" + keywordSearchArtRequest.getKeyword() + "%",
                    keywordSearchArtRequest.getSort(),
                    pageRequest
            );
            return new SortedArtResponse<>(auctionArtListSearchByKeyword.size(), (T)auctionArtListSearchByKeyword);
        } else {
            List<GeneralArtResponse> generalArtListSearchByKeyword = artService.getGeneralArtListSearchByKeyword(
                    "%" + keywordSearchArtRequest.getKeyword() + "%",
                    keywordSearchArtRequest.getSort(),
                    pageRequest
            );
            return new SortedArtResponse<>(generalArtListSearchByKeyword.size(), (T)generalArtListSearchByKeyword);
        }
    }
}
