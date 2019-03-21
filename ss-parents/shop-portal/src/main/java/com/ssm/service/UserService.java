package com.ssm.service;

import com.ssm.dto.ResponseDto;

public interface UserService {
    ResponseDto login1(String userAccount, String password);
}
