package com.imagine.another_arts.web.art;

import com.imagine.another_arts.domain.art.service.ArtService;
import com.imagine.another_arts.exception.AnotherArtException;
import com.imagine.another_arts.web.art.dto.request.*;
import com.imagine.another_arts.web.art.dto.response.SimpleArtSuccessResponse;
import com.imagine.another_arts.web.art.dto.response.SingleArtResponse;
import com.imagine.another_arts.web.art.dto.response.SortedArtResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.imagine.another_arts.exception.AnotherArtErrorCode.ILLEGAL_URL_REQUEST;

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
        Long saveArtId = artRegisterRequest.getSaleType().equals("auction")
                ? artService.registerArt(artRegisterRequest.toAuctioArtDto())
                : artService.registerArt(artRegisterRequest.toGeneralArtDto());

        return ResponseEntity
                .created(URI.create("/api/art/" + saveArtId))
                .body(new SimpleArtSuccessResponse(saveArtId));
    }

    @GetMapping("/art/{artId}")
    @ApiOperation(value = "작품 조회 API", notes = "PathVariable로 작품의 PK를 보내고 그에 따른 작품을 조회하는 API")
    public SingleArtResponse<ArtResponse> getSingleArt(@PathVariable Long artId) {
        return new SingleArtResponse<>(artService.getSingleArt(artId));
    }

    @PatchMapping("/art/{artId}")
    @ApiOperation(value = "작품 정보 수정 API", notes = "작품명, 작품 설명을 변경하는 API")
    public ResponseEntity<Void> editArt(@PathVariable Long artId, @RequestBody ArtEditRequest editRequest) {
        artService.editArt(artId, editRequest.getUpdateName(), editRequest.getUpdateDescription());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/art/{artId}")
    @ApiOperation(value = "작품 삭제 API", notes = "artId에 매핑되는 작품을 삭제하는 API")
    public ResponseEntity<Void> deleteArt(@PathVariable Long artId) {
        artService.deleteArt(artId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/hashtag/{artId}")
    @ApiOperation(value = "작품 해시태그 추가 API", notes = "artId에 매핑되는 작품에 해시태그를 추가하는 API")
    public ResponseEntity<Void> addHashtag(@PathVariable Long artId, @Valid @RequestBody ArtHashtagUpdateRequest artHashtagUpdateRequest) {
        artService.addHashtag(artId, artHashtagUpdateRequest.getHashtagList());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/hashtag/{artId}")
    @ApiOperation(value = "작품 해시태그 삭제 API", notes = "작품의 해시태그 리스트중에 삭제할 해시태그를 삭제 요청하는 API")
    public ResponseEntity<Void> deleteHashtag(@PathVariable Long artId, @Valid @RequestBody ArtHashtagDeleteRequest artHashtagDeleteRequest) {
        artService.deleteHashtag(artId, artHashtagDeleteRequest.getHashtagList());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/main/arts")
    @ApiOperation(value = "메인페이지 경매 작품 조회 API", notes = "경매 작품 데이터들을 페이징 개수만큼 응답 (정렬 기준 존재)")
    public <T> SortedArtResponse<List<T>> mainSortArtList(@Valid @RequestBody ArtMainSearchRequest artMainSearchRequest) {
        PageRequest pageRequest = PageRequest.of(artMainSearchRequest.getScroll(), SLICE_PER_PAGE);
        List<AuctionArtResponse> sortedAuctionArtList = artService.getSortedAuctionArtList(artMainSearchRequest.getSort(), pageRequest);
        return new SortedArtResponse<>(sortedAuctionArtList.size(), (List<T>) sortedAuctionArtList);
    }

    @PostMapping("/hashtag/arts")
    @ApiOperation(value = "해시태그를 통한 작품 조회 API", notes = "해시태그로 검색된 작품 데이터들을 페이징 개수만큼 응답 (정렬 기준 존재)")
    public <T> SortedArtResponse<List<T>> hashtagSearchArtList(@Valid @RequestBody ArtHashtagSearchRequest artHashtagSearchRequest) {
        PageRequest pageRequest = PageRequest.of(artHashtagSearchRequest.getScroll(), SLICE_PER_PAGE);

        if (artHashtagSearchRequest.getType().equals("auction")) {
            List<AuctionArtResponse> auctionArtListSearchByHashtag = artService.getAuctionArtListSearchByHashtag(
                    artHashtagSearchRequest.getHashtag(), artHashtagSearchRequest.getSort(), pageRequest
            );
            return new SortedArtResponse<>(auctionArtListSearchByHashtag.size(), (List<T>) auctionArtListSearchByHashtag);
        } else {
            List<GeneralArtResponse> generalArtListSearchByHashtag = artService.getGeneralArtListSearchByHashtag(
                    artHashtagSearchRequest.getHashtag(), artHashtagSearchRequest.getSort(), pageRequest
            );
            return new SortedArtResponse<>(generalArtListSearchByHashtag.size(), (List<T>) generalArtListSearchByHashtag);
        }
    }

    @PostMapping("/keyword/arts")
    @ApiOperation(value = "키워드를 통한 작품 조회 API", notes = "키워드로 검색된 작품 데이터들을 페이징 개수만큼 응답 (정렬 기준 존재)")
    public <T> SortedArtResponse<List<T>> keywordSearchArtList(@Valid @RequestBody ArtKeywordSearchRequest artKeywordSearchRequest) {
        PageRequest pageRequest = PageRequest.of(artKeywordSearchRequest.getScroll(), SLICE_PER_PAGE);

        if (artKeywordSearchRequest.getType().equals("auction")) {
            List<AuctionArtResponse> auctionArtListSearchByKeyword = artService.getAuctionArtListSearchByKeyword(
                    artKeywordSearchRequest.getKeyword(), artKeywordSearchRequest.getSort(), pageRequest
            );
            return new SortedArtResponse<>(auctionArtListSearchByKeyword.size(), (List<T>) auctionArtListSearchByKeyword);
        } else {
            List<GeneralArtResponse> generalArtListSearchByKeyword = artService.getGeneralArtListSearchByKeyword(
                    artKeywordSearchRequest.getKeyword(), artKeywordSearchRequest.getSort(), pageRequest
            );
            return new SortedArtResponse<>(generalArtListSearchByKeyword.size(), (List<T>) generalArtListSearchByKeyword);
        }
    }

    @PostMapping("/art/duplicate-check")
    @ApiOperation(value = "작품명 중복 검사 API", notes = "작품 등록간 작품명 중복을 체크하기 위한 API")
    public ResponseEntity<Void> duplicateArtNameValidation(@Valid @RequestBody DuplicateCheckRequest duplicateCheckRequest) {
        String resource = duplicateCheckRequest.getResource();
        String input = duplicateCheckRequest.getInput();

        if (resource.equals("name")) {
            artService.checkDuplicateArtName(input);
            return ResponseEntity.noContent().build();
        } else {
            throw AnotherArtException.type(ILLEGAL_URL_REQUEST);
        }
    }

    @PostMapping("/art/like")
    @ApiOperation(value = "작품 좋아요 API", notes = "사용자가 작품에 좋아요 마킹을 하기 위한 API")
    public ResponseEntity<Void> artLikeMarking(@Valid @RequestBody ArtLikeMarkingRequest artLikeMarkingRequest) {
        artService.likeArt(artLikeMarkingRequest.getArtId(), artLikeMarkingRequest.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/art/cancel")
    @ApiOperation(value = "작품 좋아요 취소 API", notes = "이전에 좋아요 버튼을 누른 작품에 대해서 좋아요 취소")
    public ResponseEntity<Void> artLikeMarkingCancel(@Valid @RequestBody ArtLikeMarkingCancelRequest artLikeMarkingCancelRequest) {
        artService.cancelArt(artLikeMarkingCancelRequest.getArtId(), artLikeMarkingCancelRequest.getUserId());
        return ResponseEntity.noContent().build();
    }
}
