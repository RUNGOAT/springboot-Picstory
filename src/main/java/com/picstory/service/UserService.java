package com.picstory.service;

import com.picstory.code.Role;
import com.picstory.config.security.jwt.JwtTokenProvider;
import com.picstory.dto.LoginUserDto;
import com.picstory.dto.SignUpFormDto;
import com.picstory.dto.TokenRoleDto;
import com.picstory.entity.User;
import com.picstory.exception.CustomException;
import com.picstory.exception.ErrorCode;
import com.picstory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User 관련 Service
 *
 * @author 서재건
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인한 유저 반환
     *
     * @return User
     */
    public static User getLogInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }


    /**
     * 회원가입 및 로그인
     *
     * @param dto   회원가입 Dto
     * @return  회원가입 후 로그인을 통한 토큰 dto 반환
     */
    public TokenRoleDto signUp(SignUpFormDto dto) {
        log.info("[SignUp] SignUpFormDto 객체: {}", dto.toString());

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException(ErrorCode.HAS_USER);
        }
        User user = User.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        log.info("[Signup] 회원 등록 완료");

        return login(new LoginUserDto().of(dto));
    }

    /**
     * 로그인 후 Jwt 토큰 반환
     *
     * @param dto   로그인 Dto
     * @return Jwt 토큰 Dto
     */
    public TokenRoleDto login(LoginUserDto dto) {
        log.info("[login] 로그인 진행");
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        log.info("[login] User 존재");
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw  new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        log.info("[login] Jwt 토큰 생성");
        String token = jwtTokenProvider.createTokne(String.valueOf(user.getId()), user.getRole().getName());

        log.info("[login] Jwt 토큰 반환");
        return new TokenRoleDto().of(user.getEmail(), user.getNickname(), token);
    }
}
