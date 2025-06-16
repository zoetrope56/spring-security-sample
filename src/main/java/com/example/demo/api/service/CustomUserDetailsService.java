package com.example.demo.api.service;

import com.example.demo.api.dto.UserDetailDto;
import com.example.demo.api.mapper.UserMapper;
import com.example.demo.api.entity.user.User;
import com.example.demo.common.enumulation.ResponseCode;
import com.example.demo.common.enumulation.UserState;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    /**
     * 사용자 정보 조회
     *
     * @param username 회원 이름
     * @return 회원 정보
     * @throws UsernameNotFoundException 회원 이름 정보 존재하지 않을 경우 예외처리
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userMapper.selectUserByName(username);
        if (user.isPresent())
            throw new UsernameNotFoundException(UserState.USER_NOT_FOUND.getDesc());

        return new UserDetailDto(user.get());
    }
}
