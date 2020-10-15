package com.demo.mt.client;

import com.demo.mt.api.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {

    public static void main(String[] args) {


        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:rpc-client.xml");
        System.out.println("client start...");
        HelloService service = (HelloService) context.getBean("helloReferer");
        System.out.println("result is "+service.sayHello());

    }

}
