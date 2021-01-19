package com.demo.mt.server.impl;

import com.demo.mt.api.HelloService;
import com.demo.mt.api.Result;
import com.demo.mt.api.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HelloServiceImpl implements HelloService {

    private Map<Long,User> users = new HashMap<>();

    @Override
    public String sayHello() {
        return "hello,我是服务端";
    }

    @Override
    public User findUser(Long id) {
        return users.get(id);
    }

    @Override
    public Result save(User user) {
        Result result = new Result();
        if(users.containsKey(user.getId())){
            result.setCode(10001L);
            result.setMsg("保存失败,用户已存在");
        }else {
            users.put(user.getId(),user);
            result.setCode(1000L);
        }
        return result;
    }
}
