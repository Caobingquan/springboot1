package com.example.springboot1.mapper;

import com.example.springboot1.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author Binquan.Cao
 */
@Repository("userMapper")
public interface UserMapper {

    User selectUser(int uId);

    /**
     * 用户注册
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 根据手机号查询用户信息
     * @param uPhone
     * @return
     */
    User selectCheckUphone(String uPhone);

    /**
     * 更新错误次数
     * @param user
     * @return
     */
    int updateCount(User user);

    /**
     * 更新
     * @param user
     * @return
     */
    int updateUser(User user);


    /**
     * 查询多个数据
     * @return
     */
    @Select("SELECT * FROM user ")
    List<User> findUsers();

}
