package com.payhere.phtest.api.user.service;

import com.payhere.phtest.api.user.dto.UserResDto;
import com.payhere.phtest.api.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public UserResDto getUserInfo(Long userId) {
        final var userVo = userMapper.selectUserById(userId);
        UserResDto userDto = UserResDto.builder().build();
        BeanUtils.copyProperties(userVo, userDto);
        return userDto;
    }
}
