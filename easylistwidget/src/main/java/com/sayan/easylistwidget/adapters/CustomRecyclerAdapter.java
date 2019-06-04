package com.sayan.easylistwidget.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sayan.easylistwidget.EasyListView;
import com.sayan.easylistwidget.Utils;
import com.sayan.easylistwidget.listtiles.CustomListTile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class CustomRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Activity activity;
    private final List<T> items;
    private int size;
    private List<CustomListTile> customListTileList;
    private final int rowResID;
    private final EasyListView.OnItemClickListener onClickListener;
    private EasyListView.OnBindViewHolderCalledListener<T> onBindViewHolderCalledListener;

    public CustomRecyclerAdapter(Activity activity, List<T> items, int size, List<CustomListTile> customListTileList, int rowResID, EasyListView.OnItemClickListener onClickListener, EasyListView.OnBindViewHolderCalledListener<T> onBindViewHolderCalledListener) {
        this.activity = activity;
        this.items = items;
        this.size = size;
        this.customListTileList = customListTileList;
        this.rowResID = rowResID;
        this.onClickListener = onClickListener;
        this.onBindViewHolderCalledListener = onBindViewHolderCalledListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(rowResID, parent, false);
        return new CustomRecyclerAdapter.CustomRecyclerViewHolder<T>(activity, itemView, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof CustomRecyclerViewHolder) {
            CustomRecyclerViewHolder<T> customRecyclerViewHolder = (CustomRecyclerViewHolder<T>) viewHolder;
            if (onBindViewHolderCalledListener == null) {
                customRecyclerViewHolder.setData(activity, items, customListTileList, onClickListener, position);
            } else {
                final int finalPosition = position;
                for (CustomListTile customListTile :
                        customListTileList) {
                    View view = customRecyclerViewHolder.itemView.findViewById(customListTile.getViewResID());
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickListener.onClick(v, finalPosition);
                        }
                    });
                }
                customRecyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(v, finalPosition);
                    }
                });
                onBindViewHolderCalledListener.onCustomBindViewHolder(customRecyclerViewHolder, items.get(position), position);
            }

        }
    }

    @Override
    public int getItemCount() {
        if (size == -1) {
            if (items == null) {
                return 0;
            }
            return items.size();
        } else return size;
    }

    public static class CustomRecyclerViewHolder<T> extends RecyclerView.ViewHolder {
        CustomRecyclerViewHolder(Activity activity, View itemView, EasyListView.OnItemClickListener onClickListener) {
            super(itemView);
        }

        void setData(Activity activity, List<T> items, List<CustomListTile> customListTileList, final EasyListView.OnItemClickListener onClickListener, final int position) {
            for (CustomListTile customListTile :
                    customListTileList) {
                View view = itemView.findViewById(customListTile.getViewResID());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(v, position);
                    }
                });
                try {
                    setDataAccordingToViewType(activity, view, customListTile.getViewGetterMethod(), items.get(position));
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
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

        private void setDataAccordingToViewType(Activity activity, View view, Method viewGetterMethod, T item) throws InvocationTargetException, IllegalAccessException {
            if (view instanceof TextView) {
                if (viewGetterMethod.getReturnType().isInstance(""))
                    ((TextView) view).setText((String) viewGetterMethod.invoke(item));
                return;
            }
            if (view instanceof ImageView) {
                if (viewGetterMethod.getReturnType().isInstance("")) {
                    Utils.loadImageDirectlyWithSize(activity, (String) viewGetterMethod.invoke(item), ((ImageView) view), 150, 150);
                } else if (viewGetterMethod.getReturnType().isInstance(1)) {
                    ((ImageView) view).setImageDrawable(activity.getResources().getDrawable((int) viewGetterMethod.invoke(item)));
                } else if (viewGetterMethod.getReturnType().isInstance(Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888))) {
                    ((ImageView) view).setImageBitmap((Bitmap) viewGetterMethod.invoke(item));
                }
                return;
            }
        }
    }
}
