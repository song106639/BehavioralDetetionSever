package com.song.behavioraldetetionsever.util;

import com.song.behavioraldetetionsever.bean.User;
import com.song.behavioraldetetionsever.vo.R;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificationTool {
    //邮箱验证合法性
    public static boolean isEmail(String email){
        String regex = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    //手机号验证
    public static boolean isPhone(String phone){
        String regex = "1[3|4|5|7|8][0-9]\\d{8}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    //验证
    public static boolean isVerification(String verification){
        String regex = "\\d{6}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(verification);
        return matcher.matches();
    }


    //判断是否登录
    //登录返回真
    public static boolean isLogin(Cookie cookie, HttpServletRequest request){
        String cookieName = cookie.getName();
        Object myUser  = request.getSession().getAttribute(cookieName);
        return myUser == null;
    }

    //判断是否是管理员
    //不是管理员返回真
    public static boolean isAdmin(Cookie cookie,HttpServletRequest request){
        String cookieName = cookie.getName();
        User myUser  = (User)request.getSession().getAttribute(cookieName);
        return myUser.getIdentity() != 1;
    }


}
