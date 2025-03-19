package com.longweixu.usercenter.service;

import com.longweixu.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户服务
 * @author Longweixu
*/
public interface UserService extends IService<User> {




    /**
     *注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword,String checkPassword);

    /**
     * 登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 用户脱敏(隐藏敏感信息）
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);
}


