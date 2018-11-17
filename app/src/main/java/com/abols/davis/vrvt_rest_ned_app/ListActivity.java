package com.abols.davis.vrvt_rest_ned_app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView restoranList = (ListView) findViewById(R.id.list);

        final ArrayList<HashMap<String, String>> RestoranList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("arraylist");

        CustomList adapter = new CustomList(ListActivity.this, RestoranList);

//        ListAdapter adapter = new SimpleAdapter(ListActivity.this, RestoranList,
//                R.layout.list_item, new String[]{ "restoran","price"},
//                new int[]{R.id.Name, R.id.Price});
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
    public class CustomList extends ArrayAdapter<HashMap<String, String>> {

        ArrayList<HashMap<String, String>> list;
        Activity context;
        public CustomList(Activity context, ArrayList<HashMap<String, String>> restoranName) {
            super(context, R.layout.list_item,restoranName);
            this.context = context;
            this.list = restoranName;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public HashMap<String, String> getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.list_item, null, true);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.restoranImage);
            TextView name = (TextView) rowView.findViewById(R.id.Name);
            TextView price = (TextView) rowView.findViewById(R.id.Price);


            name.setText(list.get(position).get("restoran"));
            price.setText(list.get(position).get("price"));
            imageView.setImageBitmap(loadImageFromStorage(list.get(position).get("restoran")));
            return rowView;
        }
    }
    public Bitmap loadImageFromStorage(String name)
    {
        ContextWrapper cw = new ContextWrapper(getBaseContext());
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        Bitmap b;
        try {
            File mypath = new File(directory, name);
            b = BitmapFactory.decodeStream(new FileInputStream(mypath));
            return b;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
