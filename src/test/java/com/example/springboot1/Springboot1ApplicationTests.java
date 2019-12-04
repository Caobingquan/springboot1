package com.example.springboot1;

import com.example.springboot1.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot1ApplicationTests {
    @Autowired
    RedisUtils redisUtils;
    @Test
    void contextLoads() {
        System.out.println(redisUtils.get("aa"));
    }

}
