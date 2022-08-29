package com.imagine.another_arts.domain.art.repository.custom;

import com.imagine.another_arts.domain.art.service.dto.response.BasicAuctionArtResponse;
import com.imagine.another_arts.domain.art.service.dto.response.BasicGeneralArtResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArtCustomRepository {
    List<BasicGeneralArtResponse> findGeneralArtHashtagSearch(String hashtag, String sortType, Pageable pageable);

    List<BasicGeneralArtResponse> findGeneralArtKeywordSearch(String keyword, String sortType, Pageable pageable);

    List<BasicAuctionArtResponse> findAuctionArt(String sortType, Pageable pageable);

    List<BasicAuctionArtResponse> findAuctionArtHashtagSearch(String hashtag, String sortType, Pageable pageable);

    List<BasicAuctionArtResponse> findAuctionArtKeywordSearch(String keyword, String sortType, Pageable pageable);
}
