package com.demo.mt.client;

import com.demo.mt.api.HelloService;
import com.demo.mt.api.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Client {

    public static void main(String[] args) {


        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:rpc-client.xml");
        System.out.println("client start...");
        HelloService service = (HelloService) context.getBean("helloReferer");
        for(int i=0;i<10;i++){
            System.out.println(service.findUser(Long.valueOf(i)));
            User user = new User();
            user.setId(Long.valueOf(i));
            user.setName("jack"+i);
            System.out.println(service.save(user));
            System.out.println(service.findUser(Long.valueOf(i)));
            System.out.println("result is "+service.sayHello());
            try {
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}
