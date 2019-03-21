package com.ssm.mapper;


import com.ssm.pojo.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {

    User queryUserAccountAndPwd(@Param("userAccount") String userAccount, @Param("password") String password);
}