package com.ssm.controller;

import com.google.code.kaptcha.Constants;
import com.ssm.pojo.User;
import com.ssm.service.UserService;
import com.ssm.vo.ResultVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/userlogin",method = RequestMethod.POST)
//就算 上面的RequestMapping的参数和方法中不一样 也可以映射到方法参数中
    public String userLogin1(@RequestParam String userAccount,
                            @RequestParam String password,
                            @RequestParam String imgCode,HttpSession session){
//        取出session中的验证码Constants.KAPTCHA_SESSION_KEY
        String imgCode1 =(String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

        if(!imgCode.equals(imgCode1)){

        return "login";
    }
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(userAccount, password);
        try {
            subject.login(token);
            //登陆成功

            //存储session
            subject.getSession().setAttribute("userAccount",userAccount);

        }catch (UnknownAccountException e){
            //没有改用户
            e.printStackTrace();
            return "login";

        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            //密码不正确
            return "login";
        }catch (LockedAccountException e){
            //账号被锁定
            e.printStackTrace();
            return "login";
        }catch (AuthenticationException e) {
            //其他异常
            e.printStackTrace();
            return "login";
        }

        return "admin/index";



    }


    @RequestMapping(value = "/uploadheadimage",method = RequestMethod.POST)
    @ResponseBody
    public Object uploadHeadImage(MultipartFile file, HttpSession session, HttpServletRequest request) throws IOException {
        Map<String,String> res=new HashMap <>();
        //上传图片 把图片传到指定文件夹下
        String realPathDir =request.getServletContext().getRealPath("/static/imgs/head");
        File fileDir=new File(realPathDir);
        if(!fileDir.isDirectory()){
            fileDir.delete();//先删除该不是文件夹类型的文件 在创建这个文件夹
            fileDir.mkdirs();
        }
        if (!file.isEmpty()){
            //构建文件名
            String fileName=UUID.randomUUID().toString().replace("-","")+"_"+file.getOriginalFilename();
            File dest=new File(realPathDir+"/"+fileName);
            //要上传到服务器的路径
            String uploadStr="static/imgs/head/"+fileName;
            //文件复制
            file.transferTo(dest);
            //从session中获取userId
            User user=(User)session.getAttribute("activeUser");
            //修改用户头像信息
            boolean f=userService.modifyUserHeadImage(user.getUserId(),uploadStr);
            if(f){
                user.setHeadimageurl(uploadStr);
                session.setAttribute("activeUser",user);
                res.put("code","0");
                return res;
            }
        }
        res.put("code","1");
        return res;
    }
    /**
     * 分页
     */
    @RequestMapping("table/user")
    public @ResponseBody Object userListTest(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer limit){

        ResultVO vo= userService.showUsersPageTest(page,limit);

        return vo;
    }
    /**
     * 检查用户名是否存在
     * /user/checkaccount
     */
    @RequestMapping("/user/checkaccount")
    @ResponseBody
    public Object checkAccount (@RequestParam String userAccount) {
        boolean f = userService.checkAccount(userAccount);
        //找到了 说明已经存在
        if (f) {
            return ResultVO.error();//500 有用户名
        }
        return ResultVO.success();
    }

    /**
     * 检验不存在之后调用add方法
     */
    @RequiresPermissions("user:add")
    @RequestMapping("/user/add")
    //返回网页  用String
    public String userAdd(User user,Model model){
        String pwd=user.getPassword();
        boolean f=userService.addUser(user);
        user.setPassword(pwd);//这层还没加密 就显示明文密码 给管理员看
        model.addAttribute("user",user);//不管成功与否 都会显示给用户看 失败的话 还能保留你想添加的数据 到时候可以在进行修改
        if (f){
            return "user/userinfo";
        }
        return "user/useradd";
    }

    /**
     *
     * @param page 当前页
     * @param limit 每页数据
     * @param no  用户id/账号
     * @param mobileNumber  手机号
     * @param status 用户状态
     * @return
     */
    @RequiresPermissions("user:view")
    @RequestMapping("/user/list")
    public @ResponseBody Object userListTest(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer limit,
                                             String no,
                                             String mobileNumber,
                                             Integer status){

        ResultVO vo= userService.showUsersPage(page,limit,no,mobileNumber,status);

        return vo;
    }
    /**
     * 编辑用户
     */
    @RequiresPermissions("user:update")
    @RequestMapping("/user/modify")
    public String userModifyView(Long userId,Model model){
        //页面跳转
        User user=userService.queryUserById(userId);
        model.addAttribute("user",user);
        return "user/usermodify";
    }
    /**
     *
     */
    @RequiresPermissions("user:update")
    @RequestMapping("/user/domodify")
    public String userModify (User user,Model model){
        boolean f=userService.modifyUser(user);
        model.addAttribute("user",user);
        if(f){
            return "user/userinfo";
        }
        return "user/usermodify";
    }
    /**
     *
     * 删除账号
     */
    @RequiresPermissions("user:delete")
    @RequestMapping(value = "user/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteUser(@PathVariable Long id){
        boolean f=userService.deleteUserById(id);
        System.out.println(f);
        if(f){

            return ResultVO.success();
        }
        return ResultVO.error();
    }
    /**
     * 批量删除  是post
     */
    @RequiresPermissions("user:deletebatch")
    @RequestMapping(value = "user/deletebatch",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteUserBatch(@RequestParam("ids") Long[] ids){
        boolean f=userService.deletebatchUserById(ids);
        if(f){
            return ResultVO.success();

        }
        return  ResultVO.error();
    }
    @RequestMapping("/user/active")
    @ResponseBody
    public Object updateActive(@RequestParam Long userId,@RequestParam Boolean status){
        boolean f=userService.updateActive(userId,status);
        if(f){
            return ResultVO.success();

        }
        return  ResultVO.error("error");
    }
}
