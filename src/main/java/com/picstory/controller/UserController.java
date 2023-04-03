package com.picstory.controller;

import com.picstory.dto.LoginUserDto;
import com.picstory.dto.SignUpFormDto;
import com.picstory.dto.TokenRoleDto;
import com.picstory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User 관련 Controller
 *
 * @author 서재건
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 후 로그인 진행
     *
     * @param dto
     * @return tokenRoleDto
     */
    @PostMapping("/signup")
    public ResponseEntity<TokenRoleDto> signup(@RequestBody SignUpFormDto dto) {
        TokenRoleDto tokenRoleDto = userService.signUp(dto);
        return ResponseEntity.ok().body(tokenRoleDto);
    }

    /**
     * 로그인
     *
     * @param dto
     * @return tokenRoleDto
     */
    @PostMapping("/login")
    public ResponseEntity<TokenRoleDto> login(@RequestBody LoginUserDto dto) {
        TokenRoleDto tokenRoleDto = userService.login(dto);
        return ResponseEntity.ok().body(tokenRoleDto);
    }
}
