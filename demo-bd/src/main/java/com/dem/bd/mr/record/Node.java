package com.dem.bd.mr.record;
@Deprecated
public class Node {

    private String code;

    private String desc;

    private int count;

    public Node(String code, String desc, int count) {
        this.code = code;
        this.desc = desc;
        this.count = count;
    }

    public Node() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
