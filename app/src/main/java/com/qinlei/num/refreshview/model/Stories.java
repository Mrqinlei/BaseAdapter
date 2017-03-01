package com.qinlei.num.refreshview.model;

import java.util.List;

/**
 * Created by ql on 2017/2/7.
 */

public class Stories {

    /**
     * images : ["http://pic1.zhimg.com/e3f596c7ed9e470733f0637adb6124e4.jpg"]
     * type : 0
     * id : 7468668
     * title : 不许无聊在读读日报里等你哟
     */

    private int type;
    private int id;
    private String title;
    private List<String> images;

    public Stories(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public Stories(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
