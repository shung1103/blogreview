package com.example.blogreview.service;

import com.example.blogreview.dto.ApiResponseDto;
import com.example.blogreview.dto.SignupRequestDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    /**
     * 회원가입 API
     * @param requestDto 회원가입 요청 정보
     */

    ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto);
}
