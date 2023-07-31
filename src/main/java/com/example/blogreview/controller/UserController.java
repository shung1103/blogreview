package com.example.blogreview.controller;

import com.example.blogreview.dto.ApiResponseDto;
import com.example.blogreview.dto.SignupRequestDto;
import com.example.blogreview.security.JwtAuthenticationFilter;
import com.example.blogreview.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @RequestMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
        jwtAuthenticationFilter.deleteAuthentication(response, authResult);
        return ResponseEntity.status(201).body(new ApiResponseDto("로그아웃 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }
}
