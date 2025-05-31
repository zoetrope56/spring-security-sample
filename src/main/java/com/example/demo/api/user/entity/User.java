package com.example.demo.api.user.entity;

import com.example.demo.common.enumulation.UserGrant;
import com.example.demo.common.enumulation.UserState;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false)
    private Long userSeq;

    @Column(name = "id", nullable = false)
    private String userId;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String userPassword;

    @Column(name = "grant", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserGrant userGrant;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserState userState;


}
