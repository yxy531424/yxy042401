package com.ssm.controller;

import com.github.pagehelper.PageHelper;
import com.ssm.pojo.Role;
import com.ssm.service.RoleService;
import com.ssm.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequestMapping("/role/allroles")
    @ResponseBody
    //返回json格式的对象
    public Object queryAllRolse(){

        return roleService.queryAllRoles();
    }

    @RequestMapping("/userroleedit")
    @ResponseBody
    public  Object userRoleEdit(@RequestParam Long userId,
                                Long[] rids){
        //先根据userId删除原来的角色,然后增加rids
        boolean f=roleService.editUserRids(userId,rids);
        if(f){
            return ResultVO.success();
        }
        return ResultVO.error();
    }

    @RequestMapping("/role/list")
    @ResponseBody
    public  Object  roleList(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer limit,
                             String no,
                             Integer status){
        //单表直接用PageHelper
        PageHelper.startPage(page,limit);
        ResultVO vo=roleService.queryAllRolesList(no,status);

        return  vo;

    }

    @RequestMapping("/role/querybyid")
    @ResponseBody
    public Object roleObject(Long roleUkid,Model model){
        Role role=roleService.queryByRoleUkid(roleUkid);
        model.addAttribute("role",role);
        return role;
    }
    @RequestMapping("/role/editrole")
    @ResponseBody
    public Object roleObjectSuccess(Role role){
        boolean f=roleService.queryByRoleUkidSuccess(role);
        if(f){
            return ResultVO.success();
        }
        return ResultVO.error();
    }


}
