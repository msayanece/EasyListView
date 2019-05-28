package com.sayan.easylistview;

import com.sayan.easylistwidget.ID;
import com.sayan.easylistwidget.Layout;

@Layout(R.layout.custom_recycler_child)
public class CustomItemsPOJO {
    @ID(R.id.titleTextView)
    private String name;
    @ID(R.id.descriptionTextView)
    private String desc;
    @ID(R.id.leadingImageView)
    private String image;

    public CustomItemsPOJO(String name, String desc, String image) {
        this.name = name;
        this.desc = desc;
        this.image = image;
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
}