package com.imagine.another_arts.web.point;

import com.imagine.another_arts.domain.login.dto.UserSessionDto;
import com.imagine.another_arts.domain.point.service.PointHistoryService;
import com.imagine.another_arts.exception.UnAuthenticatedUserException;
import com.imagine.another_arts.web.SessionFactory;
import com.imagine.another_arts.web.point.dto.request.PointUseRequest;
import com.imagine.another_arts.web.point.dto.response.PointUseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PointController {

    private final PointHistoryService pointHistoryService;

    @GetMapping("/session-check")
    @ApiOperation(value = "세션 정보 확인", notes = "포인트 충전하기 위한 사용자 확인")
    public PointUseResponse checkUserSessionForPointCharge(@SessionAttribute(name = SessionFactory.ANOTHER_ART_SESSION_KEY, required = false) UserSessionDto userSession) {
        if (userSession == null) {
            throw new UnAuthenticatedUserException("로그인 상태가 아닙니다");
        }

        return new PointUseResponse(
                userSession.getId(),
                userSession.getLoginId(),
                userSession.getName(),
                userSession.getEmail(),
                userSession.getPhoneNumber(),
                userSession.getAddress()
        );
    }

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
