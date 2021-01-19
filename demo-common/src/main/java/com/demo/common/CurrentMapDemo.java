package com.demo.common;

import com.demo.common.pojo.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CurrentMapDemo {


    public void putIf(){

        User user1 = new User();
        user1.setId(1L);
        user1.setName("u1");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("u2");

        Map<String,User> map = new ConcurrentHashMap<>();
        User u = map.putIfAbsent("one", user1);
        System.out.println(u);
        System.out.println(map);
        User uu = map.putIfAbsent("one", user2);
        System.out.println(uu);
        System.out.println(map);

        System.out.println("-----------------------");

        Map<String,User> m2 = new HashMap<>();
        User u2 = m2.putIfAbsent("one", user1);
        System.out.println(u2);
        System.out.println(map);


    }

    public static void main(String[] args) {
        CurrentMapDemo demo = new CurrentMapDemo();
        demo.putIf();
    }


}
