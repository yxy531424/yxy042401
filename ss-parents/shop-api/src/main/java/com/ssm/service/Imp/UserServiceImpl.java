package com.ssm.service.Imp;


import com.ssm.dto.ResponseDto;
import com.ssm.dto.UserDto;


import com.ssm.mapper.UserMapper;
import com.ssm.pojo.User;
import com.ssm.service.UserService;
import com.ssm.utils.ErrorCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public ResponseDto login(String userAccount, String password) {

        User user=userMapper.queryUserAccountAndPwd(userAccount,password);
        if (user==null){
            return ResponseDto.error(ErrorCode.LOGIN_ERROR,"用户名或者密码错误！");
        }
        UserDto userDto=new UserDto();
        BeanUtils.copyProperties(user,userDto);
        System.out.println(userDto+"---------------");
        if(userDto!=null) {
            ResponseDto dto = new ResponseDto();
            dto.setError_code(200);
            dto.setObj(userDto);
            return dto;
        }

        return ResponseDto.error(ErrorCode.LOGIN_ERROR,"用户名或者密码错误！");
    }
}
