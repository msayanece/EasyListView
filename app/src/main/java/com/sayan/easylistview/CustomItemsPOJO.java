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

    @ID(R.id.titleTextView)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ID(R.id.leadingImageView)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @ID(R.id.descriptionTextView)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @ID(R.id.button)
    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }
}