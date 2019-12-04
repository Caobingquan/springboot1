package com.example.springboot1.service;


import com.example.springboot1.pojo.User;

/**
 * @author Binquan.Cao
 */
public interface UserService {
    /**
     * 根据编号查询用户信息
     */
    User selectUser(int uId);

    /**
     * 根据手机查询用户信息
     */
    User selectUser2(String uPhone);

    /**
     用户注册
     */
    int add(User user);

    /**
     * Ajax的前台验证用户名称是否一致
     */
    public User selectCheckUphone(String uPhone);

    /**
     用户登录
     */
    String login(User user);

    /**
    更新
     */
    int updateUser(User user);


    /**
    检查注册
     */
    boolean checkRegist(User user);


}
