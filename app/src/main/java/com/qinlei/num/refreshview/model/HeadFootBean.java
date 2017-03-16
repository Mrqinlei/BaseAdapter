package com.qinlei.num.refreshview.model;

/**
 * Created by ql on 2017/3/14.
 */

public class HeadFootBean {
    private String name;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public HeadFootBean(String name, int type) {

        this.name = name;
        this.type = type;
    }
}
