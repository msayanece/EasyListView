package com.sayan.easylistwidget;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
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

public class EasyListView {

    /*
     * basic constructor of the Recycler view adapter
     */
    private <T> EasyListView(Activity activity, RecyclerView recyclerView, List<T> listItems, int size, ListTile listTile, OnItemClickListener onClickListener, OnBindViewHolderCalledListener<T> onBindViewHolderCalledListener) {
        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        SimpleTextAdapter<T> adapter = new SimpleTextAdapter<T>(activity, listItems, size, listTile, onClickListener, onBindViewHolderCalledListener);
        recyclerView.setAdapter(adapter);
    }

    /*
     * custom constructor of the Recycler view adapter
     */
    private  <T> EasyListView(Activity activity, RecyclerView recyclerView, List<T> listItems, int size, List<CustomListTile> customListTileList, int rowResID, OnItemClickListener onClickListener, OnBindViewHolderCalledListener<T> onBindViewHolderCalledListener) {
        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        CustomRecyclerAdapter<T> adapter = new CustomRecyclerAdapter<T>(activity, listItems, size, customListTileList, rowResID, onClickListener, onBindViewHolderCalledListener);
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
        private OnBindViewHolderCalledListener<T> onBindViewHolderCalledListener;
        private int size = -1;

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
         * This is used to set the onBindViewHolder() call back listener.
         * <pre>bind to this listener to get a callback to implement your own business logic.</pre>
         * <br></br> The appropriate listener method will be called when the onBindViewHolder()
         * gets called in the adapter
         * <br></br> <pre>When {@link #addRow(int)} is set, custom adapter onBindViewHolder() is being
         * returned through this listener method
         * {@link OnBindViewHolderCalledListener#onCustomBindViewHolder(CustomRecyclerAdapter.CustomRecyclerViewHolder, Object, int)}
         * Other method is not being called </pre>
         * <br></br> <pre>When {@link #addRow(ListTile)} is set, basic/simple adapter onBindViewHolder()
         * is being returned through this listener method
         * {@link OnBindViewHolderCalledListener#onBasicBindViewHolder(SimpleTextAdapter.SimpleTextViewHolder, Object, int)}
         * Other method is not being called </pre>
         * @param onBindViewHolderCalledListener the listener object
         * @see OnBindViewHolderCalledListener
         * @return the Builder class object itself
         */
        public Builder<T> setOnBindViewHolderCalledListener(OnBindViewHolderCalledListener<T> onBindViewHolderCalledListener) {
            this.onBindViewHolderCalledListener = onBindViewHolderCalledListener;
            return this;
        }

        /**
         * set the item child count of the recycler to be displayed
         * @param count the count
         * @return the Builder class object itself
         */
        public Builder<T> setCount(int count) {
            this.size = count;
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
                return new EasyListView(activity, recyclerView, listItems, size, listTile, onClickListener, onBindViewHolderCalledListener);
            } else {
                //show custom recycler view
                return new EasyListView(activity, recyclerView, listItems, size, customListTileList, rowResID, onClickListener, onBindViewHolderCalledListener);
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

    /**
     * When The onBindViewHolder() is being called on a particular row of the recycler,
     * bind to this listener to get a callback to implement your own business logic.
     * @param <T> The user given POJO or Model type
     */
    public interface OnBindViewHolderCalledListener<T>{

        /**
         * Use this callback method for your own implementation of the business logic
         * <pre> Called only when the EasyListView is set for the <b font color="YELLOW">BASIC</b> implementation using
         * {@link EasyListView.Builder#addRow(ListTile)} method</pre>
         * @param viewHolder The SimpleTextViewHolder object with all its fields in the accessible state
         * @param itemOnThatPosition The item should be used for the position generated by listItem.get(position)
         * @param position The row position
         */
        void onBasicBindViewHolder(@NonNull SimpleTextAdapter.SimpleTextViewHolder<T> viewHolder, T itemOnThatPosition, int position);

        /**
         * Use this callback method for your own implementation of the business logic
         * <pre> Called only when the EasyListView is set for the <b font color="YELLOW">CUSTOM</b> implementation using
         * {@link EasyListView.Builder#addRow(int)} method </pre>
         * @param viewHolder The SimpleTextViewHolder object with all its fields in the accessible state
         * @param itemOnThatPosition The item should be used for the position generated by listItem.get(position)
         * @param position The row position
         */
        void onCustomBindViewHolder(@NonNull CustomRecyclerAdapter.CustomRecyclerViewHolder<T> viewHolder, T itemOnThatPosition, int position);
    }
}
