package com.qinlei.num.refreshview.model;

import java.util.List;

/**
 * Created by ql on 2017/2/8.
 */

public class ThemeContentBean {

    private String background;
    private int color;
    private String description;
    private String image;
    private String image_source;
    private String name;
    private List<Stories> stories;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Stories> getStories() {
        return stories;
    }

    public void setStories(List<Stories> stories) {
        this.stories = stories;
    }

    @Override
    public String toString() {
        return "ThemeContentBean{" +
                "background='" + background + '\'' +
                ", color=" + color +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", image_source='" + image_source + '\'' +
                ", name='" + name + '\'' +
                ", stories=" + stories +
                '}';
    }
}
