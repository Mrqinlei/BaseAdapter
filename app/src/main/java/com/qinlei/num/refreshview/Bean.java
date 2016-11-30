package com.qinlei.num.refreshview;

/**
 * Created by ql on 2016/11/30.
 */

public class Bean {
    String title;
    String content;
    int resId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public Bean(String title, String content, int resId) {

        this.title = title;
        this.content = content;
        this.resId = resId;
    }
}
