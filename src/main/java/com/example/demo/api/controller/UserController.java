package com.example.demo.api.controller;

import com.example.demo.api.dto.LoginReqDto;
import com.example.demo.api.dto.SignupReqDto;
import com.example.demo.api.dto.UserDto;
import com.example.demo.api.service.UserService;
import com.example.demo.common.enumulation.ResponseCode;
import com.example.demo.common.dto.Response;
import com.example.demo.config.exception.ValidationException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/info")
    @Operation(summary = "회원 정보 조회(가입 여부 확인)", description = "회원 정보 조회(가입 여부 확인)")
    public ResponseEntity<Response<UserDto>> getUserInfo(@RequestBody @Valid UserDto reqDto) {
        return Response.success(ResponseCode.OK_SUCCESS, userService.getUserInfo(reqDto));
    }

    @PostMapping("/signup")
    @Operation(summary = "회원 가입하기", description = "회원 가입하기")
    public ResponseEntity<Response<String>> signup(@RequestBody @Valid SignupReqDto reqDto) {
        userService.signup(reqDto);
        return Response.success(ResponseCode.OK_SUCCESS);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인하기", description = "로그인하기")
    public ResponseEntity<Response<String>> login(@RequestBody LoginReqDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        userService.login(reqDto);
        return Response.success(ResponseCode.OK_SUCCESS);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃하기", description = "로그아웃하기")
    public ResponseEntity<Response<String>> logout(BindingResult errors) {
        userService.logout();
        return Response.success(ResponseCode.OK_SUCCESS);
    }

}
