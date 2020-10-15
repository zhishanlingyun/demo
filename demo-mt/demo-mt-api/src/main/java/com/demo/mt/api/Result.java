package com.demo.mt.api;

public class Result {

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
}
