package com.example.demo.api.service;

import com.example.demo.api.dto.LoginReqDto;
import com.example.demo.api.dto.UserDto;
import com.example.demo.api.dto.SignupReqDto;
import com.example.demo.api.mapper.UserMapper;
import com.example.demo.api.entity.user.User;
import com.example.demo.common.enumulation.ResponseCode;
import com.example.demo.common.enumulation.UserState;
import com.example.demo.config.exception.DataConflictException;
import com.example.demo.config.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 정보 반환 (가입 여부 확인)
     *
     * @param reqDto 회원정보 요청 객체
     * @return 회원 정보
     */
    public UserDto getUserInfo(UserDto reqDto) {
        final var userVo = userMapper.selectUserById(reqDto.getUserId());
        // userVo null check
        if (ObjectUtils.isEmpty(userVo))
            throw new DataNotFoundException(UserState.USER_NOT_FOUND.getDesc());

        UserDto userDto = UserDto.builder().build();
        BeanUtils.copyProperties(userVo, userDto);
        return userDto;
    }

    /**
     * 회원 가입하기
     *
     * @param reqDto 회원가입 요청 객체
     */
    public void signup(SignupReqDto reqDto) {
        // 연락처 중복 체크
        if (userMapper.selectUserByName(reqDto.getUsername()).isPresent())
            throw new DataConflictException(ResponseCode.CONFLICT_USER_ERROR.getMessage());

        // vo create
        User user = User.builder()
                .userName(reqDto.getUsername())
                .userPassword(passwordEncoder.encode(reqDto.getPassword()))
                .build();
        this.userMapper.insertUser(user);
    }

    public void login(LoginReqDto reqDto) {

    }

    public void logout() {

    }
}
