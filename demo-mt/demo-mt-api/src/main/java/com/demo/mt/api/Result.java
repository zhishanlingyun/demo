package com.demo.mt.api;

import java.io.Serializable;

public class Result implements Serializable {

    private Long code;

    private String msg;

    public boolean success(){
        return Long.valueOf("1000").equals(code);
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
