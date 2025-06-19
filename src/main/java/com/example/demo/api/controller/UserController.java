package com.example.demo.api.controller;

import com.example.demo.api.dto.SignupReqDto;
import com.example.demo.api.dto.UserInfoReqDto;
import com.example.demo.api.service.UserService;
import com.example.demo.common.enumulation.ResponseCode;
import com.example.demo.common.dto.Response;
import com.example.demo.config.component.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/user")
    @Operation(summary = "회원정보 조회(가입 여부 확인)", description = "회원정보 조회하기(가입 여부 확인)")
    public ResponseEntity<Response<UserInfoReqDto>> getUserInfo(@RequestBody @Valid UserInfoReqDto reqDto) {
        return Response.success(ResponseCode.OK_SUCCESS, userService.getUserInfo(reqDto));
    }

    @PutMapping("/user")
    @Operation(summary = "회원정보 수정", description = "회원정보 수정하기")
    public ResponseEntity<Response<String>> updateUserInfo(@Valid @RequestBody UserInfoReqDto reqDto, HttpServletRequest request) {
        var userDto = jwtTokenProvider.parseClaims(request.getHeader("Authorization"));
//        reqDto.setUserSeq(userDto.user().getUserSeq());
        userService.updateUserInfo(reqDto, userDto);
        return Response.success(ResponseCode.OK_SUCCESS);
    }

    @DeleteMapping("/user")
    @Operation(summary = "회원탈퇴", description = "회원탈퇴하기")
    public ResponseEntity<Response<String>> deleteUserInfo(HttpServletRequest request) {
        var userDto = jwtTokenProvider.parseClaims(request.getHeader("Authorization"));
        userService.deleteUserInfo(userDto);
        return Response.success(ResponseCode.OK_SUCCESS);
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입하기", description = "회원 가입하기")
    public ResponseEntity<Response<String>> signup(@RequestBody @Valid SignupReqDto reqDto) {
        userService.signup(reqDto);
        return Response.success(ResponseCode.OK_SUCCESS);
    }



    /*

    TODO : 회원정보 수정, 비밀번호 변경, 회원탈퇴


    */
}
