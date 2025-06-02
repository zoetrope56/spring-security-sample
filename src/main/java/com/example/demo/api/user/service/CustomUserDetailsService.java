package com.example.demo.api.user.service;

import com.example.demo.api.user.dto.UserDetailDto;
import com.example.demo.api.user.dto.UserDto;
import com.example.demo.api.user.mapper.UserMapper;
import com.example.demo.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userMapper.selectUserByName(username);
        Optional<User> user = this.userMapper.selectUserByName(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("<UNK>");
        }

        return new UserDetailDto(user.get());
    }
}
