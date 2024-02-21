package com.example.demo.api.user.mapper;

import com.example.demo.api.user.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    UserVo selectUserById(Long userId);

    UserVo selectUserByName(String username);

    Optional<UserVo> selectUserByPhone(String phoneNum);

    void insertUser(UserVo user);

}
