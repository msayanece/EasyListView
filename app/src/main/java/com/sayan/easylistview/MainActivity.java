package com.sayan.easylistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.sayan.easylistwidget.EasyListView;
import com.sayan.easylistwidget.ListTile;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EasyListView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<ItemsPOJO> listItems = new ArrayList<>();
        listItems.add(new ItemsPOJO("Sayan", "The Crazy Leader", "https://at-cdn-s01.audiotool.com/2010/12/01/documents/5obpzwkq/1/cover256x256-5b9c62a0549047c7800ee7b8fc82b2f3.jpg"));
        listItems.add(new ItemsPOJO("Debayan", "The innovative thinker", "https://is1-ssl.mzstatic.com/image/thumb/Purple118/v4/36/85/b8/3685b864-3d02-1b6f-04a5-074d1892d8f6/source/256x256bb.jpg"));
        listItems.add(new ItemsPOJO("Ashutosh", "Knows everything", "https://at-cdn-s01.audiotool.com/2010/12/01/documents/5obpzwkq/1/cover256x256-5b9c62a0549047c7800ee7b8fc82b2f3.jpg"));
        listItems.add(new ItemsPOJO("Mondira", "One woman army", "https://is1-ssl.mzstatic.com/image/thumb/Purple118/v4/36/85/b8/3685b864-3d02-1b6f-04a5-074d1892d8f6/source/256x256bb.jpg"));
        listItems.add(new ItemsPOJO("Joy Prakash", "The designer pandit", "https://at-cdn-s01.audiotool.com/2010/12/01/documents/5obpzwkq/1/cover256x256-5b9c62a0549047c7800ee7b8fc82b2f3.jpg"));
        listItems.add(new ItemsPOJO("Arup", "The Java developer", "https://is1-ssl.mzstatic.com/image/thumb/Purple118/v4/36/85/b8/3685b864-3d02-1b6f-04a5-074d1892d8f6/source/256x256bb.jpg"));
        listItems.add(new ItemsPOJO("Krishna", "The source coder", "https://at-cdn-s01.audiotool.com/2010/12/01/documents/5obpzwkq/1/cover256x256-5b9c62a0549047c7800ee7b8fc82b2f3.jpg"));
        listItems.add(new ItemsPOJO("Amit", "The code enjoyer", "https://is1-ssl.mzstatic.com/image/thumb/Purple118/v4/36/85/b8/3685b864-3d02-1b6f-04a5-074d1892d8f6/source/256x256bb.jpg"));

        try {
            ListTile<ItemsPOJO> listTile = new ListTile<ItemsPOJO>(ItemsPOJO.class)
                    .addTitle("getName")
                    .addDescription("getDesc")
                    .addIcon(EasyListView.IconPosition.LEADING, "getImage");

            new EasyListView.Builder<ItemsPOJO>(this)
                    .addRecyclerView(recyclerView)
                    .addListItems(listItems)
                    .addItemModel(ItemsPOJO.class)
                    .addRow(listTile)
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
