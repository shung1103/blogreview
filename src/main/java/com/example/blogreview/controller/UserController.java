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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @GetMapping("/login-page")
    public String loginPage() {return "login";}

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @RequestMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
        jwtAuthenticationFilter.deleteAuthentication(response, authResult);
        return ResponseEntity.status(201).body(new ApiResponseDto("로그아웃 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size()>0) {
            for (FieldError fieldError: bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + "필드: "+fieldError.getDefaultMessage());
            }
            return "redirect:/api/user/signup";
        }

        userService.signup(requestDto);

        return  "redirect:/api/user/login-page";
    }
}
