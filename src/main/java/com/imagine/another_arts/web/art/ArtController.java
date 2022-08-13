package com.imagine.another_arts.web.art;

import com.imagine.another_arts.domain.art.service.ArtService;
import com.imagine.another_arts.domain.art.service.dto.AuctionArtResponseDto;
import com.imagine.another_arts.exception.ArtNotFoundException;
import com.imagine.another_arts.web.art.dto.SortedArtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class ArtController {
    private final ArtService artService;
    private static final int SLICE_PER_PAGE = 20;

    @GetMapping("/api/main/art")
    public SortedArtResponse<List<AuctionArtResponseDto>> mainArtList(
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "scroll", defaultValue = "0") Integer scroll
    ){
        PageRequest pageRequest = PageRequest.of(scroll, SLICE_PER_PAGE);
        List<AuctionArtResponseDto> sortedAuctionArtList = artService.getSortedAuctionArtList(sort, pageRequest);
        
        if(sortedAuctionArtList.size() == 0){
            throw new ArtNotFoundException("더이상 작품이 존재하지 않습니다");
        }

        return new SortedArtResponse<>(true, sortedAuctionArtList.size(), sortedAuctionArtList);
    }

    @GetMapping("/api/search/art")
    public <T> SortedArtResponse<List<T>> searchArtList(
            @RequestParam(value = "hashtag") String hashtag,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "scroll", defaultValue = "0") Integer scroll
    ) {
        PageRequest pageRequest = PageRequest.of(scroll, SLICE_PER_PAGE);
        List<T> sortedArtListBySearch;

        if (type.equals("auction")) {
            sortedArtListBySearch = (List<T>) artService.getSearchedAuctionArtList(hashtag, sort, pageRequest);
        } else {
            sortedArtListBySearch = (List<T>) artService.getSearchedGeneralArtList(hashtag, sort, pageRequest);
        }

        if(sortedArtListBySearch.size() == 0){
            throw new ArtNotFoundException("더이상 작품이 존재하지 않습니다");
        }

        return new SortedArtResponse<>(true, sortedArtListBySearch.size(), sortedArtListBySearch);
    }
}
