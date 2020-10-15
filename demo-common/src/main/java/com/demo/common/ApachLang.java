package com.demo.common;

import com.demo.common.pojo.Address;
import com.demo.common.pojo.User;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.*;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * apache-lang3 使用用例
 */
public class ApachLang {

    public static void initCollects(){
        Integer[] a = ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
        System.out.println(a);
        System.out.println(a.length);
        System.out.println(null==a);
        Integer[] b = new Integer[]{1,2,5,6,8,9};

        ArrayUtils.nullToEmpty(b);
        System.out.println(ArrayUtils.toStringArray(b));
        System.out.println(ArrayUtils.toString(b));

    }

    public static void clone0(){

        int[] a = new int[]{1,2,3,4,5,6};
        int[] aa = ArrayUtils.clone(a);
        System.out.println(aa==a);
        System.out.println(ArrayUtils.toString(aa));

    }

    public static void buildToString(){

        Address address = new Address();
        address.setCity("北京市");
        address.setDetail("海淀区西北旺");

        String[] instrests = new String[]{"看书","打篮球"};

        User user = new User();
        user.setId(123L);
        user.setAge(20);
        user.setInterests(instrests);
        user.setName("张三");
        user.setAddress(address);

        System.out.println(ToStringBuilder.reflectionToString(user));
        System.out.println(ToStringBuilder.reflectionToString(user, ToStringStyle.JSON_STYLE));
        System.out.println(ToStringBuilder.reflectionToString(user,ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(ToStringBuilder.reflectionToString(user,ToStringStyle.SIMPLE_STYLE));
        System.out.println(ToStringBuilder.reflectionToString(user,ToStringStyle.NO_CLASS_NAME_STYLE));
        System.out.println(ToStringBuilder.reflectionToString(user,ToStringStyle.NO_FIELD_NAMES_STYLE));
        System.out.println(ToStringBuilder.reflectionToString(user,new RecursiveToStringStyle()));
        System.out.println(ToStringBuilder.reflectionToString(user,new MultilineRecursiveToStringStyle()));
        System.out.println("---------------------------------------------------");
        System.out.println(ReflectionToStringBuilder.toString(user));
        System.out.println(ReflectionToStringBuilder.toString(user,new RecursiveToStringStyle()));

    }

    public void number(){


    }


    public static void main(String[] args) {
        //initCollects();
        //clone0();
        buildToString();
    }
}
