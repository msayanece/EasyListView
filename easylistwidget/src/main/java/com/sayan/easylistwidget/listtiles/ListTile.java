package com.sayan.easylistwidget.listtiles;

import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;

import com.sayan.easylistwidget.EasyListView;

/**
 * This class is used for a tile of the list with the user given model (POJO) generic type
 *
 * @param <T> the user given model (POJO) generic type
 */
public class ListTile<T> {

    @NonNull
    private Class<T> itemsPOJOClass;
    private Icon icon;
    private Title title;
    private Description description;

    /**
     * constructor of the class
     *
     * @param itemsPOJOClass the user given model.class
     */
    public ListTile(@NonNull Class<T> itemsPOJOClass) {
        if (itemsPOJOClass == null) {
            throw new IllegalArgumentException("itemsPOJOClass must not be null");
        }
        this.itemsPOJOClass = itemsPOJOClass;
    }

    /**
     * getter method of field icon
     *
     * @return icon
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     * getter method of field title
     *
     * @return title
     */
    public Title getTitle() {
        return title;
    }

    /**
     * getter method of field description
     *
     * @return description
     */
    public Description getDescription() {
        return description;
    }

    /**
     * getter method of itemsPOJOClass
     *
     * @return itemsPOJOClass
     */
    public Class<T> getItemsPOJOClass() {
        return itemsPOJOClass;
    }

    /**
     * add icon to the list tile
     *
     * @param imagePosition image position leading or trailing
     * @param methodName    the mapped method name of the user given model
     * @return the same ListTile<T> object
     * @throws IllegalStateException if already the field has been added
     * @throws NoSuchMethodException if the method name is not found in the user given model
     */
    public ListTile<T> addIcon(@EasyListView.IconPosition int imagePosition, @NonNull String methodName) throws NoSuchMethodException {
        //check if icon already added
        if (icon != null) {
            throw new IllegalStateException("Already an icon is added");
        }
        //check if the method name is found in the POJO
        itemsPOJOClass.getDeclaredMethod(methodName);
        //finally create the icon view
        icon = new Icon(imagePosition, methodName);
        return this;
    }

    /**
     * add the title text to the list tile
     *
     * @param methodName the mapped method name of the user given model
     * @return the same ListTile<T> object
     * @throws IllegalStateException if already the field has been added
     * @throws NoSuchMethodException if the method name is not found in the user given model
     */
    public ListTile<T> addTitle(@NonNull String methodName) throws IllegalStateException, NoSuchMethodException {
        //check if icon already added
        if (title != null) {
            throw new IllegalStateException("Already a title is added");
        }
        //check if the method name is found in the POJO
        itemsPOJOClass.getDeclaredMethod(methodName);
        //finally create the icon view
        title = new Title(methodName);
        return this;
    }

    /**
     * add the description text to the list tile
     *
     * @param methodName the mapped method name of the user given model
     * @return the same ListTile<T> object
     * @throws IllegalStateException if already the field has been added
     * @throws NoSuchMethodException if the method name is not found in the user given model
     */
    public ListTile<T> addDescription(@NonNull String methodName) throws NoSuchMethodException {
        //check if icon already added
        if (description != null) {
            throw new IllegalStateException("Already a title is added");
        }
        //check if the method name is found in the POJO
        itemsPOJOClass.getDeclaredMethod(methodName);
        //finally create the icon view
        description = new Description(methodName);
        return this;
    }

    /**
     * Icon model for holding image view and image mapping properties
     */
    public static class Icon {
        private @EasyListView.IconPosition
        int imagePosition;
        private String methodName;

        private Icon(@EasyListView.IconPosition int imagePosition, String methodName) {
            this.imagePosition = imagePosition;
            this.methodName = methodName;
        }

        /**
         * get image position property like leading or trailing
         *
         * @return the field imagePosition
         */
        public @EasyListView.IconPosition
        int getImagePosition() {
            return imagePosition;
        }

        /**
         * get mapped method name property
         *
         * @return the field methodName
         */
        public String getMethodName() {
            return methodName;
        }
    }

    /**
     * Title model for holding title text view and text mapping properties
     */
    public static class Title {
        private String methodName;

        private Title(String methodName) {
            this.methodName = methodName;
        }

        /**
         * get the mapped method name property
         *
         * @return the field methodName
         */
        public String getMethodName() {
            return methodName;
        }
    }

    /**
     * Description model for holding description text view and text mapping properties
     */
    public static class Description {
        private String methodName;

        private Description(String methodName) {
            this.methodName = methodName;
        }

        /**
         * get the mapped method name property
         *
         * @return the field methodName
         */
        public String getMethodName() {
            return methodName;
        }
    }
}
