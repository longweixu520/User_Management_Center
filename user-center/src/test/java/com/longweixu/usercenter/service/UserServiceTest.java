package com.longweixu.usercenter.service;


import com.longweixu.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户服务测试
 *
 *
 * @author Longweixu
 *
 */

@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("longweixu");
        user.setUserAccount("1001");
        user.setAvatarUrl("https://plugins.jetbrains.com/static/versions/33571/jetbrains-simple.svg");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setPhone("17786217863");
        user.setEmail("1023145715@qq.com");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        String username = "zhangjunjie";
        String password = "12345678";
        String checkPassword = "12345678";
        userService.userRegister(username, password, checkPassword);
    }

}