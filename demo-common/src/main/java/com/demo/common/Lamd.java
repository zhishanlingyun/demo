package com.demo.common;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Lamd {

    public static final Predicate<String> NOT_NULL = Objects::nonNull;

    public static final Predicate<String> MAX_LEN_10 = s-> s.length() < 10;

    public static final Predicate<String> MIN_LEN_5 = s -> s.length() >5;


    public static void main(String[] args) {

        boolean match = Stream.of("aaabbbcccffff").allMatch(MAX_LEN_10);
        System.out.println(match);

    }

}
