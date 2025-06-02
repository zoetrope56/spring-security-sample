package com.example.demo.api.user.mapper;

import com.example.demo.api.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    User selectUserById(Long userId);

    Optional<User> selectUserByName(String username);

    Optional<User> selectUserByPhone(String phoneNum);

    void insertUser(User user);

}
