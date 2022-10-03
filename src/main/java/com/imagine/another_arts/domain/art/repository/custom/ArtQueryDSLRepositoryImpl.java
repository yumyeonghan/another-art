package com.imagine.another_arts.domain.art.repository.custom;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.enums.SaleType;
import com.imagine.another_arts.domain.art.service.dto.response.BasicAuctionArtResponse;
import com.imagine.another_arts.domain.art.service.dto.response.BasicGeneralArtResponse;
import com.imagine.another_arts.domain.art.service.dto.response.QBasicAuctionArtResponse;
import com.imagine.another_arts.domain.art.service.dto.response.QBasicGeneralArtResponse;
import com.imagine.another_arts.domain.user.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.imagine.another_arts.domain.art.QArt.art;
import static com.imagine.another_arts.domain.arthashtag.QArtHashtag.artHashtag;
import static com.imagine.another_arts.domain.auction.QAuction.auction;
import static com.imagine.another_arts.domain.auctionhistory.QAuctionHistory.auctionHistory;
import static com.imagine.another_arts.domain.likeart.QLikeArt.likeArt;
import static com.imagine.another_arts.domain.user.QUser.user;

@RequiredArgsConstructor
public class ArtQueryDSLRepositoryImpl implements ArtQueryDSLRepository {
    private final JPAQueryFactory query;
    private static QUser userA = new QUser("userA");
    private static QUser userB = new QUser("userB");

    private static final SimpleExpression<QBasicGeneralArtResponse> LIKE_GROUP_BY_EXPRESSION = Expressions.list(
            QBasicGeneralArtResponse.class,
            art.id, art.name, art.description, art.initPrice, art.registerDate, art.storageName,
            user.id, user.nickname, user.schoolName
    );

    private static final SimpleExpression<QBasicAuctionArtResponse> COUNT_GROUP_BY_EXPRESSION = Expressions.list(
            QBasicAuctionArtResponse.class,
            auction.id, userB.id, userB.nickname, userB.schoolName, auction.bidPrice, auction.startDate,
            auction.endDate, art.id, art.name, art.description, art.initPrice, art.registerDate,
            art.storageName, userA.id, userA.nickname, userA.schoolName
    );

    @Override
    public Optional<Art> findArtByArtId(Long artId) {
        return Optional.ofNullable(
                query
                        .select(art)
                        .from(art)
                        .innerJoin(art.user, user).fetchJoin()
                        .where(artIdEq(artId))
                        .fetchOne()
        );
    }

    @Override
    public BasicGeneralArtResponse findSingleGeneralArtByArtId(Long artId) {
        return query
                .select(new QBasicGeneralArtResponse(
                        art.id, art.name, art.description, art.initPrice, art.registerDate, art.storageName,
                        user.id, user.nickname, user.schoolName))
                .from(art)
                .innerJoin(art.user, user)
                .where(saleTypeEq(SaleType.GENERAL), artIdEq(artId))
                .fetchOne();
    }

    @Override
    public BasicAuctionArtResponse findSingleAuctionArtByArtId(Long artId) {
        return query
                .select(new QBasicAuctionArtResponse(
                        auction.id, userB.id, userB.nickname, userB.schoolName, auction.bidPrice, auction.startDate,
                        auction.endDate, art.id, art.name, art.description, art.initPrice, art.registerDate,
                        art.storageName, userA.id, userA.nickname, userA.schoolName))
                .from(auction)
                .innerJoin(auction.art, art)
                .innerJoin(art.user, userA)
                .leftJoin(auction.user, userB)
                .where(saleTypeEq(SaleType.AUCTION), artIdEq(artId))
                .fetchOne();
    }

    @Override
    public List<BasicGeneralArtResponse> findGeneralArtHashtagSearch(String givenHashtag, String sortType, Pageable pageRequest) {
        JPAQuery<BasicGeneralArtResponse> basicQuery = query
                .selectDistinct(new QBasicGeneralArtResponse(
                        art.id, art.name, art.description, art.initPrice, art.registerDate, art.storageName,
                        user.id, user.nickname, user.schoolName))
                .from(art)
                .innerJoin(art.user, user)
                .innerJoin(art.artHashtagList, artHashtag)
                .where(saleTypeEq(SaleType.GENERAL), hashtagEq(givenHashtag))
                .offset((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
                .limit(pageRequest.getPageSize());

        // date, rdate, price, rprice, like, rlike
        switch (sortType) {
            case "date":
                return basicQuery
                        .orderBy(art.registerDate.desc())
                        .fetch();

            case "rdate":
                return basicQuery
                        .orderBy(art.registerDate.asc())
                        .fetch();

            case "price":
                return basicQuery
                        .orderBy(art.initPrice.desc())
                        .fetch();

            case "rprice":
                return basicQuery
                        .orderBy(art.initPrice.asc())
                        .fetch();

            case "like":
                return basicQuery
                        .leftJoin(art.likeArtList, likeArt)
                        .groupBy(LIKE_GROUP_BY_EXPRESSION)
                        .orderBy(likeArt.count().desc())
                        .fetch();

            case "rlike":
                return basicQuery
                        .leftJoin(art.likeArtList, likeArt)
                        .groupBy(LIKE_GROUP_BY_EXPRESSION)
                        .orderBy(likeArt.count().asc())
                        .fetch();

            default:
                return new ArrayList<>(); // return empty list
        }
    }

    @Override
    public List<BasicGeneralArtResponse> findGeneralArtKeywordSearch(String givenKeyword, String sortType, Pageable pageRequest) {
        JPAQuery<BasicGeneralArtResponse> basicQuery = query
                .selectDistinct(new QBasicGeneralArtResponse(
                        art.id, art.name, art.description, art.initPrice, art.registerDate, art.storageName,
                        user.id, user.nickname, user.schoolName))
                .from(art)
                .innerJoin(art.user, user)
                .where(saleTypeEq(SaleType.GENERAL), keywordContains(givenKeyword))
                .offset((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
                .limit(pageRequest.getPageSize());

        // date, rdate, price, rprice, like, rlike
        switch (sortType) {
            case "date":
                return basicQuery
                        .orderBy(art.registerDate.desc())
                        .fetch();

            case "rdate":
                return basicQuery
                        .orderBy(art.registerDate.asc())
                        .fetch();

            case "price":
                return basicQuery
                        .orderBy(art.initPrice.desc())
                        .fetch();

            case "rprice":
                return basicQuery
                        .orderBy(art.initPrice.asc())
                        .fetch();

            case "like":
                return basicQuery
                        .leftJoin(art.likeArtList, likeArt)
                        .groupBy(LIKE_GROUP_BY_EXPRESSION)
                        .orderBy(likeArt.count().desc())
                        .fetch();

            case "rlike":
                return basicQuery
                        .leftJoin(art.likeArtList, likeArt)
                        .groupBy(LIKE_GROUP_BY_EXPRESSION)
                        .orderBy(likeArt.count().asc())
                        .fetch();

            default:
                return new ArrayList<>(); // return empty list
        }
    }

    @Override
    public List<BasicAuctionArtResponse> findAuctionArt(String sortType, Pageable pageRequest) {
        JPAQuery<BasicAuctionArtResponse> basicQuery = query
                .selectDistinct(new QBasicAuctionArtResponse(
                        auction.id, userB.id, userB.nickname, userB.schoolName, auction.bidPrice, auction.startDate,
                        auction.endDate, art.id, art.name, art.description, art.initPrice, art.registerDate,
                        art.storageName, userA.id, userA.nickname, userA.schoolName))
                .from(auction)
                .innerJoin(auction.art, art)
                .innerJoin(art.user, userA)
                .leftJoin(auction.user, userB)
                .where(saleTypeEq(SaleType.AUCTION), currentDateBetween(LocalDateTime.now()))
                .offset((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
                .limit(pageRequest.getPageSize());

        // date, rdate, price, rprice, count, rcount
        switch (sortType) {
            case "date":
                return basicQuery
                        .orderBy(art.registerDate.desc())
                        .fetch();

            case "rdate":
                return basicQuery
                        .orderBy(art.registerDate.asc())
                        .fetch();

            case "price":
                return basicQuery
                        .orderBy(auction.bidPrice.desc())
                        .fetch();

            case "rprice":
                return basicQuery
                        .orderBy(auction.bidPrice.asc())
                        .fetch();

            case "count":
                return basicQuery
                        .leftJoin(auction.auctionHistoryList, auctionHistory)
                        .groupBy(COUNT_GROUP_BY_EXPRESSION)
                        .orderBy(auctionHistory.count().desc())
                        .fetch();

            case "rcount":
                return basicQuery
                        .leftJoin(auction.auctionHistoryList, auctionHistory)
                        .groupBy(COUNT_GROUP_BY_EXPRESSION)
                        .orderBy(auctionHistory.count().asc())
                        .fetch();

            default:
                return new ArrayList<>(); // return empty list
        }
    }

    @Override
    public List<BasicAuctionArtResponse> findAuctionArtHashtagSearch(String givenHashtag, String sortType, Pageable pageRequest) {
        JPAQuery<BasicAuctionArtResponse> basicQuery = query
                .selectDistinct(new QBasicAuctionArtResponse(
                        auction.id, userB.id, userB.nickname, userB.schoolName, auction.bidPrice, auction.startDate,
                        auction.endDate, art.id, art.name, art.description, art.initPrice, art.registerDate,
                        art.storageName, userA.id, userA.nickname, userA.schoolName))
                .from(auction)
                .innerJoin(auction.art, art)
                .innerJoin(art.user, userA)
                .leftJoin(auction.user, userB)
                .innerJoin(art.artHashtagList, artHashtag)
                .where(saleTypeEq(SaleType.AUCTION), currentDateBetween(LocalDateTime.now()), hashtagEq(givenHashtag))
                .offset((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
                .limit(pageRequest.getPageSize());

        // date, rdate, price, rprice, count, rcount
        switch (sortType) {
            case "date":
                return basicQuery
                        .orderBy(art.registerDate.desc())
                        .fetch();

            case "rdate":
                return basicQuery
                        .orderBy(art.registerDate.asc())
                        .fetch();

            case "price":
                return basicQuery
                        .orderBy(auction.bidPrice.desc())
                        .fetch();

            case "rprice":
                return basicQuery
                        .orderBy(auction.bidPrice.asc())
                        .fetch();

            case "count":
                return basicQuery
                        .leftJoin(auction.auctionHistoryList, auctionHistory)
                        .groupBy(COUNT_GROUP_BY_EXPRESSION)
                        .orderBy(auctionHistory.count().desc())
                        .fetch();

            case "rcount":
                return basicQuery
                        .leftJoin(auction.auctionHistoryList, auctionHistory)
                        .groupBy(COUNT_GROUP_BY_EXPRESSION)
                        .orderBy(auctionHistory.count().asc())
                        .fetch();

            default:
                return new ArrayList<>(); // return empty list
        }
    }

    @Override
    public List<BasicAuctionArtResponse> findAuctionArtKeywordSearch(String givenKeyword, String sortType, Pageable pageRequest) {
        JPAQuery<BasicAuctionArtResponse> basicQuery = query
                .selectDistinct(new QBasicAuctionArtResponse(
                        auction.id, userB.id, userB.nickname, userB.schoolName, auction.bidPrice, auction.startDate,
                        auction.endDate, art.id, art.name, art.description, art.initPrice, art.registerDate,
                        art.storageName, userA.id, userA.nickname, userA.schoolName))
                .from(auction)
                .innerJoin(auction.art, art)
                .innerJoin(art.user, userA)
                .leftJoin(auction.user, userB)
                .where(saleTypeEq(SaleType.AUCTION), currentDateBetween(LocalDateTime.now()), keywordContains(givenKeyword))
                .offset((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
                .limit(pageRequest.getPageSize());

        // date, rdate, price, rprice, count, rcount
        switch (sortType) {
            case "date":
                return basicQuery
                        .orderBy(art.registerDate.desc())
                        .fetch();

            case "rdate":
                return basicQuery
                        .orderBy(art.registerDate.asc())
                        .fetch();

            case "price":
                return basicQuery
                        .orderBy(auction.bidPrice.desc())
                        .fetch();

            case "rprice":
                return basicQuery
                        .orderBy(auction.bidPrice.asc())
                        .fetch();

            case "count":
                return basicQuery
                        .leftJoin(auction.auctionHistoryList, auctionHistory)
                        .groupBy(COUNT_GROUP_BY_EXPRESSION)
                        .orderBy(auctionHistory.count().desc())
                        .fetch();

            case "rcount":
                return basicQuery
                        .leftJoin(auction.auctionHistoryList, auctionHistory)
                        .groupBy(COUNT_GROUP_BY_EXPRESSION)
                        .orderBy(auctionHistory.count().asc())
                        .fetch();

            default:
                return new ArrayList<>(); // return empty list
        }
    }

    private BooleanExpression saleTypeEq(SaleType saleType) {
        if (saleType == null) {
            return null;
        }

        return art.saleType.eq(saleType);
    }

    private BooleanExpression artIdEq(Long artId) {
        if (artId == null) {
            return null;
        }

        return art.id.eq(artId);
    }

    private BooleanExpression currentDateBetween(LocalDateTime curretDateTime) {
        if (curretDateTime == null) {
            return null;
        }

        return auction.startDate.before(curretDateTime).and(auction.endDate.after(curretDateTime));
    }

    private BooleanExpression hashtagEq(String givenHashtag) {
        if (givenHashtag == null) {
            return null;
        }

        return artHashtag.name.eq(givenHashtag);
    }

    private BooleanExpression keywordContains(String givenKeyword) {
        if (givenKeyword == null) {
            return null;
        }

        return art.name.contains(givenKeyword).or(art.description.contains(givenKeyword));
    }
}
