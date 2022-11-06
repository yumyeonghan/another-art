package com.imagine.another_arts.domain.art.repository.custom;

import com.imagine.another_arts.domain.art.Art;
import com.imagine.another_arts.domain.art.enums.SaleType;
import com.imagine.another_arts.domain.art.repository.dto.response.BasicAuctionArt;
import com.imagine.another_arts.domain.art.repository.dto.response.BasicGeneralArt;
import com.imagine.another_arts.domain.art.repository.dto.response.QBasicAuctionArt;
import com.imagine.another_arts.domain.art.repository.dto.response.QBasicGeneralArt;
import com.imagine.another_arts.domain.user.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(readOnly = true)
public class ArtQueryDslRepositoryImpl implements ArtQueryDslRepository {
    private final JPAQueryFactory query;
    private static QUser userA = new QUser("userA");
    private static QUser userB = new QUser("userB");

    private static final SimpleExpression<QBasicGeneralArt> LIKE_GROUP_BY_EXPRESSION = Expressions.list(
            QBasicGeneralArt.class,
            art.id, art.name, art.description, art.initPrice, art.registerDate, art.storageName,
            user.id, user.nickname, user.schoolName
    );

    private static final SimpleExpression<QBasicAuctionArt> COUNT_GROUP_BY_EXPRESSION = Expressions.list(
            QBasicAuctionArt.class,
            auction.id, userB.id, userB.nickname, userB.schoolName, auction.bidPrice, auction.startDate,
            auction.endDate, art.id, art.name, art.description, art.initPrice, art.registerDate,
            art.storageName, userA.id, userA.nickname, userA.schoolName
    );

    @Override
    public Optional<Art> findByArtId(Long artId) {
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
    public BasicGeneralArt findSingleGeneralArtByArtId(Long artId) {
        return query
                .select(new QBasicGeneralArt(
                        art.id, art.name, art.description, art.initPrice, art.registerDate, art.storageName,
                        user.id, user.nickname, user.schoolName))
                .from(art)
                .innerJoin(art.user, user)
                .where(saleTypeEq(SaleType.GENERAL), artIdEq(artId))
                .fetchOne();
    }

    @Override
    public BasicAuctionArt findSingleAuctionArtByArtId(Long artId) {
        return query
                .select(new QBasicAuctionArt(
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
    public Page<BasicGeneralArt> findGeneralArtListWithHashtag(String givenHashtag, String sortType, Pageable pageRequest) {
        JPAQuery<BasicGeneralArt> basicQuery = query
                .selectDistinct(new QBasicGeneralArt(
                        art.id, art.name, art.description, art.initPrice, art.registerDate, art.storageName,
                        user.id, user.nickname, user.schoolName))
                .from(art)
                .innerJoin(art.user, user)
                .innerJoin(art.artHashtagList, artHashtag)
                .where(saleTypeEq(SaleType.GENERAL), hashtagEq(givenHashtag))
                .offset((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
                .limit(pageRequest.getPageSize());

        // date, rdate, price, rprice, like, rlike
        List<BasicGeneralArt> content;
        switch (sortType) {
            case "date":
                content = basicQuery
                        .orderBy(art.registerDate.desc())
                        .fetch();
                break;

            case "rdate":
                content = basicQuery
                        .orderBy(art.registerDate.asc())
                        .fetch();
                break;

            case "price":
                content = basicQuery
                        .orderBy(art.initPrice.desc())
                        .fetch();
                break;

            case "rprice":
                content = basicQuery
                        .orderBy(art.initPrice.asc())
                        .fetch();
                break;

            case "like":
                content = basicQuery
                        .leftJoin(art.likeArtList, likeArt)
                        .groupBy(LIKE_GROUP_BY_EXPRESSION)
                        .orderBy(likeArt.count().desc())
                        .fetch();
                break;

            case "rlike":
                content = basicQuery
                        .leftJoin(art.likeArtList, likeArt)
                        .groupBy(LIKE_GROUP_BY_EXPRESSION)
                        .orderBy(likeArt.count().asc())
                        .fetch();
                break;

            default:
                content = new ArrayList<>();
        }

        List<Long> countQuery = query
                .select(art.id)
                .from(art)
                .innerJoin(art.artHashtagList, artHashtag)
                .where(saleTypeEq(SaleType.GENERAL), hashtagEq(givenHashtag))
                .fetch();

        return PageableExecutionUtils.getPage(content, pageRequest, countQuery::size);
    }

    @Override
    public Page<BasicGeneralArt> findGeneralArtListWithKeyword(String givenKeyword, String sortType, Pageable pageRequest) {
        JPAQuery<BasicGeneralArt> basicQuery = query
                .selectDistinct(new QBasicGeneralArt(
                        art.id, art.name, art.description, art.initPrice, art.registerDate, art.storageName,
                        user.id, user.nickname, user.schoolName))
                .from(art)
                .innerJoin(art.user, user)
                .where(saleTypeEq(SaleType.GENERAL), keywordContains(givenKeyword))
                .offset((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
                .limit(pageRequest.getPageSize());

        // date, rdate, price, rprice, like, rlike
        List<BasicGeneralArt> content;
        switch (sortType) {
            case "date":
                content = basicQuery
                        .orderBy(art.registerDate.desc())
                        .fetch();
                break;

            case "rdate":
                content = basicQuery
                        .orderBy(art.registerDate.asc())
                        .fetch();
                break;

            case "price":
                content = basicQuery
                        .orderBy(art.initPrice.desc())
                        .fetch();
                break;

            case "rprice":
                content = basicQuery
                        .orderBy(art.initPrice.asc())
                        .fetch();
                break;

            case "like":
                content = basicQuery
                        .leftJoin(art.likeArtList, likeArt)
                        .groupBy(LIKE_GROUP_BY_EXPRESSION)
                        .orderBy(likeArt.count().desc())
                        .fetch();
                break;

            case "rlike":
                content = basicQuery
                        .leftJoin(art.likeArtList, likeArt)
                        .groupBy(LIKE_GROUP_BY_EXPRESSION)
                        .orderBy(likeArt.count().asc())
                        .fetch();
                break;

            default:
                content = new ArrayList<>(); // return empty list
        }

        List<Long> countQuery = query
                .select(art.id)
                .from(art)
                .where(saleTypeEq(SaleType.GENERAL), keywordContains(givenKeyword))
                .fetch();

        return PageableExecutionUtils.getPage(content, pageRequest, countQuery::size);
    }

    @Override
    public Page<BasicAuctionArt> findAuctionArtList(String sortType, Pageable pageRequest) {
        JPAQuery<BasicAuctionArt> basicQuery = query
                .selectDistinct(new QBasicAuctionArt(
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
        List<BasicAuctionArt> content;
        switch (sortType) {
            case "date":
                content = basicQuery
                        .orderBy(art.registerDate.desc())
                        .fetch();
                break;

            case "rdate":
                content = basicQuery
                        .orderBy(art.registerDate.asc())
                        .fetch();
                break;

            case "price":
                content = basicQuery
                        .orderBy(auction.bidPrice.desc())
                        .fetch();
                break;

            case "rprice":
                content = basicQuery
                        .orderBy(auction.bidPrice.asc())
                        .fetch();
                break;

            case "count":
                content = basicQuery
                        .leftJoin(auction.auctionHistoryList, auctionHistory)
                        .groupBy(COUNT_GROUP_BY_EXPRESSION)
                        .orderBy(auctionHistory.count().desc())
                        .fetch();
                break;

            case "rcount":
                content = basicQuery
                        .leftJoin(auction.auctionHistoryList, auctionHistory)
                        .groupBy(COUNT_GROUP_BY_EXPRESSION)
                        .orderBy(auctionHistory.count().asc())
                        .fetch();
                break;

            default:
                content = new ArrayList<>(); // return empty list
        }

        List<Long> countQuery = query
                .select(art.id)
                .from(auction)
                .innerJoin(auction.art, art)
                .where(saleTypeEq(SaleType.AUCTION), currentDateBetween(LocalDateTime.now()))
                .fetch();

        return PageableExecutionUtils.getPage(content, pageRequest, countQuery::size);
    }

    @Override
    public Page<BasicAuctionArt> findAuctionArtListWithHashtag(String givenHashtag, String sortType, Pageable pageRequest) {
        JPAQuery<BasicAuctionArt> basicQuery = query
                .selectDistinct(new QBasicAuctionArt(
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
        List<BasicAuctionArt> content;
        switch (sortType) {
            case "date":
                content = basicQuery
                        .orderBy(art.registerDate.desc())
                        .fetch();
                break;

            case "rdate":
                content = basicQuery
                        .orderBy(art.registerDate.asc())
                        .fetch();
                break;

            case "price":
                content = basicQuery
                        .orderBy(auction.bidPrice.desc())
                        .fetch();
                break;

            case "rprice":
                content = basicQuery
                        .orderBy(auction.bidPrice.asc())
                        .fetch();
                break;

            case "count":
                content = basicQuery
                        .leftJoin(auction.auctionHistoryList, auctionHistory)
                        .groupBy(COUNT_GROUP_BY_EXPRESSION)
                        .orderBy(auctionHistory.count().desc())
                        .fetch();
                break;

            case "rcount":
                content = basicQuery
                        .leftJoin(auction.auctionHistoryList, auctionHistory)
                        .groupBy(COUNT_GROUP_BY_EXPRESSION)
                        .orderBy(auctionHistory.count().asc())
                        .fetch();
                break;

            default:
                content = new ArrayList<>(); // return empty list
        }

        List<Long> countQuery = query
                .select(auction.id)
                .from(auction)
                .innerJoin(auction.art, art)
                .innerJoin(art.artHashtagList, artHashtag)
                .where(saleTypeEq(SaleType.AUCTION), currentDateBetween(LocalDateTime.now()), hashtagEq(givenHashtag))
                .fetch();

        return PageableExecutionUtils.getPage(content, pageRequest, countQuery::size);
    }

    @Override
    public Page<BasicAuctionArt> findAuctionArtListWithKeyword(String givenKeyword, String sortType, Pageable pageRequest) {
        JPAQuery<BasicAuctionArt> basicQuery = query
                .selectDistinct(new QBasicAuctionArt(
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
        List<BasicAuctionArt> content;
        switch (sortType) {
            case "date":
                content = basicQuery
                        .orderBy(art.registerDate.desc())
                        .fetch();
                break;

            case "rdate":
                content = basicQuery
                        .orderBy(art.registerDate.asc())
                        .fetch();
                break;

            case "price":
                content = basicQuery
                        .orderBy(auction.bidPrice.desc())
                        .fetch();
                break;

            case "rprice":
                content = basicQuery
                        .orderBy(auction.bidPrice.asc())
                        .fetch();
                break;

            case "count":
                content = basicQuery
                        .leftJoin(auction.auctionHistoryList, auctionHistory)
                        .groupBy(COUNT_GROUP_BY_EXPRESSION)
                        .orderBy(auctionHistory.count().desc())
                        .fetch();
                break;

            case "rcount":
                content = basicQuery
                        .leftJoin(auction.auctionHistoryList, auctionHistory)
                        .groupBy(COUNT_GROUP_BY_EXPRESSION)
                        .orderBy(auctionHistory.count().asc())
                        .fetch();
                break;

            default:
                content = new ArrayList<>(); // return empty list
        }

        List<Long> countQuery = query
                .select(auction.id)
                .from(auction)
                .innerJoin(auction.art, art)
                .where(saleTypeEq(SaleType.AUCTION), currentDateBetween(LocalDateTime.now()), keywordContains(givenKeyword))
                .fetch();

        return PageableExecutionUtils.getPage(content, pageRequest, countQuery::size);
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
