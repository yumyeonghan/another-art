package com.imagine.another_arts.domain.art.repository.custom;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.service.dto.response.BasicAuctionArtResponse;
import com.imagine.another_arts.domain.art.service.dto.response.BasicGeneralArtResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArtQueryDslRepository {
    Optional<Art> findArtByArtId(Long artId);
    BasicGeneralArtResponse findSingleGeneralArtByArtId(Long artId);
    BasicAuctionArtResponse findSingleAuctionArtByArtId(Long artId);
    List<BasicGeneralArtResponse> findGeneralArtHashtagSearch(String givenHashtag, String sortType, Pageable pageRequest);
    List<BasicGeneralArtResponse> findGeneralArtKeywordSearch(String givenKeyword, String sortType, Pageable pageRequest);
    List<BasicAuctionArtResponse> findAuctionArt(String sortType, Pageable pageRequest);
    List<BasicAuctionArtResponse> findAuctionArtHashtagSearch(String givenHashtag, String sortType, Pageable pageRequest);
    List<BasicAuctionArtResponse> findAuctionArtKeywordSearch(String givenKeyword, String sortType, Pageable pageRequest);
}
