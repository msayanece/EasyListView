package com.sayan.easylistwidget;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class SimpleTextAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Activity activity;
    private final List<T> items;
    private ListTile listTile;
    private final EasyListView.OnItemClickListener onClickListener;

    public SimpleTextAdapter(Activity activity, List<T> items, ListTile listTile, EasyListView.OnItemClickListener onClickListener) {
        this.activity = activity;
        this.items = items;
        this.listTile = listTile;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_child, parent, false);
        return new SimpleTextViewHolder<T>(activity, itemView, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof SimpleTextViewHolder) {
            try {
                SimpleTextViewHolder<T> simpleTextViewHolder = (SimpleTextViewHolder<T>) viewHolder;
                simpleTextViewHolder.setData(items, listTile, position);
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

    private static class SimpleTextViewHolder<T> extends RecyclerView.ViewHolder{
        private Activity activity;
        private final ImageView imageView;
        private final TextView titleTextView;
        private final TextView descriptionTextView;
        private View itemView;
        private EasyListView.OnItemClickListener onClickListener;

        SimpleTextViewHolder(Activity activity, View itemView, EasyListView.OnItemClickListener onClickListener) {
            super(itemView);
            this.activity = activity;
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            this.itemView = itemView;
            this.onClickListener = onClickListener;
        }

        void setData(List<T> items, ListTile listTile, final int position) {
            if(listTile.getIcon() != null){
                String itemViewData = null;
                try {
                    itemViewData = getItemViewData(items.get(position), listTile.getClass().getDeclaredMethod(listTile.getIcon().getMethodName()));
                    Utils.loadImageDirectlyWithSize(activity, itemViewData,imageView, 150, 150);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            if(listTile.getTitle() != null){
                String itemViewData = null;
                try {
                    itemViewData = getItemViewData(items.get(position), listTile.getClass().getDeclaredMethod(listTile.getTitle().getMethodName()));
                    titleTextView.setText(itemViewData);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            if(listTile.getDescription() != null){
                String itemViewData = null;
                try {
                    itemViewData = getItemViewData(items.get(position), listTile.getClass().getDeclaredMethod(listTile.getDescription().getMethodName()));
                    descriptionTextView.setText(itemViewData);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(v, position);
                }
            });
        }

        private String getItemViewData(T model, Method methodName) {
            try {
                Class<?> returnType = methodName.getReturnType();
                Object returnValue = methodName.invoke(model);
                if (returnType.isInstance(returnValue)){
                    if (returnValue instanceof String){
                        return (String)returnValue;
                    }else {
                        return "";
                    }
                }else {
                    return "";
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}
