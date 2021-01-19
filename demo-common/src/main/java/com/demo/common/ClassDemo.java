package com.demo.common;

import com.demo.common.pojo.Animal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassDemo {

    private static final Logger log = LoggerFactory.getLogger(ClassDemo.class);

    public void formname(){
        try {
            Class.forName("com.demo.common.pojo.Animal").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cld(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Class clazz = classLoader.loadClass("com.demo.common.pojo.Animal");
            //clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        log.info("hello");
        ClassDemo demo = new ClassDemo();
        //demo.formname();
        demo.cld();
        //Animal animal = new Animal();
    }

}
