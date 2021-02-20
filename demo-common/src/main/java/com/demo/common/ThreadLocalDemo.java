package com.demo.common;

import java.util.concurrent.CountDownLatch;

public class ThreadLocalDemo {

    public static ThreadLocal<Integer> pool = new ThreadLocal<>();

    public static ThreadLocal<Counter> cpool = new ThreadLocal<>();
    static Counter counter = new Counter(10);

    public static void main(String[] args) {

        pool.set(1);
        pool.set(2);
        pool.set(3);
        System.out.println(pool.get());


        //CountDownLatch latch = new CountDownLatch(10);
        Runnable run = new Runnable() {
            @Override
            public void run() {
                //for(int i=1;i<=5;i++){
                    /*pool.set(i);*/
                    /*System.out.println(Thread.currentThread().getId()+"---"+pool.get());*/
                    /*pool.remove();*/
                    counter.setNum(counter.getNum()+1);
                    cpool.set(counter);
                    System.out.println(Thread.currentThread().getId()+"---"+cpool.get());
                    //cpool.remove();
                    /*try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            //}
        };

        for(int i=0;i<5;i++){
            new Thread(run).start();
        }


    }

    public static class Counter{

        private int num;

        public Counter() {
        }

        public Counter(int num) {
            this.num = num;
        }

        public void incr(){
            num++;
        }

        @Override
        public String toString() {
            return "Counter{" +
                    "num=" + num +
                    '}';
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }




}
