package com.example.demo.api.controller;

import com.example.demo.api.dto.LoginReqDto;
import com.example.demo.api.dto.UserDetailDto;
import com.example.demo.common.dto.Response;
import com.example.demo.common.enumulation.ResponseCode;
import com.example.demo.config.component.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/generate")
    @Operation(summary = "토큰 생성", description = "토큰 생성하기")
    public ResponseEntity<Response<String>> generateToken(@Valid @RequestBody LoginReqDto reqDto, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(reqDto.getUserId(), reqDto.getPassword())
        );

        var principal = (UserDetailDto) authentication.getPrincipal();
        val accessToken = this.jwtTokenProvider.generateToken(principal.user());

        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(accessToken));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        return Response.success(ResponseCode.OK_SUCCESS, accessToken);
    }

}
