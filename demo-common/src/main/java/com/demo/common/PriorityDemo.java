package com.demo.common;

import java.util.PriorityQueue;

public class PriorityDemo {


    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        queue.add(2);
        queue.add(5);
        queue.add(12);
        queue.add(7);
        queue.add(17);
        queue.add(1);
        queue.add(25);
        queue.add(19);
        queue.add(36);
        queue.add(22);
        queue.add(28);
        queue.add(99);
        System.out.println(queue.poll());
        queue.add(7);
        System.out.println(queue.peek());
    }

}
