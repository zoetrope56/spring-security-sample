package com.example.demo.api.user.service;

import com.example.demo.api.user.mapper.UserMapper;
import com.example.demo.api.user.vo.UserVo;
import com.example.demo.common.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo userVo = userMapper.selectUserByName(username);
        return new UserInfo(userVo);
    }
}
