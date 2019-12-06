package com.example.springboot1.service.impl;


import com.example.springboot1.mapper.UserMapper;
import com.example.springboot1.pojo.User;
import com.example.springboot1.service.UserService;
import com.example.springboot1.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Binquan.Cao
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
	@Autowired
	RedisUtils redisUtils;
    @Override
    public User selectUser(int uId) {
        return userMapper.selectUser(uId);
    }

    @Override
    public User selectUser2(String uPhone) {
        return userMapper.selectCheckUphone(uPhone);
    }

    @Override
    public int add(User user) {
        return userMapper.addUser(user);
    }


    @Override
    public User selectCheckUphone(String uPhone) {
        return userMapper.selectCheckUphone(uPhone);
    }

	@Cacheable(value="a",key = "#user.uPhone")
    @Override
	public String login(User user) {
		User getUser = userMapper.selectCheckUphone(user.getuPhone());
		if (null==getUser){
			return "uNameErr";
		}else {
			if (getUser.getuErrorCount()<3){
				if (user.getuPassword().equals(getUser.getuPassword())){
					getUser.setuErrorCount(0);
					userMapper.updateCount(getUser);
					redisUtils.set("aa",getUser,60*10);
					System.out.println(redisUtils.get("aa"));
					System.out.println("aaaaaa");
					return "ok";
				}else {
					getUser.setuErrorCount(getUser.getuErrorCount()+1);
					getUser.setuErrorDate(user.getuErrorDate());
					userMapper.updateCount(getUser);
					return "uPasswordErr";
				}

			}else {
				Date date = new Date();
				long cha = date.getTime() - getUser.getuErrorDate().getTime();
				double result = cha * 1.0 / (1000 * 60 * 60);
				if(result<24){
					return "DateErr";
				}else{
					getUser.setuErrorCount(0);
					userMapper.updateCount(getUser);
					return "ok";
				}
			}

		}
	}

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }


    @Override
    public boolean checkRegist(User user) {
        int i = userMapper.addUser(user);
        if (i>0){
            return  true;
        }
        return false;
    }

}
