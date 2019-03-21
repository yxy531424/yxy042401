package com.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.ssm.mapper.UserMapper;
import com.ssm.pojo.Role;
import com.ssm.pojo.User;
import com.ssm.pojo.UserExample;
import com.ssm.pojo.UserRoleExpand;
import com.ssm.service.UserService;
import com.ssm.utils.MD5Utils;
import com.ssm.vo.ResultVO;
import com.ssm.vo.UserRoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String userAccount, String password) {
        //密码加密 encrypt(加密)
        password = MD5Utils.encrypt(password);
        //调用mapper
        UserExample example = new UserExample();
        example.createCriteria().andUserAccountEqualTo(userAccount)
                .andPasswordEqualTo(password)
                .andStatusEqualTo(1);//1可用 0:禁用
        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public User login1(String userAccount, String password) {
        //密码加密 encrypt
        password = MD5Utils.encrypt(password);
        //调用userMapper自定义的登陆方法
        User user = null;
        user = userMapper.login1(userAccount, password);
        if (user != null) {
            return user;
        }
        return null;
    }

    @Override
    public boolean modifyUserHeadImage(Long userId, String uploadStr) {
        if (userId != null) {
            return userMapper.modifyHeadImage(userId, uploadStr) > 0;
        }

        return false;
    }

    @Override
    public ResultVO showUsersPageTest(Integer pages, Integer limit) {
        PageHelper.startPage(pages, limit);
        List<User> list = userMapper.selectByExample(null);
        Long row = userMapper.countByExample(null);
        return ResultVO.success(row, list);
    }

    @Override
    public boolean checkAccount(String userAccount) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserAccountEqualTo(userAccount);
        List<User> list = userMapper.selectByExample(userExample);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;

    }

    @Override
    public boolean addUser(User user) {
        //密码加密后存储
        user.setPassword(MD5Utils.encrypt(user.getPassword()));
        //设置其他信息
        user.getCreateTime(new Date());
        //直接使用接口方法
        int i = userMapper.insertSelective(user);
        return i > 0;
    }

    @Override
    public ResultVO showUsersPage(Integer page, Integer limit, String no, String mobileNumber, Integer status) {

        //1.分页数据  data
        List<UserRoleVO> data = new ArrayList<>();

        List<UserRoleExpand> list = userMapper.selectUserAndRoleByPages((page - 1) * limit, limit, no, mobileNumber, status);
        for (UserRoleExpand ur : list) {
            UserRoleVO vo = new UserRoleVO();
            vo.setRoleList(ur.getRoleList());
            //赋值两个对象相同的属性
            BeanUtils.copyProperties(ur, vo);

            //  private List<String> rids=new ArrayList<>();
            //    private List<String> rnames=new ArrayList<>();

            //该用户拥有的所有角色信息
            List<Role> roleList = ur.getRoleList();
            for (Role r : roleList) {
                //把当前角色id添加到vo的角色id集合里面
                vo.getRids().add(r.getRoleUkid());
                vo.getRnames().add(r.getRoleName());
            }


            data.add(vo);
        }

        //2.查询分页 总个数 count
        long count = userMapper.selectUserAndRoleCount(no, mobileNumber, status);

        return ResultVO.success(count, data);

    }

    @Override
    public User queryUserById(Long userId) {
        if(userId!=null){
            return userMapper.selectByPrimaryKeyone(userId);
        }
        return null;
    }

    @Override
    public boolean modifyUser(User user) {
        user.setModifyTime(new Date());
        int count=userMapper.updateByPrimaryKeySelectiveone(user);
        return count>0;
    }

    @Override
    public boolean deleteUserById(Long id) {
       if(id==null){
           return false;
       }
       return userMapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public boolean deletebatchUserById(Long[] ids) {
        if (ids!=null&&ids.length>0){
            return userMapper.deleteBatch(ids)>0;
        }
        return false;
    }

    @Override
    public boolean updateActive(Long userId, Boolean status) {
        if(userId!=null&&status!=null){
            return userMapper.updateActive(userId,status?1:0)>0;
        }
        return false;
    }
}
