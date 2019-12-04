package com.example.springboot1.controller;

import com.example.springboot1.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Binquan.Cao
 */
public class Test1 {

    @Autowired
    RedisUtils redisUtils;
    public  void main() {

        System.out.println(redisUtils.get("aa"));
    }
}
