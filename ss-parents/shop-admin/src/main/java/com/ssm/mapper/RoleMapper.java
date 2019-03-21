package com.ssm.mapper;

import com.ssm.pojo.Role;
import com.ssm.pojo.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Long roleUkid);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Long roleUkid);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);


    Long countLongRole(@Param("no") String no, @Param("status") Integer status);

    List<Role> countRoleByExample(@Param("no") String no, @Param("status") Integer status);

    Role queryByRoleUkid(Long roleUkid);

    boolean modifySuccess(Role role);
}