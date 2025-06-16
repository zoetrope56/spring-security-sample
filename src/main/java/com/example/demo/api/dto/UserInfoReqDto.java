package com.example.demo.api.dto;


import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoReqDto {

    @Hidden
    @Schema(description = "회원 일련번호")
    private Long userSeq;

    @NotBlank(message = "회원 아이디가 없습니다.")
    @Schema(description = "회원 ID")
    private String userId;

    @NotBlank(message = "회원 이름이 없습니다.")
    @Schema(description = "회원 이름")
    private String username;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 문자, 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @Schema(description = "비밀번호")
    private String password;

    @Schema(description = "회원 상태")
    private String userState;

    @Schema(description = "회원 권한")
    private String userGrant;

}
