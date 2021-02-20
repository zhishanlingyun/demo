package com.demo.common;

import java.lang.reflect.Proxy;

public class DynamicProxyDemo {


    public static void main(String[] args) {


        IService target = new FooService();

        IService proxy = (IService) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                new Class[]{IService.class}, new SimpleInvoker(target));

        proxy.foo1();
        System.out.println(proxy);

    }



}
