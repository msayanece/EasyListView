package com.sayan.easylistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.sayan.easylistwidget.EasyListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EasyListView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<ItemsPOJO> listItems = new ArrayList<>();
        listItems.add(new ItemsPOJO("asd"));
        listItems.add(new ItemsPOJO("abc"));
        listItems.add(new ItemsPOJO("xyz"));
        listItems.add(new ItemsPOJO("123"));
        listItems.add(new ItemsPOJO("000"));
        listItems.add(new ItemsPOJO("rrr"));
        listItems.add(new ItemsPOJO("sss"));
        listItems.add(new ItemsPOJO("uuuu"));
        listItems.add(new ItemsPOJO("ppp"));
        listItems.add(new ItemsPOJO("ddd"));

        try {
            new EasyListView.Builder<ItemsPOJO>(this)
                    .addRecyclerView(recyclerView)
                    .addListItems(listItems)
                    .addItemModel(ItemsPOJO.class)
                    .addRow(EasyListView.ViewType.TEXT, "getNames")
                    .setOnItemClickListener(this)
                    .Build();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view, int position) {
        Toast.makeText(this, position + " :clicked", Toast.LENGTH_SHORT).show();
    }
}
