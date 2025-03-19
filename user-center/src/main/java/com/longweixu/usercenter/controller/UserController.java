package com.longweixu.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.longweixu.usercenter.model.domain.User;
import com.longweixu.usercenter.model.domain.request.UserLoginRequest;
import com.longweixu.usercenter.model.domain.request.UserRegisterRequest;
import com.longweixu.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.longweixu.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.longweixu.usercenter.constant.UserConstant.USER_LOGIN_STATE;


/**
 * 用户接口
 *
 * @author Longweixu
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    // 注册
    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if(userRegisterRequest == null) {
            return null;
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }

        return userService.userRegister(userAccount, userPassword, checkPassword);

    }

    // 登录
    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if(userLoginRequest == null) {
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        return userService.userLogin(userAccount,userPassword,request);

    }

    //查询用户
    @GetMapping("/search")
    public List<User> searchUsers(String username,HttpServletRequest request) {

        if(!isAdmin(request)){
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);

        }

        List<User> userlist = userService.list(queryWrapper);
        return userlist.stream().map(user -> {
            user.setUserPassword(null);
            return userService.getSafetyUser(user);
        }).collect(Collectors.toList());
    }

    //删除用户
    @PostMapping("/delete")
    public boolean deleteUsers(@RequestBody long id,HttpServletRequest request) {
        if(!isAdmin(request)){
            return false;
        }
        if(id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }

    // 判断是不是管理员身份，只有管理员可以进行查询用户和删除用户
    private boolean isAdmin(HttpServletRequest request) {
        //鉴权，仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

}
