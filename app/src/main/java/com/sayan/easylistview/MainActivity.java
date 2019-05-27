package com.sayan.easylistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sayan.easylistwidget.EasyListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<ItemsPOJO> listItems = new ArrayList<>();
        listItems.add(new ItemsPOJO("asd"));
        try {
            new EasyListView.Builder<ItemsPOJO>(this)
                    .addListItems(listItems)
                    .addRow(
                            EasyListView.ViewType.TEXT,
                            ItemsPOJO.class.getDeclaredMethod("getNames")).Build();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
