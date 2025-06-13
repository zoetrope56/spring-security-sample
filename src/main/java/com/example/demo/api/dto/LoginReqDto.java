package com.example.demo.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Schema(description = "로그인 요청 dto")
public class LoginReqDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -6637912590063927858L;

    @NotBlank(message = "회원 아이디가 없습니다.")
    @Schema(description = "회원 ID")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 문자, 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @Schema(description = "비밀번호")
    private String password;

}
