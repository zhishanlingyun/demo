package com.demo.common;

import com.demo.common.pojo.Address;
import com.demo.common.pojo.User;
import com.sun.org.apache.xml.internal.res.XMLErrorResources_tr;


/**
 * @see <a href="http://blog.itpub.net/31561269/viewspace-2222522/"/>
 */
public class ClassLoaderDemo {

    public void load(){
        Integer a = new Integer(10);
        System.out.println(a.getClass().getClassLoader());
        User user = new User();
        user.setId(1L);
        System.out.println(user.getClass().getClassLoader());

        System.out.println(this.getClass().getClassLoader());
    }

    public void loadThread(){
        Thread t1 = new Thread(()->{
            User u1 = new User();
            u1.setId(2L);
            System.out.println(Thread.currentThread().getName()+"-user classloader is "+u1.getClass().getClassLoader());
            System.out.println("Thread.currentThread().getContextClassLoader() is "+Thread.currentThread().getContextClassLoader());
            System.out.println("thread classloader is "+this.getClass().getClassLoader());
            Integer a1 = new Integer(20);
            System.out.println("thread a1 classloader is "+a1.getClass().getClassLoader());
        });
        t1.setName("t1");
        t1.start();
    }

    public void loadSys(){
        System.out.println("sys classloader is "+ClassLoader.getSystemClassLoader());
    }

    public void loadNoInit(){
        System.out.println("address classloader is "+ Address.class.getClassLoader());
    }

    public static void main(String[] args) {

        System.out.println(ClassLoaderDemo.class.getClassLoader());
        ClassLoaderDemo demo = new ClassLoaderDemo();
        demo.load();
        demo.loadThread();
        demo.loadSys();
        demo.loadNoInit();

    }

}
