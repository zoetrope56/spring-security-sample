package com.example.demo.api.mapper;

import com.example.demo.api.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    User selectUserById(String userId);

    Optional<User> selectUserByName(String username);

    Optional<User> selectUserByPhone(String phoneNum);

    void insertUser(User user);

}
