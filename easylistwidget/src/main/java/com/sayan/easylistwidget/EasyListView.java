package com.sayan.easylistwidget;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.sayan.easylistwidget.adapters.CustomRecyclerAdapter;
import com.sayan.easylistwidget.adapters.SimpleTextAdapter;
import com.sayan.easylistwidget.annotations.ID;
import com.sayan.easylistwidget.annotations.Layout;
import com.sayan.easylistwidget.listtiles.CustomListTile;
import com.sayan.easylistwidget.listtiles.ListTile;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EasyListView {

    private <T> EasyListView(Activity activity, RecyclerView recyclerView, List<T> listItems, ListTile listTile, OnItemClickListener onClickListener) {
        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        SimpleTextAdapter<T> adapter = new SimpleTextAdapter<T>(activity, listItems, listTile, onClickListener);
        recyclerView.setAdapter(adapter);
    }

    public <T> EasyListView(Activity activity, RecyclerView recyclerView, List<T> listItems, List<CustomListTile> customListTileList, int rowResID, OnItemClickListener onClickListener) {
        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        CustomRecyclerAdapter<T> adapter = new CustomRecyclerAdapter<T>(activity, listItems, customListTileList, rowResID, onClickListener);
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
        private List<CustomListTile> customListTileList;
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
            customListTileList = generateCustomListTileOfLayout(itemsPOJOClass);
            return this;
        }
        private List<CustomListTile> generateCustomListTileOfLayout(Class<T> itemsPOJOClass) {
            List<CustomListTile> customListTileList = new ArrayList<>();
            if (itemsPOJOClass.isAnnotationPresent(Layout.class)) {
                int layoutResID = Objects.requireNonNull(itemsPOJOClass.getAnnotation(Layout.class)).value();
                Log.d("layout resource: ", "layout => " + layoutResID);
            }
            for (Method method : itemsPOJOClass.getDeclaredMethods()) {
                method.setAccessible(true);
                if (method.isAnnotationPresent(ID.class)) {
                    int viewResID = method.getAnnotation(ID.class).value();
                    CustomListTile customListTile = new CustomListTile(method, viewResID);
                    customListTileList.add(customListTile);
                    Log.d("layout resource: ", method.getName() + " => " + viewResID);
                }
            }
            return customListTileList;
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
                return new EasyListView(activity, recyclerView, listItems, customListTileList, rowResID, onClickListener);
            }
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

}
