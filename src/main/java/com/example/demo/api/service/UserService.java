package com.example.demo.api.service;

import com.example.demo.api.dto.UserDetailDto;
import com.example.demo.api.dto.UserInfoReqDto;
import com.example.demo.api.dto.SignupReqDto;
import com.example.demo.api.dto.UserInfoResDto;
import com.example.demo.api.mapper.UserMapper;
import com.example.demo.api.entity.user.User;
import com.example.demo.common.enumulation.ResponseCode;
import com.example.demo.common.enumulation.UserGrant;
import com.example.demo.common.enumulation.UserState;
import com.example.demo.config.exception.DataConflictException;
import com.example.demo.config.exception.DataNotFoundException;
import jakarta.validation.Valid;
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
     * 회원정보 조회 (가입 여부 확인)
     *
     * @param reqDto 회원정보 요청 객체
     * @return 회원정보
     */
    public UserInfoResDto getUserInfo(@Valid UserInfoReqDto reqDto) {
        final var user = userMapper.selectUserById(reqDto.getUserId());
        // null check
        if (ObjectUtils.isEmpty(user))
            throw new DataNotFoundException(ResponseCode.NOT_FOUND_DATA.getMessage());

        UserInfoResDto infoDto = UserInfoResDto.builder().build();
        BeanUtils.copyProperties(user, infoDto);
        return infoDto;
    }

    /**
     * 회원정보 수정
     *
     * @param reqDto  회원정보 수정요청 객체
     * @param userDto 회원정보 수정 전 객체
     */
    public void updateUserInfo(UserInfoReqDto reqDto, UserDetailDto userDto) {
        // ID 중복 체크
        if (reqDto.getUserId() != null) {
            if (userMapper.existsUserID(reqDto.getUserId()) != 0)
                throw new DataConflictException("이미 존재하는 ID 입니다.", ResponseCode.CONFLICT_DATA_ERROR);
        }

        // user seq 찾아서 해당 컬럼으로 조회 후 업데이트
        User user = User.builder()
                .userSeq(userDto.user().getUserSeq())
                .userId(reqDto.getUserId())
                .userName(reqDto.getUsername())
                .password(passwordEncoder.encode(reqDto.getPassword()))
                .userState(UserState.valueOf(reqDto.getUserState()))
                .userGrant(UserGrant.valueOf(reqDto.getUserGrant()))
                .build();
        userMapper.updateUserInfo(user);
    }

    /**
     * 회원가입
     *
     * @param reqDto 회원가입 요청 객체
     */
    public void signup(SignupReqDto reqDto) {
        // ID 중복 체크
        if (userMapper.existsUserID(reqDto.getUserId()) != 0)
            throw new DataConflictException("이미 존재하는 ID 입니다.", ResponseCode.CONFLICT_DATA_ERROR);

        // entity creates
        User user = User.builder()
                .userId(reqDto.getUserId())
                .userName(reqDto.getUsername())
                .password(passwordEncoder.encode(reqDto.getPassword()))
                .userGrant(UserGrant.USER)
                .userState(UserState.USER_ALIVE)
                .build();
        this.userMapper.insertUser(user);
    }

    public void deleteUserInfo(UserDetailDto userDto) {
        final var user = userMapper.selectUserById(userDto.user().getUserId());
        // null check
        if (ObjectUtils.isEmpty(user))
            throw new DataNotFoundException(ResponseCode.NOT_FOUND_DATA.getMessage());
        userMapper.deleteUser(user.get());
    }
}
