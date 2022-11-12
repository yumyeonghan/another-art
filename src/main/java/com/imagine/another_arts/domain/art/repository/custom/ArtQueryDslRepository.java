package com.imagine.another_arts.domain.art.repository.custom;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.repository.dto.response.BasicAuctionArt;
import com.imagine.another_arts.domain.art.repository.dto.response.BasicGeneralArt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ArtQueryDslRepository {
    Optional<Art> findByArtId(Long artId);
    BasicGeneralArt findSingleGeneralArtByArtId(Long artId);
    BasicAuctionArt findSingleAuctionArtByArtId(Long artId);
    Page<BasicGeneralArt> findGeneralArtListWithHashtag(String givenHashtag, String sortType, Pageable pageRequest);
    Page<BasicGeneralArt> findGeneralArtListWithKeyword(String givenKeyword, String sortType, Pageable pageRequest);
    Page<BasicAuctionArt> findAuctionArtList(String sortType, Pageable pageRequest);
    Page<BasicAuctionArt> findAuctionArtListWithHashtag(String givenHashtag, String sortType, Pageable pageRequest);
    Page<BasicAuctionArt> findAuctionArtListWithKeyword(String givenKeyword, String sortType, Pageable pageRequest);
}
