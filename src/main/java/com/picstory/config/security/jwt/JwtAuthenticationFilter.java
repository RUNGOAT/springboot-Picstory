package com.picstory.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picstory.exception.ErrorCode;
import com.picstory.exception.ErrorResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt 토큰을 받았을 때 유효성 검증을 하고 SecurityContextHolder 에 저장
 *
 * @author 서재건
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Request 로 들어오는 Jwt Token 의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter 를 filterChain 에 등록합니다.
     *
     * @param request  The request to process
     * @param response The response associated with the request
     * @param filterChain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     *
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.resolveToken(request);
            log.info("[doFilter] token: {}", token);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                log.info("### SecurityContextHolder auth 등록 ###");
            }
        } catch (Exception e) {
            log.info("[Filter] error");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), ErrorResponseEntity.toReponseEntity(ErrorCode.INTER_SERVER_ERROR).getBody());
        }
        filterChain.doFilter(request, response);
    }
}
