package com.ssm.service.Impl;

import com.ssm.commons.APIURlUtils;
import com.ssm.commons.MD5Utils;
import com.ssm.dto.ResponseDto;
import com.ssm.service.UserService;

import com.ssm.utils.JsonUtils;
import org.springframework.stereotype.Service;
import com.ssm.utils.HttpClientUtils;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public ResponseDto login1(String userAccount, String password) {
        /**
         * HttpClient调用shop-api接口
         */
        Map<String,String> map=new HashMap<>();
        map.put("userAccount",userAccount);
        map.put("password",MD5Utils.encrypt(password));
        //使用工具类 传入参数url和map

        System.out.println("---------------------------------->22222222222222");
        String jsonStr=HttpClientUtils.post(APIURlUtils.LOGIN, map);
        //解析字符串
        ResponseDto responseDto=JsonUtils.jsonToPojo(jsonStr,ResponseDto.class);
        return responseDto;
    }
}
