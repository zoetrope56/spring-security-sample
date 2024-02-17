package com.payhere.phtest.api.user.controller;

import com.payhere.phtest.api.user.dto.UserResDto;
import com.payhere.phtest.api.user.service.UserService;
import com.payhere.phtest.common.enumulation.ResponseCode;
import com.payhere.phtest.common.vo.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/info")
    public ResponseEntity<Response<UserResDto>> getUserInfo(Long userId) {
        return Response.success(ResponseCode.OK_SUCCESS, userService.getUserInfo(userId));
    }

}
