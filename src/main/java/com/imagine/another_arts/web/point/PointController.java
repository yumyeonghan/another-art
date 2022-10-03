package com.imagine.another_arts.web.point;

import com.imagine.another_arts.domain.point.service.PointHistoryService;
import com.imagine.another_arts.web.point.dto.request.PointUseRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PointController {
    private final PointHistoryService pointHistoryService;

    @PostMapping("/point/charge")
    @ApiOperation(value = "포인트 충전", notes = "포인트 충전")
    public ResponseEntity<Void> pointCharge(@Valid @RequestBody PointUseRequest pointChargeRequest) {
        pointHistoryService.chargePoint(pointChargeRequest.getLoginId(), pointChargeRequest.getDealAmount());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/point/refund")
    @ApiOperation(value = "포인트 환불", notes = "포인트 환불")
    public ResponseEntity<Void> pointRefund(@Valid @RequestBody PointUseRequest pointRefundRequest) {
        pointHistoryService.refundPoint(pointRefundRequest.getLoginId(), pointRefundRequest.getDealAmount());
        return ResponseEntity.noContent().build();
    }
}
