package com.picstory.controller;

import com.picstory.exception.CustomException;
import com.picstory.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * security 에서 authentication, authorization 에러 전달받아 throw 하는 Controller
 *
 * @author 서재건
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/exception")
public class ExceptionController {

    /**
     * CustomAuthenticationEntryPoint 에서 redirect 로 오면 authentication error throw
     */
    @GetMapping("/entrypoint")
    public void entrypointException() {
        throw new CustomException(ErrorCode.FAIL_AUTHENTICATION);
    }

    /**
     * CustomAccessDeniedHandler 에서 redirect 로 오면 authorization error throw
     */
    @GetMapping("/authorization")
    public void deniedHandler() {
        throw new CustomException(ErrorCode.FAIL_AUTHORIZATION);
    }
}
