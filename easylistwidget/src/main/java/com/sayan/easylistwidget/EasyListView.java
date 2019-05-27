package com.sayan.easylistwidget;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class EasyListView {

    @IntDef({ViewType.TEXT, ViewType.IMAGE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType{
        int TEXT = 1;
        int IMAGE = 2;
    }

    public static class Builder<T>{
        private Activity activity;
        private @ViewType int viewType;
        private Method methodName;
        private List<T> listItems;
        private Class<T> itemsPOJOClass;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder<T> addRow(@ViewType int viewType, Method methodName){
            this.viewType = viewType;
            this.methodName = methodName;
            return this;
        }

        public Builder<T> addListItems(List<T> listItems) {
            this.listItems = listItems;
            return this;
        }

        public Builder<T> addItemModel(Class<T> itemsPOJOClass) {
            this.itemsPOJOClass = itemsPOJOClass;
            return this;
        }

        public void Build(){
            try {
                Class<?> returnType = methodName.getReturnType();
                Object returnValue = methodName.invoke(listItems.get(0));
                if (returnType.isInstance(returnValue)){
                    if (returnValue instanceof String){
                        Toast.makeText(activity, "row value: " + returnValue, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
