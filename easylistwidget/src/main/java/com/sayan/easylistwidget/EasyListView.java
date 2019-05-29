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
import com.sayan.easylistwidget.listtiles.CustomListTile;
import com.sayan.easylistwidget.listtiles.ListTile;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EasyListView {

    /*
     * basic constructor of the Recycler view adapter
     */
    private <T> EasyListView(Activity activity, RecyclerView recyclerView, List<T> listItems, ListTile listTile, OnItemClickListener onClickListener) {
        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        SimpleTextAdapter<T> adapter = new SimpleTextAdapter<T>(activity, listItems, listTile, onClickListener);
        recyclerView.setAdapter(adapter);
    }

    /*
     * custom constructor of the Recycler view adapter
     */
    private  <T> EasyListView(Activity activity, RecyclerView recyclerView, List<T> listItems, List<CustomListTile> customListTileList, int rowResID, OnItemClickListener onClickListener) {
        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        CustomRecyclerAdapter<T> adapter = new CustomRecyclerAdapter<T>(activity, listItems, customListTileList, rowResID, onClickListener);
        recyclerView.setAdapter(adapter);
    }

//    @IntDef({ViewType.TEXT, ViewType.IMAGE})
//    @Retention(RetentionPolicy.SOURCE)
//    public @interface ViewType {
//        int TEXT = 1;
//        int IMAGE = 2;
//    }

    /**
     * Indicate image icon position as LEADING or TRAILING
     */
    @IntDef({IconPosition.LEADING, IconPosition.TRAILING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IconPosition {
        int LEADING = 1;
        int TRAILING = 2;
    }

    /**
     * EasyListView builder class, use this class to build the EasyListView object.
     * <br></br>
     * Sample code => <pre>new EasyListView.Builder<ItemsPOJO>(this)
     *                     .addRecyclerView(recyclerView)
     *                     .addListItems(listItems)
     *                     .addItemModel(ItemsPOJO.class)
     *                     .addRow(listTile)
     *                     .setOnItemClickListener(this)
     *                     .Build();
     *                     </pre>
     * @param <T> The user given POJO or Model Type
     */
    public static class Builder<T> {
        private Activity activity;
        private Method methodName;
        private List<T> listItems;
        private Class<T> itemsPOJOClass;
        private OnItemClickListener onClickListener;
        private RecyclerView recyclerView;
        private ListTile listTile;
        private List<CustomListTile> customListTileList;
        private int rowResID;

        /**
         * Builder Constructor with activity param
         * @param activity the activity which is the container of the recycler view
         */
        public Builder(Activity activity) {
            this.activity = activity;
        }

        /**
         * Add a row template with ListTile for basic and easy use
         * @see ListTile
         * @param listTile ListTile with user given POJO or Model type
         * @return the Builder class object itself
         * @throws IllegalStateException when one row template is already set
         */
        public Builder<T> addRow(ListTile<T> listTile) throws IllegalStateException{
            if (rowResID != 0) throw new IllegalStateException("Row Already set!");
            this.listTile = listTile;
            return this;
        }

        /**
         * Add a row template with custom layout resource ID.
         * <br></br> Use this method only when you want to use custom child view.
         * <br></br>  Otherwise Use the {@link #addRow(ListTile)} } method.
         *
         * @param childResId  the layout resource ID of the recycler view custom child
         * @return the Builder class object itself
         * @throws IllegalStateException when one row template is already set
         */
        public Builder<T> addRow(int childResId) throws IllegalStateException {
            if (listTile != null) throw new IllegalStateException("Row Already set!");
            rowResID = childResId;
            return this;
        }

        /**
         * add the list of items to be set when the recycler view is ready
         * @param listItems the list of items
         * @return the Builder class object itself
         */
        public Builder<T> addListItems(List<T> listItems) {
            this.listItems = listItems;
            return this;
        }

        /**
         * add the user given Model.class which is being used to generate the list of items
         * @see Class
         * @param itemsPOJOClass the user given Model.class
         * @return the Builder class object itself
         */
        public Builder<T> addItemModel(Class<T> itemsPOJOClass) {
            this.itemsPOJOClass = itemsPOJOClass;
            customListTileList = generateCustomListTileOfLayout(itemsPOJOClass);
            return this;
        }

        /**
         * used to generate a list of custom list tiles by using reflection and annotations
         * @param itemsPOJOClass the user given Model.class
         * @return the list of CustomListTile
         */
        private List<CustomListTile> generateCustomListTileOfLayout(Class<T> itemsPOJOClass) {
            List<CustomListTile> customListTileList = new ArrayList<>();
//            if (itemsPOJOClass.isAnnotationPresent(Layout.class)) {
//                int layoutResID = Objects.requireNonNull(itemsPOJOClass.getAnnotation(Layout.class)).value();
//                Log.d("layout resource: ", "layout => " + layoutResID);
//            }
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

        /**
         * set the listener on the child views in the recycler
         * @param onClickListener the listener object
         * @see OnItemClickListener
         * @return the Builder class object itself
         */
        public Builder<T> setOnItemClickListener(OnItemClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        /**
         * Add the recycler view object from the activity layout
         * @param recyclerView the recycler view - container
         * @return the Builder class object itself
         */
        public Builder<T> addRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        /**
         * Builds the object of EasyListView and creates the adapter and shows according to the setup
         * @return the object of EasyListView
         */
        public EasyListView Build() {
            if (rowResID == 0) {
                //show basic recycler view
                return new EasyListView(activity, recyclerView, listItems, listTile, onClickListener);
            } else {
                //show custom recycler view
                return new EasyListView(activity, recyclerView, listItems, customListTileList, rowResID, onClickListener);
            }
        }
    }

    /**
     * The click listener on the recycler child views
     */
    public interface OnItemClickListener {
        /**
         * implement this method to listener to a click on the child views
         * @param view the view which was clicked
         * @param position the position of the onBindViewHolder() method of the recycler adapter
         */
        void onClick(View view, int position);
    }

}
