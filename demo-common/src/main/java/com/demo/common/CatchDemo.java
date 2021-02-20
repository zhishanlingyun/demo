package com.demo.common;

public class CatchDemo {

    public void foo1(){

        double r = 0.0d;

        try {
            r = 5/0;
            System.out.println(r);
        }finally {
            throw new RuntimeException("aaa");
            //return;
        }

    }


    public int foo2(){
        double r = 0.0d;
        int ret = 0;

        try {
            r = 5/1;
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            ret +=10;
            return ret;
        }finally {
            ret =1;
            return ret;
        }

    }

    public static void main(String[] args) {
        CatchDemo demo = new CatchDemo();
        //demo.foo1();
        System.out.println(demo.foo2());

    }

}
