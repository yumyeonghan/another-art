package com.imagine.another_arts.web.point;

import com.imagine.another_arts.domain.login.dto.UserDto;
import com.imagine.another_arts.domain.point.service.PointHistoryService;
import com.imagine.another_arts.exception.IllegalUserInfoFoundException;
import com.imagine.another_arts.web.SessionConst;
import com.imagine.another_arts.web.point.dto.request.PointUseRequest;
import com.imagine.another_arts.web.point.dto.response.PointUseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PointController {

    private final PointHistoryService pointHistoryService;

    @GetMapping("/point-charge-user-inf")
    @ApiOperation(value = "세션 정보 확인", notes = "포인트 충전하기 위한 사용자 확인")
    public PointUseResponse pointChargeUserInf(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) UserDto loginUser) {

        if (loginUser == null) {
            throw new IllegalUserInfoFoundException("로그인 상태가 아닙니다.");
        }

        return new PointUseResponse(
                loginUser.getId(),
                loginUser.getLoginId(),
                loginUser.getName(),
                loginUser.getEmail(),
                loginUser.getPhoneNumber(),
                loginUser.getAddress());
    }

    @PostMapping("/point-charge")
    @ApiOperation(value = "포인트 충전", notes = "포인트 충전")
    public ResponseEntity<Void> pointCharge(@RequestBody PointUseRequest pointChargeRequest) {

        pointHistoryService.chargePoint(pointChargeRequest.getLoginId(), pointChargeRequest.getAmount());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/point-refund")
    @ApiOperation(value = "포인트 환불", notes = "포인트 환불")
    public ResponseEntity<Void> pointRefund(@RequestBody PointUseRequest pointChargeRequest) {

        pointHistoryService.refundPoint(pointChargeRequest.getLoginId(), pointChargeRequest.getAmount());

        return ResponseEntity.noContent().build();
    }

}
