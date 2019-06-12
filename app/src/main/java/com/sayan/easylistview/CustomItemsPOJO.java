package com.sayan.easylistview;

import com.sayan.easylistwidget.annotations.ID;

public class CustomItemsPOJO {
    private String name;
    private String desc;
    private String image;
    private String button;

    public CustomItemsPOJO(String name, String desc, String image, String button) {
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.button = button;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }
}