package com.sayan.easylistwidget.listtiles;

import java.lang.reflect.Method;

/**
 * POJO or Model class for mapping of user given view and user given model-method
 */
public class CustomListTile {
    private Method viewGetterMethod;
    private int viewResID;

    /**
     * Constructor for the model
     * @param viewGetterMethod the getter method which must be called to set the view
     * @param viewResID the view resource ID like R.id.xxx of the layout
     */
    public CustomListTile(Method viewGetterMethod, int viewResID) {
        this.viewGetterMethod = viewGetterMethod;
        this.viewResID = viewResID;
    }

    /**
     * getter of "viewGetterMethod" field
     * @return the mapped method
     */
    public Method getViewGetterMethod() {
        return viewGetterMethod;
    }

    /**
     * getter of "viewResID" field
     * @return the mapped resource ID
     */
    public int getViewResID() {
        return viewResID;
    }
}
