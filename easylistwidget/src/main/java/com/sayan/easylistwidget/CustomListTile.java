package com.sayan.easylistwidget;

import java.lang.reflect.Method;

public class CustomListTile {
    private Method viewGetterMethod;
    private int viewResID;

    public CustomListTile(Method viewGetterMethod, int viewResID) {
        this.viewGetterMethod = viewGetterMethod;
        this.viewResID = viewResID;
    }

    public Method getViewGetterMethod() {
        return viewGetterMethod;
    }

    public int getViewResID() {
        return viewResID;
    }
}
