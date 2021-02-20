package com.demo.common;

import java.util.Arrays;
import java.util.List;

public class FooService implements IService {

    @Override
    public void foo1() {
        System.out.println(getName()+"-foo1");
    }

    @Override
    public String foo2(String str) {
        return getName()+"-foo2-"+str;
    }

    @Override
    public List foo3(String str1, String str2) {

        return Arrays.asList(getName(),"foo3",str1,str2);
    }

    private String getName(){
        return "[FooService]";
    }

    @Override
    public String toString() {
        return "FooService{}";
    }
}
