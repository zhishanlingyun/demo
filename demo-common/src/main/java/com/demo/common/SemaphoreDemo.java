package com.demo.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args) {

    }

    private static class Pool<T>{

        private Semaphore semaphore;

        private List<T> datas;

        public Pool() {
            semaphore = new Semaphore(2);
            datas = new ArrayList<>(20);
        }

        public void add(T data) throws InterruptedException{
            semaphore.acquire();
            try {
                datas.add(data);
            } finally {
                semaphore.release();
            }
        }

        public void release(){

        }

    }

}
