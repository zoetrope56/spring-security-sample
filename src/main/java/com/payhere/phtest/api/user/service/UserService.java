package com.payhere.phtest.api.user.service;

import com.payhere.phtest.api.user.dto.LoginReqDto;
import com.payhere.phtest.api.user.dto.SignupReqDto;
import com.payhere.phtest.api.user.dto.UserDto;
import com.payhere.phtest.api.user.mapper.UserMapper;
import com.payhere.phtest.api.user.vo.UserVo;
import com.payhere.phtest.common.enumulation.ResponseCode;
import com.payhere.phtest.config.exception.DataConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserDto getUserInfo(Long userId) {
        final var userVo = userMapper.selectUserById(userId);
        UserDto userDto = UserDto.builder().build();
        BeanUtils.copyProperties(userVo, userDto);
        return userDto;
    }

    /**
     * 회원가입하기
     *
     * @param reqDto 회원가입 요청 객체
     */
    public void signup(SignupReqDto reqDto) {
        // 연락처 중복 체크
        if(userMapper.selectUserByPhone(reqDto.getMobile()).isPresent())
            throw new DataConflictException(ResponseCode.CONFLICT_USER_ERROR.getMessage());

        // vo create
        UserVo user = UserVo.builder()
                .userName(reqDto.getName())
                .mobile(reqDto.getMobile())
                .password(passwordEncoder.encode(reqDto.getPassword()))
                .build();
        // insert
        this.userMapper.insertUser(user);
    }

    public void login(LoginReqDto reqDto) {

    }

    public void logout() {

    }
}
