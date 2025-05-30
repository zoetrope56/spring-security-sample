package com.example.demo.api.user.controller;

import com.example.demo.api.user.dto.LoginReqDto;
import com.example.demo.api.user.dto.SignupReqDto;
import com.example.demo.api.user.dto.UserDto;
import com.example.demo.api.user.service.UserService;
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

    /**
     * 유저 정보 조회
     *
     * @return 유저 정보
     */
    @GetMapping("/info")
    public ResponseEntity<Response<UserDto>> getUserInfo(Long userId) {
        // TODO Deprecated
        return Response.success(ResponseCode.OK_SUCCESS, userService.getUserInfo(userId));
    }

    /**
     * 회원가입하기
     *
     * @param reqDto 회원가입 요청 객체
     * @return 성공 응답
     */
    @PostMapping("/signup")
    public ResponseEntity<Response<String>> signup(@RequestBody @Valid SignupReqDto reqDto, BindingResult errors) {
        if (errors.hasErrors())
            throw new ValidationException(ResponseCode.INVALID_DATA_ERROR.getMessage());
        userService.signup(reqDto);
        return Response.success(ResponseCode.OK_SUCCESS);
    }

    /**
     * 로그인하기
     *
     * @param reqDto 로그인 요청 객체
     * @return 성공 응답
     */
    @Operation(summary = "로그인하기", description = "로그인하기")
    @PostMapping("/login")
    public ResponseEntity<Response<String>> login(@RequestBody SignupReqDto reqDto, HttpServletRequest request, HttpServletResponse response) {
        userService.login(reqDto);
        return Response.success(ResponseCode.OK_SUCCESS);
    }

    /**
     * 로그아웃 하기
     *
     * @return 성공 응답
     */
    @Operation(summary = "로그아웃하기", description = "로그아웃하기")
    @PostMapping("/logout")
    public ResponseEntity<Response<String>> logout(BindingResult errors) {
        userService.logout();
        return Response.success(ResponseCode.OK_SUCCESS);
    }

}
