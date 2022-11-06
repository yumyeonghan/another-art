package com.imagine.another_arts.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagine.another_arts.exception.ErrorResponse;
import com.imagine.another_arts.web.SessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.imagine.another_arts.exception.AnotherArtErrorCode.AUTHENTICATION_USER;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String requestURI = request.getRequestURI();
        log.info("요청 URI : {}", requestURI);

        HttpSession session = request.getSession(false);
        if (Objects.isNull(session) || Objects.isNull(session.getAttribute(SessionFactory.ANOTHER_ART_SESSION_KEY))) {
            log.info("미인증 사용자 요청");
            String errorResponseToJSON = objectMapper.writeValueAsString(ErrorResponse.of(AUTHENTICATION_USER));

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(errorResponseToJSON);
            return false;
        }

        return true;
    }
}
