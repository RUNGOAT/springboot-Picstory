package com.picstory.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 후 리턴값으로 제공할 Dto
 *
 * @author 서재건
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRoleDto {

    @NotNull
    private String email;
    @NotNull
    private String nickname;
    @NotNull
    private String token;

    public TokenRoleDto of(String email, String nickname, String token) {
        return TokenRoleDto.builder()
                .email(email)
                .nickname(nickname)
                .token(token)
                .build();
    }

}
