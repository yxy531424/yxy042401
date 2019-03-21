package com.ssm.service;

import com.ssm.pojo.User;
import com.ssm.vo.ResultVO;

public interface UserService {

    User login(String userAccount, String password);

    User login1(String userAccount, String password);

    boolean modifyUserHeadImage(Long userId, String uploadStr);

    ResultVO showUsersPageTest(Integer page, Integer limit);

    boolean checkAccount(String userAccount);

    boolean addUser(User user);
    ResultVO showUsersPage(Integer page, Integer limit, String no, String mobileNumber, Integer status);

    User queryUserById(Long userId);

    boolean modifyUser(User user);

    boolean deleteUserById(Long id);

    boolean deletebatchUserById(Long[] ids);

    boolean updateActive(Long userId, Boolean status);
}
