package com.ssm.service.impl;


import com.ssm.mapper.RoleMapper;
import com.ssm.mapper.UserRoleMapper;
import com.ssm.pojo.Role;
import com.ssm.service.RoleService;
import com.ssm.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private  RoleMapper roleMapper;
    
    


    @Override
    public List<Role> queryAllRoles() {
        return userRoleMapper.selectAll();
    }

    @Override
    public boolean editUserRids(Long userId, Long[] rids) {
        if(userId!=null){
            userRoleMapper.deleteRoleById(userId);
            if(rids!=null&& rids.length>0){
                userRoleMapper.insertRoleById(userId,rids);
            }
            return true;
        }
        return false;

    }

    @Override
    public ResultVO queryAllRolesList(String no, Integer status) {
        List<Role> list=roleMapper.countRoleByExample(no,status);
        Long count=roleMapper.countLongRole(no,status);
        return ResultVO.success(count,list);
    }

    @Override
    public Role queryByRoleUkid(Long roleUkid) {
        if(roleUkid!=null){
           Role role= roleMapper.queryByRoleUkid(roleUkid);
           role.setModifyTime(new Date());
           return role;
        }
        return null;


    }

    @Override
    public boolean queryByRoleUkidSuccess(Role role) {
        return  roleMapper.modifySuccess(role);

    }


}
