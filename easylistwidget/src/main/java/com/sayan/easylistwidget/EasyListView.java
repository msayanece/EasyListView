package com.sayan.easylistwidget;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class EasyListView {

    private <T> EasyListView(Activity activity, RecyclerView recyclerView, List<T> listItems, ListTile listTile, OnItemClickListener onClickListener) {
        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        SimpleTextAdapter<T> adapter = new SimpleTextAdapter<T>(activity, listItems, listTile, onClickListener);
        recyclerView.setAdapter(adapter);
    }

    public <T> EasyListView(Activity activity, RecyclerView recyclerView, List<T> listItems, int rowResID, OnItemClickListener onClickListener) {
        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        CustomRecyclerAdapter<T> adapter = new CustomRecyclerAdapter<T>(activity, listItems, rowResID, onClickListener);
        recyclerView.setAdapter(adapter);
    }

    @IntDef({ViewType.TEXT, ViewType.IMAGE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
        int TEXT = 1;
        int IMAGE = 2;
    }

    @IntDef({IconPosition.LEADING, IconPosition.TRAILING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IconPosition {
        int LEADING = 1;
        int TRAILING = 2;
    }

    public static class Builder<T> {
        private Activity activity;
        private @ViewType
        int viewType;
        private Method methodName;
        private List<T> listItems;
        private Class<T> itemsPOJOClass;
        private OnItemClickListener onClickListener;
        private RecyclerView recyclerView;
        private ListTile listTile;
        private int rowResID;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder<T> addRow(ListTile<T> listTile) {
            if (rowResID != 0) throw new IllegalStateException("Row Already set!");
            this.listTile = listTile;
            return this;
        }

        public Builder<T> addRow(int childResId) {
            if (listTile != null) throw new IllegalStateException("Row Already set!");
            rowResID = childResId;
            return this;
        }

        public Builder<T> addListItems(List<T> listItems) {
            this.listItems = listItems;
            return this;
        }

        public Builder<T> addItemModel(Class<T> itemsPOJOClass) {
            this.itemsPOJOClass = itemsPOJOClass;
            checkAnnotations(itemsPOJOClass);
            return this;
        }

        private void checkAnnotations(Class<T> itemsPOJOClass) {
            if (itemsPOJOClass.isAnnotationPresent(Layout.class)) {
                int layoutResID = itemsPOJOClass.getAnnotation(Layout.class).value();
                Log.d("checkAnnotations: ", "layout => " + layoutResID);
            }
            for (Field field : itemsPOJOClass.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ID.class)) {
                    int viewResID = field.getAnnotation(ID.class).value();
                    String methodName = findGetterMethodName(field);
                    Log.d("checkAnnotations: ", methodName + " => " + viewResID);
                }
            }
        }

        private String findGetterMethodName(Field field) {
            StringBuilder methodNameBuilder = new StringBuilder();
            methodNameBuilder.append("get");
            String fieldName = field.getName();
            String upperCaseFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            methodNameBuilder.append(upperCaseFieldName);
            return methodNameBuilder.toString();
        }

        public Builder<T> setOnItemClickListener(OnItemClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public Builder<T> addRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        public EasyListView Build() {
            if (rowResID == 0) {
                return new EasyListView(activity, recyclerView, listItems, listTile, onClickListener);
            } else {
                return new EasyListView(activity, recyclerView, listItems, rowResID, onClickListener);
            }
        }


    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

}
