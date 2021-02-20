package com.dem.bd.mr.tool;

import com.google.gson.Gson;

public class Json {

    private static Gson gson = new Gson();

    public static String toJson(Object obj){
        return gson.toJson(obj);
    }

}
