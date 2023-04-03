package com.picstory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picstory.dto.LoginUserDto;
import com.picstory.dto.SignUpFormDto;
import com.picstory.dto.TokenRoleDto;
import com.picstory.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserController 관련 테스트 작성
 *
 * @author 서재건
 */
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    private static SignUpFormDto signUpDto;

    @PostConstruct
    void init() {
        signUpDto = new SignUpFormDto("test@naver.com", "1234", "test", "code");
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("회원가입")
    void signup() throws Exception {
        //given
        given(userService.signUp(signUpDto))
                .willReturn(new TokenRoleDto().of(signUpDto.getEmail(), signUpDto.getNickname(), "token"));

        //when
        String content = objectMapper.writeValueAsString(signUpDto);

        //then
        mockMvc.perform(
                post("/api/accounts/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                ;
    }

    @Test
    @DisplayName("로그인")
    void login() throws Exception {
        //given
        LoginUserDto dto = new LoginUserDto().of(signUpDto);
        given(userService.login(dto))
                .willReturn(new TokenRoleDto().of(signUpDto.getEmail(), signUpDto.getNickname(), "token"));

        //when
        String content = objectMapper.writeValueAsString(dto);

        //then
        mockMvc.perform(
                post("/api/accounts/login")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }
}