package com.sayan.easylistwidget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class SimpleTextAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final List<T> items;
    private Method methodName;
    private final EasyListView.OnItemClickListener onClickListener;

    public SimpleTextAdapter(List<T> items, Method methodName, EasyListView.OnItemClickListener onClickListener) {
        this.items = items;
        this.methodName = methodName;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_text_child, parent, false);
        return new SimpleTextViewHolder<T>(itemView, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof SimpleTextViewHolder) {
            try {
                SimpleTextViewHolder<T> simpleTextViewHolder = (SimpleTextViewHolder<T>) viewHolder;
                simpleTextViewHolder.setData(items, methodName, position);
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
        private final TextView rowTextView;
        private View itemView;
        private EasyListView.OnItemClickListener onClickListener;

        SimpleTextViewHolder(View itemView, EasyListView.OnItemClickListener onClickListener) {
            super(itemView);
            rowTextView = itemView.findViewById(R.id.text_view);
            this.itemView = itemView;
            this.onClickListener = onClickListener;
        }

        void setData(List<T> items, Method methodName, final int position) {
            String itemViewData = getItemViewData(items.get(position), methodName);
            rowTextView.setText(itemViewData);
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
