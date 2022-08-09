package com.imagine.another_arts.web.art;

import com.imagine.another_arts.domain.art.service.ArtService;
import com.imagine.another_arts.domain.art.service.dto.SortedAuctionArtDto;
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
public class ArtController {
    private final ArtService artService;
    private static final int SLICE_PER_PAGE = 20;

    @GetMapping("/test")
    public SortedArtResponse<List<SortedAuctionArtDto>> artList(
            @RequestParam(value = "sort", defaultValue = "RD") String sort,
            @RequestParam(value = "scroll", defaultValue = "0") Integer scroll
    ){
        PageRequest pageRequest = PageRequest.of(scroll, SLICE_PER_PAGE);
        List<SortedAuctionArtDto> sortedArtListInAuction = artService.getSortedArtListInAuction(sort, pageRequest);

        if(sortedArtListInAuction.size() == 0){
            throw new ArtNotFoundException("더이상 작품이 존재하지 않습니다");
        }

        return new SortedArtResponse<>(true, sortedArtListInAuction.size(), sortedArtListInAuction);
    }
}
