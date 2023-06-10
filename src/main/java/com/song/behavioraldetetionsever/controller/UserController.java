package com.song.behavioraldetetionsever.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.song.behavioraldetetionsever.bean.User;
import com.song.behavioraldetetionsever.service.Impl.UserServiceImpl;
import com.song.behavioraldetetionsever.util.VerificationTool;
import com.song.behavioraldetetionsever.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/user")
@Api(value = "用户增删改查接口")
public class UserController {
    @Resource
    UserServiceImpl userService;

    //根据用户邮箱和密码登录
    @PostMapping("/login")
    @ApiOperation(value = "用户登录接口根据用户邮箱和用户密码登录，传递的是json数据")
    public R<String> login(@RequestBody User user, HttpServletRequest request){
        boolean isEmail = VerificationTool.isEmail(user.getEmail());
        if(!isEmail) return new R<String>(200,isEmail,"用户邮箱不合法");
        LambdaQueryWrapper<User> lqw  = new LambdaQueryWrapper<>();
        lqw.eq(User::getEmail,user.getEmail()).eq(User::getPwd,user.getPwd());
        User myUser = userService.getOne(lqw);
        Cookie[] cookies = request.getCookies();
        Cookie cookie = cookies[0];
        String cookieName = cookie.getName();
        //用cookieName验证游湖是否存在
        if(myUser!=null){
            request.getSession().setAttribute(cookieName,myUser);
            return new R<String>(200,true,"登录成功");
        }
        return  new R<String>(200,false,"账户或者密码不正确");
    }

    @GetMapping("/deLogin")
    @ApiOperation(value = "用户退出登录接口")
    public R<String> deLogin(HttpServletRequest request){
        Cookie cookie = request.getCookies()[0];
        String cookieName = cookie.getName();
        request.getSession().setAttribute(cookieName,null);
        return new R<>(200,false,"退出成功");
    }


//    // 浏览器获取cookies
//    @RequestMapping("/getCookies")
//    public void getCookies(HttpServletRequest request){
//
//        Cookie[] cookies = request.getCookies();
//        System.out.println(cookies.length);
//        for (Cookie cookie : cookies) {
//            System.out.println(cookie.getName());
//            System.out.println(cookie.getValue());
//            System.out.println();
//        }
//    }

    //增删改查
    @ApiOperation("添加用户接口")
    @PostMapping("/addUser")
    public R<String> addUser(@RequestBody User user,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Cookie cookie = cookies[0];
        if(VerificationTool.isLogin(cookie,request)){
            return new R<String>(200,false,"请先登录");
        }
        if(VerificationTool.isAdmin(cookie,request)){
            return new R<String>(200,false,"您不是管理员");
        }
        boolean isEmail = VerificationTool.isEmail(user.getEmail());
        if(!isEmail) return new R<String>(200,false,"用户邮箱不合法");
        user.setUsrId(0);
        boolean isSave = userService.save(user);
        String reString = "邮箱重复";
        if(isSave){
            reString = "添加成功";
        }
        return new R<>(200,isSave,reString);
    }

    @DeleteMapping("/delUser/{id}")
    @ApiOperation("根据用户id删除用户接口")
    public R<String> delUser(@PathVariable int id,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Cookie cookie = cookies[0];
        if(VerificationTool.isLogin(cookie,request)){
            return new R<String>(200,false,"请先登录");
        }
        if(VerificationTool.isAdmin(cookie,request)){
            return new R<String>(200,false,"您不是管理员");
        }
        boolean is_del = userService.removeById(id);
        String reString = "删除失败";
        if(is_del){
            reString = "删除成功";
        }
        return new R<>(200,is_del,reString);
    }

    @PutMapping("/updateUser")
    @ApiOperation("更新用户接口")
    public R<String> updateUser(@RequestBody User user,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Cookie cookie = cookies[0];
        if(VerificationTool.isLogin(cookie,request)){
            return new R<String>(200,false,"请先登录");
        }
        if(VerificationTool.isAdmin(cookie,request)){
            return new R<String>(200,false,"您不是管理员");
        }
        boolean is_update = userService.updateById(user);
        String reString = "更新失败";
        if(is_update){
            reString = "更新成功";
        }
        return new R<>(200,is_update,reString);
    }

    @GetMapping("/getUser/{name}")
    @ApiOperation("根据用户名称查询用户接口")
    public R<Object> getUser(@PathVariable String name,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Cookie cookie = cookies[0];
        if(VerificationTool.isLogin(cookie,request)){
            return new R<Object>(200,false,"请先登录");
        }
        if(VerificationTool.isAdmin(cookie,request)){
            return new R<Object>(200,false,"您不是管理员");
        }
        LambdaQueryWrapper<User> lqw  = new LambdaQueryWrapper<>();
        lqw.eq(User::getName,name);
        User user = userService.getOne(lqw);
        return new R<Object>(200,true,user);
    }

    @GetMapping("/getUsers")
    @ApiOperation("查询所有用户接口")
    public R<List<User>> getUsers(){
        List<User> list = userService.list();
        return new R<List<User>>(200,true,list);
    }
}
