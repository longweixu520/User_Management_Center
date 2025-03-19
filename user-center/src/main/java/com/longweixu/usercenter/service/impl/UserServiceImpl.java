package com.longweixu.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.longweixu.usercenter.model.domain.User;
import com.longweixu.usercenter.service.UserService;
import com.longweixu.usercenter.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import static com.longweixu.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
 *
 * @author Longweixu
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    // 盐值，混淆密码
    private final String SALT = "longweixu";

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 注册
     */
    @Override
    public long userRegister(String userAccount, String userPassword,String checkPassword) {
        //1、校验

        // if(userAccount==null || userPassword==null || checkPassword==null){} 这种判断很麻烦，所以我们直接用Java的一个类库

        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            // 修改为自定义异常
            return -1;
        }
        if(userAccount.length()<4){
            return -1;
        }
        if(userPassword.length()<8||checkPassword.length()<8){
            return -1;
        }
        //账户不能包含特殊字符(只含大小写英文和数字）
        String regex = "^[a-zA-Z0-9]+$";
        if (!userAccount.matches(regex)) {
            return -1;
        }
        //密码和校验密码需要相同
        if(!checkPassword.equals(userPassword)){
            return -1;
        }
        // 账户不能重复(查询数据库放最后，省去一些调用数据库时间）
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if(count>0){
            return -1;
        }

        //2、加密密码

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        // 3、插入数据到数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if(!saveResult){
            return -1;
        }
        return user.getId();
    }

    /**
     * 登录
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        if(userAccount.length()<4){
            return null;
        }
        if(userPassword.length()<8){
            return null;
        }
        //账户不能包含特殊字符(只含大小写英文和数字）
        String regex = "^[a-zA-Z0-9]+$";
        if (!userAccount.matches(regex)) {
            return null;
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);

        // 用户不存在的情况
        if(user==null){
            log.info("user login failed,userAccount cannot match userPassword");
            return null;
        }
        //用户脱敏
        User safetyUSer = getSafetyUser(user);
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUSer);

        return safetyUSer;
    }

    /**
     * 用户脱敏(隐藏敏感信息）
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser) {
        if(originUser==null){
            log.info("user login failed,userAccount cannot match userPassword");
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());


        return safetyUser;

    }


}
