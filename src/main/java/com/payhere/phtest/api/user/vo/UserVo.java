package com.payhere.phtest.api.user.vo;

import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserVo {

    private Long userNo;

    private String userId;

    private String name;

    private String password;

    private String email;

    private String userState;

}
