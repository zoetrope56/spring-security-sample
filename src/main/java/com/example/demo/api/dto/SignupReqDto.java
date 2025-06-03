package com.example.demo.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class SignupReqDto {

    private String name;

    @NotBlank(message = "연락처를 입력해주세요")
    private String mobile;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

}
