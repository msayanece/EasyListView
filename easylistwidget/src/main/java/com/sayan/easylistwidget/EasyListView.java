package com.sayan.easylistwidget;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class EasyListView {

    private <T> EasyListView(Activity activity, RecyclerView recyclerView, List<T> listItems, Method methodName, OnItemClickListener onClickListener) {
        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        SimpleTextAdapter<T> adapter = new SimpleTextAdapter<T>(listItems, methodName, onClickListener);
        recyclerView.setAdapter(adapter);
    }

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
        private OnItemClickListener onClickListener;
        private RecyclerView recyclerView;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder<T> addRow(@ViewType int viewType, String methodName) throws NoSuchMethodException {
            this.viewType = viewType;
            this.methodName = itemsPOJOClass.getDeclaredMethod(methodName);
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

        public Builder<T> setOnItemClickListener(OnItemClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public Builder<T> addRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        public EasyListView Build(){
            return new EasyListView(activity, recyclerView, listItems, methodName, onClickListener);
        }
    }

    public interface OnItemClickListener{
        void onClick(View view, int position);
    }

}
