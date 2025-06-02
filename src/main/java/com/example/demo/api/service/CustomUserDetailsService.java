package com.example.demo.api.service;

import com.example.demo.api.dto.UserDetailDto;
import com.example.demo.api.mapper.UserMapper;
import com.example.demo.api.entity.user.User;
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
        Optional<User> user = this.userMapper.selectUserByName(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("<UNK>");
        }

        return new UserDetailDto(user.get());
    }
}
