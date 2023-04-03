package com.picstory.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 dto
 *
 * @author 서재건
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {

    @NotNull
    private String email;
    @NotNull
    private String password;

    public LoginUserDto of(SignUpFormDto dto) {
        return LoginUserDto.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }
}
