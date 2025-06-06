package com.example.demo.api.dto;


import com.example.demo.common.enumulation.UserGrant;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userSeq;
    private String userId;
    private String userName;
    private String userPassword;
    private String userState;
    private String userGrant;

    public static UserDto of(Long userSeq, String userId, String userName, String userPassword, String userState,  String userGrant) {
        return new UserDto(userSeq, userId, userName, userPassword, userState, userGrant);
    }

    // security 에서 사용할 메소드
    public static UserDto of(String userId) {
        return new UserDto(null, userId, null, null, null, null);
    }

}
