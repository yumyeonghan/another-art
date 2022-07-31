package com.imagine.another_arts.web.art;

import com.imagine.another_arts.domain.art.service.ArtService;
import com.imagine.another_arts.exception.ArtNotFoundException;
import com.imagine.another_arts.web.art.dto.ResultSortedArt;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArtController {
    private final ArtService artService;
    private static final int SLICE_PER_PAGE = 20;

    @GetMapping("/test")
    public ResultSortedArt<List<Object>> artList(
            @RequestParam(value = "sort", defaultValue = "RD") String sort,
            @RequestParam(value = "lastArtId", defaultValue = Long.MAX_VALUE + "") Long lastArtId,
            @RequestParam(value = "scroll", defaultValue = "0") Integer scroll
    ){
        PageRequest pageRequest = PageRequest.of(scroll, SLICE_PER_PAGE);
        List<Object> auctionArtSortList = artService.getSortedAuctionArtList(sort, lastArtId, pageRequest);

        if(auctionArtSortList.size() == 0){
            throw new ArtNotFoundException("작품을 찾지 못하였습니다");
        }

        return new ResultSortedArt<>(true, auctionArtSortList.size(), auctionArtSortList);
    }
}
