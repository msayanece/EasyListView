package com.sayan.easylistwidget.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sayan.easylistwidget.EasyListView;
import com.sayan.easylistwidget.R;
import com.sayan.easylistwidget.Utils;
import com.sayan.easylistwidget.listtiles.ListTile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class SimpleTextAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private final List<T> items;
    private ListTile listTile;
    private final EasyListView.OnItemClickListener onClickListener;
    private EasyListView.OnBindViewHolderCalledListener<T> onBindViewHolderCalledListener;

    public SimpleTextAdapter(Activity activity, List<T> items, ListTile listTile, EasyListView.OnItemClickListener onClickListener, EasyListView.OnBindViewHolderCalledListener<T> onBindViewHolderCalledListener) {
        this.activity = activity;
        this.items = items;
        this.listTile = listTile;
        this.onClickListener = onClickListener;
        this.onBindViewHolderCalledListener = onBindViewHolderCalledListener;
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
                if (onBindViewHolderCalledListener == null) {
                    simpleTextViewHolder.setData(items, listTile, position);
                }else {
                    onBindViewHolderCalledListener.onBasicBindViewHolder(simpleTextViewHolder, items.get(position), position);
                }
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

    public static class SimpleTextViewHolder<T> extends RecyclerView.ViewHolder {
        public final ImageView trailingImageView;
        private Activity activity;
        public final ImageView leadingImageView;
        public final TextView titleTextView;
        public final TextView descriptionTextView;
        private View itemView;
        private EasyListView.OnItemClickListener onClickListener;

        SimpleTextViewHolder(Activity activity, View itemView, EasyListView.OnItemClickListener onClickListener) {
            super(itemView);
            this.activity = activity;
            leadingImageView = itemView.findViewById(R.id.leadingImageView);
            trailingImageView = itemView.findViewById(R.id.trailingImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            this.itemView = itemView;
            this.onClickListener = onClickListener;
        }

        void setData(List<T> items, ListTile listTile, final int position) {
            if (listTile.getIcon() != null) {
                String itemViewData = null;
                try {
                    itemViewData = getItemViewData(items.get(position), listTile.getItemsPOJOClass().getDeclaredMethod(listTile.getIcon().getMethodName()));
                    switch (listTile.getIcon().getImagePosition()) {
                        case EasyListView.IconPosition.LEADING:
                            Utils.loadImageDirectlyWithSize(activity, itemViewData, leadingImageView, 150, 150);
                            trailingImageView.setVisibility(View.GONE);
                            break;
                        case EasyListView.IconPosition.TRAILING:
                            Utils.loadImageDirectlyWithSize(activity, itemViewData, trailingImageView, 150, 150);
                            leadingImageView.setVisibility(View.GONE);
                            break;
                        default:
                            leadingImageView.setVisibility(View.GONE);
                            trailingImageView.setVisibility(View.GONE);
                            break;
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    leadingImageView.setVisibility(View.GONE);
                    trailingImageView.setVisibility(View.GONE);
                }
            } else {
                leadingImageView.setVisibility(View.GONE);
                trailingImageView.setVisibility(View.GONE);
            }
            if (listTile.getTitle() != null) {
                String itemViewData = null;
                try {
                    itemViewData = getItemViewData(items.get(position), listTile.getItemsPOJOClass().getDeclaredMethod(listTile.getTitle().getMethodName()));
                    titleTextView.setText(itemViewData);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    titleTextView.setVisibility(View.GONE);
                }
            } else {
                titleTextView.setVisibility(View.GONE);
            }
            if (listTile.getDescription() != null) {
                String itemViewData = null;
                try {
                    itemViewData = getItemViewData(items.get(position), listTile.getItemsPOJOClass().getDeclaredMethod(listTile.getDescription().getMethodName()));
                    descriptionTextView.setText(itemViewData);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    descriptionTextView.setVisibility(View.GONE);
                }
            } else {
                descriptionTextView.setVisibility(View.GONE);
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
                if (returnType.isInstance(returnValue)) {
                    if (returnValue instanceof String) {
                        return (String) returnValue;
                    } else {
                        return "";
                    }
                } else {
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
