package com.imagine.another_arts.domain.art.repository.custom;

import com.imagine.another_arts.domain.art.service.dto.response.BasicAuctionArtResponse;
import com.imagine.another_arts.domain.art.service.dto.response.BasicGeneralArtResponse;
import com.imagine.another_arts.query.AuctionArtQueryFactory;
import com.imagine.another_arts.query.GeneralArtQueryFactory;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class ArtCustomRepositoryImpl implements ArtCustomRepository {

    private final EntityManager em;
    private static JpaResultMapper mapper = new JpaResultMapper(); // DTO Mapper QLRM

    @Override
    public List<BasicGeneralArtResponse> findGeneralArtHashtagSearch(String hashtag, String sortType, Pageable pageable) {
        StringBuffer query = new StringBuffer();
        query.append(GeneralArtQueryFactory.HASHTAG_QUERY);

        switch (sortType) {
            case "date" -> query.append(GeneralArtQueryFactory.HASHTAG_ORDER_BY_DATE + " DESC");
            case "rdate" -> query.append(GeneralArtQueryFactory.HASHTAG_ORDER_BY_DATE);
            case "price" -> query.append(GeneralArtQueryFactory.HASHTAG_ORDER_BY_PRICE + " DESC");
            case "rprice" -> query.append(GeneralArtQueryFactory.HASHTAG_ORDER_BY_PRICE);
            case "like" -> query.append(GeneralArtQueryFactory.HASHTAG_ORDER_BY_LIKE_COUNT + " DESC");
            case "rlike" -> query.append(GeneralArtQueryFactory.HASHTAG_ORDER_BY_LIKE_COUNT);
        }

        return mapper.list(
                em.createNativeQuery(query.toString())
                        .setParameter("hashtag", hashtag)
                        .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                        .setMaxResults(pageable.getPageSize()),
                BasicGeneralArtResponse.class
        );
    }

    @Override
    public List<BasicGeneralArtResponse> findGeneralArtKeywordSearch(String keyword, String sortType, Pageable pageable) {
        StringBuffer query = new StringBuffer();
        query.append(GeneralArtQueryFactory.KEYWORD_QUERY);

        switch (sortType) {
            case "date" -> query.append(GeneralArtQueryFactory.KEYWORD_ORDER_BY_DATE + " DESC");
            case "rdate" -> query.append(GeneralArtQueryFactory.KEYWORD_ORDER_BY_DATE);
            case "price" -> query.append(GeneralArtQueryFactory.KEYWORD_ORDER_BY_PRICE + " DESC");
            case "rprice" -> query.append(GeneralArtQueryFactory.KEYWORD_ORDER_BY_PRICE);
            case "like" -> query.append(GeneralArtQueryFactory.KEYWORD_ORDER_BY_LIKE_COUNT + " DESC");
            case "rlike" -> query.append(GeneralArtQueryFactory.KEYWORD_ORDER_BY_LIKE_COUNT);
        }

        return mapper.list(
                em.createNativeQuery(query.toString())
                        .setParameter("keyword", keyword)
                        .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                        .setMaxResults(pageable.getPageSize()),
                BasicGeneralArtResponse.class
        );
    }

    @Override
    public List<BasicAuctionArtResponse> findAuctionArt(String sortType, Pageable pageable) {
        StringBuffer query = new StringBuffer();
        query.append(AuctionArtQueryFactory.MAIN_QUERY);

        switch (sortType) {
            case "date" -> query.append(AuctionArtQueryFactory.MAIN_ORDER_BY_DATE + " DESC");
            case "rdate" -> query.append(AuctionArtQueryFactory.MAIN_ORDER_BY_DATE);
            case "price" -> query.append(AuctionArtQueryFactory.MAIN_ORDER_BY_BID_PRICE + " DESC");
            case "rprice" -> query.append(AuctionArtQueryFactory.MAIN_ORDER_BY_BID_PRICE);
            case "count" -> query.append(AuctionArtQueryFactory.MAIN_ORDER_BY_BID_COUNT + " DESC");
            case "rcount" -> query.append(AuctionArtQueryFactory.MAIN_ORDER_BY_BID_COUNT);
        }

        return mapper.list(
                em.createNativeQuery(query.toString())
                        .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                        .setMaxResults(pageable.getPageSize()),
                BasicAuctionArtResponse.class
        );
    }

    @Override
    public List<BasicAuctionArtResponse> findAuctionArtHashtagSearch(String hashtag, String sortType, Pageable pageable) {
        StringBuffer query = new StringBuffer();
        query.append(AuctionArtQueryFactory.HASHTAG_QUERY);

        switch (sortType) {
            case "date" -> query.append(AuctionArtQueryFactory.HASHTAG_ORDER_BY_DATE + " DESC");
            case "rdate" -> query.append(AuctionArtQueryFactory.HASHTAG_ORDER_BY_DATE);
            case "price" -> query.append(AuctionArtQueryFactory.HASHTAG_ORDER_BY_BID_PRICE + " DESC");
            case "rprice" -> query.append(AuctionArtQueryFactory.HASHTAG_ORDER_BY_BID_PRICE);
            case "count" -> query.append(AuctionArtQueryFactory.HASHTAG_ORDER_BY_BID_COUNT + " DESC");
            case "rcount" -> query.append(AuctionArtQueryFactory.HASHTAG_ORDER_BY_BID_COUNT);
        }

        return mapper.list(
                em.createNativeQuery(query.toString())
                        .setParameter("hashtag", hashtag)
                        .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                        .setMaxResults(pageable.getPageSize()),
                BasicAuctionArtResponse.class
        );
    }

    @Override
    public List<BasicAuctionArtResponse> findAuctionArtKeywordSearch(String keyword, String sortType, Pageable pageable) {
        StringBuffer query = new StringBuffer();
        query.append(AuctionArtQueryFactory.KEYWORD_QUERY);

        switch (sortType) {
            case "date" -> query.append(AuctionArtQueryFactory.KEYWORD_ORDER_BY_DATE + " DESC");
            case "rdate" -> query.append(AuctionArtQueryFactory.KEYWORD_ORDER_BY_DATE);
            case "price" -> query.append(AuctionArtQueryFactory.KEYWORD_ORDER_BY_BID_PRICE + " DESC");
            case "rprice" -> query.append(AuctionArtQueryFactory.KEYWORD_ORDER_BY_BID_PRICE);
            case "count" -> query.append(AuctionArtQueryFactory.KEYWORD_ORDER_BY_BID_COUNT + " DESC");
            case "rcount" -> query.append(AuctionArtQueryFactory.KEYWORD_ORDER_BY_BID_COUNT);
        }

        return mapper.list(
                em.createNativeQuery(query.toString())
                        .setParameter("keyword", keyword)
                        .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                        .setMaxResults(pageable.getPageSize()),
                BasicAuctionArtResponse.class
        );
    }
}
