package com.abols.davis.vrvt_rest_ned_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView restoranList = (ListView) findViewById(R.id.list);

        final ArrayList<HashMap<String, String>> RestoranList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("arraylist");

        ListAdapter adapter = new SimpleAdapter(ListActivity.this, RestoranList,
                R.layout.list_item, new String[]{ "restoran","price"},
                new int[]{R.id.Name, R.id.Price});
        restoranList.setAdapter(adapter);

        restoranList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ListActivity.this, FullInfo.class);
                intent.putExtra("restoran",RestoranList.get(position).get("restoran"));
                intent.putExtra("adress",RestoranList.get(position).get("adress"));
                intent.putExtra("phone",RestoranList.get(position).get("phone"));
                intent.putExtra("webpage",RestoranList.get(position).get("webpage"));
                intent.putExtra("price",RestoranList.get(position).get("price"));
                intent.putExtra("day1",RestoranList.get(position).get("day1"));
                intent.putExtra("day2",RestoranList.get(position).get("day2"));
                intent.putExtra("day3",RestoranList.get(position).get("day3"));
                intent.putExtra("day4",RestoranList.get(position).get("day4"));
                intent.putExtra("day5",RestoranList.get(position).get("day5"));
                intent.putExtra("day6",RestoranList.get(position).get("day6"));
                intent.putExtra("day7",RestoranList.get(position).get("day7"));
                intent.putExtra("food1",RestoranList.get(position).get("food1"));
                intent.putExtra("food2",RestoranList.get(position).get("food2"));
                intent.putExtra("food3",RestoranList.get(position).get("food3"));
                intent.putExtra("coment",RestoranList.get(position).get("coment"));
                startActivityForResult(intent, 500);
        }

    });
    }
}
