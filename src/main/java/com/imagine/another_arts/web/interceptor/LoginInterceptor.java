package com.imagine.another_arts.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagine.another_arts.exception.ErrorDescription;
import com.imagine.another_arts.web.SessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        String requestURI = request.getRequestURI();
        log.info("요청 URI : {}", requestURI);

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionFactory.ANOTHER_ART_SESSION_KEY) == null) {
            log.info("미인증 사용자 요청");

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ErrorDescription errorDescription = new ErrorDescription(
                    false,
                    "UNAUTHORIZED",
                    "비로그인 사용자 요청"
            );

            String responseJsonString = objectMapper.writeValueAsString(errorDescription);

            response.getWriter().write(responseJsonString);

            return false;
        }
        return true;
    }
}
