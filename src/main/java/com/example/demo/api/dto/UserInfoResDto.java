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
public class UserInfoResDto {

    @Schema(description = "회원 일련번호")
    private Long userSeq;

    @Schema(description = "회원 ID")
    private String userId;

    @Schema(description = "회원 이름")
    private String username;

    @Schema(description = "비밀번호")
    private String password;

    @Schema(description = "회원 상태")
    private String userState;

    @Schema(description = "회원 권한")
    private String userGrant;

}
