package com.sayan.easylistwidget.listtiles;

import com.sayan.easylistwidget.EasyListView;

public class ListTile<T> {

    private Class<T> itemsPOJOClass;
    private Icon icon;
    private Title title;
    private Description description;

    public ListTile(Class<T> itemsPOJOClass) {
        this.itemsPOJOClass = itemsPOJOClass;
    }

    public Icon getIcon() {
        return icon;
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public Class<T> getItemsPOJOClass() {
        return itemsPOJOClass;
    }

    public ListTile<T> addIcon(@EasyListView.IconPosition int imagePosition, String methodName) throws NoSuchMethodException {
        //check if icon already added
        if (icon != null){
            throw new IllegalStateException("Already an icon is added");
        }
        //check if the method name is found in the POJO
        itemsPOJOClass.getDeclaredMethod(methodName);
        //finally create the icon view
        icon = new Icon(imagePosition, methodName);
        return this;
    }

    public ListTile<T> addTitle(String methodName) throws IllegalStateException, NoSuchMethodException {
        //check if icon already added
        if (title != null){
            throw new IllegalStateException("Already a title is added");
        }
        //check if the method name is found in the POJO
        itemsPOJOClass.getDeclaredMethod(methodName);
        //finally create the icon view
        title = new Title(methodName);
        return this;
    }

    public ListTile<T> addDescription(String methodName) throws NoSuchMethodException {
        //check if icon already added
        if (description != null){
            throw new IllegalStateException("Already a title is added");
        }
        //check if the method name is found in the POJO
        itemsPOJOClass.getDeclaredMethod(methodName);
        //finally create the icon view
        description = new Description(methodName);
        return this;
    }


    public static class Icon{
        private @EasyListView.IconPosition int imagePosition;
        private String methodName;

        private Icon(@EasyListView.IconPosition int imagePosition, String methodName) {
            this.imagePosition = imagePosition;
            this.methodName = methodName;
        }

        public @EasyListView.IconPosition int getImagePosition() {
            return imagePosition;
        }

        public String getMethodName() {
            return methodName;
        }
    }

    public static class Title{
        private String methodName;
        private Title(String methodName) {
            this.methodName = methodName;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }
    }

    public static class Description{
        private String methodName;
        private Description(String methodName) {
            this.methodName = methodName;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }
    }
}
