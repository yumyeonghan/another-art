package com.imagine.another_arts.web.art;

import com.imagine.another_arts.domain.art.service.ArtService;
import com.imagine.another_arts.domain.art.service.dto.response.AbstractArt;
import com.imagine.another_arts.domain.art.service.dto.response.AuctionArt;
import com.imagine.another_arts.domain.art.service.dto.response.GeneralArt;
import com.imagine.another_arts.exception.AnotherArtException;
import com.imagine.another_arts.web.art.dto.Pagination;
import com.imagine.another_arts.web.art.dto.request.*;
import com.imagine.another_arts.web.art.dto.response.SingleArtResponse;
import com.imagine.another_arts.web.art.dto.response.SortedArtResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
@Api(tags = "작품 API")
public class ArtController {
    private final ArtService artService;
    private static final int SLICE_PER_PAGE = 20;

    @PostMapping(value = "/art", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "작품 등록 API", notes = "artType(auction, general)을 통해서 경매/일반 작품 등록 구분 -> 경매 작품일 경우 [경매 시작/종료 날짜] 반드시 기입")
    public ResponseEntity<Void> artRegister(ArtRegisterRequest registerRequest) {
        Long saveArtId = registerRequest.getSaleType().equals("auction")
                ? artService.registerArt(registerRequest.toAuctioArtDto())
                : artService.registerArt(registerRequest.toGeneralArtDto());

        return ResponseEntity
                .created(URI.create("/api/art/" + saveArtId))
                .build();
    }

    @GetMapping("/art/{artId}")
    @ApiOperation(value = "작품 조회 API", notes = "PathVariable로 작품의 PK를 보내고 그에 따른 작품을 조회하는 API")
    public SingleArtResponse<AbstractArt> getSingleArt(@PathVariable Long artId) {
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
    public ResponseEntity<Void> addHashtag(@PathVariable Long artId, @Valid @RequestBody HashtagUpdateRequest hashtagUpdateRequest) {
        artService.addHashtag(artId, hashtagUpdateRequest.getHashtagList());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/hashtag/{artId}")
    @ApiOperation(value = "작품 해시태그 삭제 API", notes = "작품의 해시태그 리스트중에 삭제할 해시태그를 삭제 요청하는 API")
    public ResponseEntity<Void> deleteHashtag(@PathVariable Long artId, @Valid @RequestBody HashtagDeleteRequest hashtagDeleteRequest) {
        artService.deleteHashtag(artId, hashtagDeleteRequest.getHashtagList());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/main/arts")
    @ApiOperation(value = "메인페이지 경매 작품 조회 API", notes = "경매 작품 데이터들을 페이징 개수만큼 응답 (정렬 기준 존재)")
    public <T extends AbstractArt> SortedArtResponse<List<T>> mainSortArtList(@Valid @RequestBody MainArtSortRequest searchRequest) {
        PageRequest pageRequest = PageRequest.of(searchRequest.getPage() - 1, SLICE_PER_PAGE);
        Page<AuctionArt> sortedAuctionArtList = artService.getSortedAuctionArtList(searchRequest.getSort(), pageRequest);
        return new SortedArtResponse<>(
                sortedAuctionArtList.getContent().size(),
                (List<T>) sortedAuctionArtList.getContent(),
                new Pagination(sortedAuctionArtList.getTotalElements(), sortedAuctionArtList.getTotalPages(), searchRequest.getPage())
        );
    }

    @PostMapping("/hashtag/arts")
    @ApiOperation(value = "해시태그를 통한 작품 조회 API", notes = "해시태그로 검색된 작품 데이터들을 페이징 개수만큼 응답 (정렬 기준 존재)")
    public <T extends AbstractArt> SortedArtResponse<List<T>> hashtagSearchArtList(@Valid @RequestBody HashtagSearchArtRequest searchRequest) {
        PageRequest pageRequest = PageRequest.of(searchRequest.getPage() - 1, SLICE_PER_PAGE);

        if (searchRequest.getType().equalsIgnoreCase("auction")) {
            Page<AuctionArt> auctionArtListSearchByHashtag = artService.getAuctionArtListSearchByHashtag(searchRequest.getHashtag(), searchRequest.getSort(), pageRequest);
            return new SortedArtResponse<>(
                    auctionArtListSearchByHashtag.getContent().size(),
                    (List<T>) auctionArtListSearchByHashtag.getContent(),
                    new Pagination(auctionArtListSearchByHashtag.getTotalElements(), auctionArtListSearchByHashtag.getTotalPages(), searchRequest.getPage())
            );
        } else {
            Page<GeneralArt> generalArtListSearchByHashtag = artService.getGeneralArtListSearchByHashtag(searchRequest.getHashtag(), searchRequest.getSort(), pageRequest);
            return new SortedArtResponse<>(
                    generalArtListSearchByHashtag.getContent().size(),
                    (List<T>) generalArtListSearchByHashtag.getContent(),
                    new Pagination(generalArtListSearchByHashtag.getTotalElements(), generalArtListSearchByHashtag.getTotalPages(), searchRequest.getPage())
            );
        }
    }

    @PostMapping("/keyword/arts")
    @ApiOperation(value = "키워드를 통한 작품 조회 API", notes = "키워드로 검색된 작품 데이터들을 페이징 개수만큼 응답 (정렬 기준 존재)")
    public <T extends AbstractArt> SortedArtResponse<List<T>> keywordSearchArtList(@Valid @RequestBody KeywordSearchArtRequest searchRequest) {
        PageRequest pageRequest = PageRequest.of(searchRequest.getPage() - 1, SLICE_PER_PAGE);

        if (searchRequest.getType().equalsIgnoreCase("auction")) {
            Page<AuctionArt> auctionArtListSearchByKeyword = artService.getAuctionArtListSearchByKeyword(searchRequest.getKeyword(), searchRequest.getSort(), pageRequest);
            return new SortedArtResponse<>(
                    auctionArtListSearchByKeyword.getContent().size(),
                    (List<T>) auctionArtListSearchByKeyword.getContent(),
                    new Pagination(auctionArtListSearchByKeyword.getTotalElements(), auctionArtListSearchByKeyword.getTotalPages(), searchRequest.getPage())
            );
        } else {
            Page<GeneralArt> generalArtListSearchByKeyword = artService.getGeneralArtListSearchByKeyword(searchRequest.getKeyword(), searchRequest.getSort(), pageRequest);
            return new SortedArtResponse<>(
                    generalArtListSearchByKeyword.getContent().size(),
                    (List<T>) generalArtListSearchByKeyword.getContent(),
                    new Pagination(generalArtListSearchByKeyword.getTotalElements(), generalArtListSearchByKeyword.getTotalPages(), searchRequest.getPage())
            );
        }
    }

    @PostMapping("/art/duplicate-check")
    @ApiOperation(value = "작품명 중복 검사 API", notes = "작품 등록간 작품명 중복을 체크하기 위한 API")
    public ResponseEntity<Void> duplicateArtNameValidation(@Valid @RequestBody DuplicateCheckRequest duplicateCheckRequest) {
        String resource = duplicateCheckRequest.getResource();
        String input = duplicateCheckRequest.getInput();

        if (resource.equalsIgnoreCase("name")) {
            artService.checkDuplicateArtName(input);
            return ResponseEntity.noContent().build();
        } else {
            throw AnotherArtException.type(ILLEGAL_URL_REQUEST);
        }
    }

    @PostMapping("/art/like")
    @ApiOperation(value = "작품 좋아요 API", notes = "사용자가 작품에 좋아요 마킹을 하기 위한 API")
    public ResponseEntity<Void> likeArt(@Valid @RequestBody LikeArtRequest likeArtRequest) {
        artService.likeArt(likeArtRequest.getArtId(), likeArtRequest.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/art/cancel")
    @ApiOperation(value = "작품 좋아요 취소 API", notes = "이전에 좋아요 버튼을 누른 작품에 대해서 좋아요 취소")
    public ResponseEntity<Void> cancelArt(@Valid @RequestBody CancelArtRequest cancelArtRequest) {
        artService.cancelArt(cancelArtRequest.getArtId(), cancelArtRequest.getUserId());
        return ResponseEntity.noContent().build();
    }
}
