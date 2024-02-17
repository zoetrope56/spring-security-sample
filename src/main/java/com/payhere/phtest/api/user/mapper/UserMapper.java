package com.payhere.phtest.api.user.mapper;

import com.payhere.phtest.api.user.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserVo selectUserById(Long userId);

}
