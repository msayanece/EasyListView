package com.sayan.easylistwidget;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

class CustomRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Activity activity;
    private final List<T> items;
    private final int rowResID;
    private final EasyListView.OnItemClickListener onClickListener;

    public CustomRecyclerAdapter(Activity activity, List<T> items, int rowResID, EasyListView.OnItemClickListener onClickListener) {
        this.activity = activity;
        this.items = items;
        this.rowResID = rowResID;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_child, parent, false);
        return new CustomRecyclerAdapter.CustomRecyclerViewHolder<T>(activity, itemView, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof CustomRecyclerViewHolder) {
            try {
                CustomRecyclerViewHolder<T> simpleTextViewHolder = (CustomRecyclerViewHolder<T>) viewHolder;
//                simpleTextViewHolder.setData(items, listTile, position);
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    private static class CustomRecyclerViewHolder<T> extends RecyclerView.ViewHolder {
        CustomRecyclerViewHolder(Activity activity, View itemView, EasyListView.OnItemClickListener onClickListener) {
            super(itemView);
        }
    }
}
