package com.demo.common.pojo;

public class Animal {

    private String name;

    static {
        System.out.println("Animal static...");
    }

    public Animal(String name) {
        this.name = name;
    }

    public Animal() {
    }

    public void skill(){
        System.out.println("吃 喝");
    }
}
