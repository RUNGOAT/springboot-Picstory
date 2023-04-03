package com.picstory.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 회원가입 Dto
 *
 * @author 서재건
 */
@Getter
@Builder
@ToString
@AllArgsConstructor
public class SignUpFormDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String nickname;
    @NotNull
    private String code;    // 이메일 인증 코드
}
