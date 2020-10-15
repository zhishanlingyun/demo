package com.demo.mt.api;

public interface HelloService {


    String sayHello();

    User findUser(Long id);

    Result save(User user);

}
