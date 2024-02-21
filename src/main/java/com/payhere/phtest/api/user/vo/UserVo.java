package com.payhere.phtest.api.user.vo;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class UserVo {

    private Long userNo;

    private String userId;

    private String userName;

    private String password;

    private String mobile;

}
