package com.example.demo.api.mapper;

import com.example.demo.api.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Integer existsUserID(String username);

    Optional<User> selectUserById(String userId);

    Optional<User> selectUserByName(String username);

    Integer insertUser(User user);

    Integer updateUserInfo(User user);

    Integer changeUserPassword(User user);

    Integer deleteUser(User user);
}
