package com.example.demo.api.entity.user;

import com.example.demo.api.entity.BaseTimeEntity;
import com.example.demo.common.enumulation.UserGrant;
import com.example.demo.common.enumulation.UserState;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@Table(name = "user_mst")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ", nullable = false)
    private Long userSeq;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "USERNAME", nullable = false)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    private String userPassword;

    @Column(name = "GRANT", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserGrant userGrant;

    @Column(name = "STATE", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserState userState;


}
