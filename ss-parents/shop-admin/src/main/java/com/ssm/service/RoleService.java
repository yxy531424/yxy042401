package com.ssm.service;


import com.ssm.pojo.Role;
import com.ssm.vo.ResultVO;

import java.util.List;

public interface RoleService {


    List<Role> queryAllRoles();

    boolean editUserRids(Long userId, Long[] rids);

    ResultVO queryAllRolesList(String no,Integer status);

    Role queryByRoleUkid(Long roleUkid);

    boolean queryByRoleUkidSuccess(Role role);
}
