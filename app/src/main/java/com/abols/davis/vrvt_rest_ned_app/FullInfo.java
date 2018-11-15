package com.abols.davis.vrvt_rest_ned_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class FullInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_info);
        String restoran = getIntent().getStringExtra("restoran");
        Log.d("huja", restoran);

    }
}
