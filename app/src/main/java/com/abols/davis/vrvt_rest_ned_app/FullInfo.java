package com.abols.davis.vrvt_rest_ned_app;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FullInfo extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    MapView Map;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_info);

        String restoran = getIntent().getStringExtra("restoran");
        String adress = getIntent().getStringExtra("adress");
        String phone = getIntent().getStringExtra("phone");
        String webpage = getIntent().getStringExtra("webpage");
        String price = getIntent().getStringExtra("price");
        String coment = getIntent().getStringExtra("coment");
        String food1 = getIntent().getStringExtra("food1");
        String food2 = getIntent().getStringExtra("food2");
        String food3 = getIntent().getStringExtra("food3");
        String day1 = getIntent().getStringExtra("day1");
        String day2 = getIntent().getStringExtra("day2");
        String day3 = getIntent().getStringExtra("day3");
        String day4 = getIntent().getStringExtra("day4");
        String day5 = getIntent().getStringExtra("day5");
        String day6 = getIntent().getStringExtra("day6");
        String day7 = getIntent().getStringExtra("day7");

        TextView _restoran = findViewById(R.id.RestoranName);
        _restoran.setText(restoran);
        TextView _price = findViewById(R.id.Price);
        _price.setText(price + " EUR");
        TextView _food1 = findViewById(R.id.Food1);
        _food1.setText(food1);
        TextView _food2 = findViewById(R.id.Food2);
        _food2.setText(food2);
        TextView _food3 = findViewById(R.id.Food3);
        _food3.setText(food3);
        TextView _coment = findViewById(R.id.Coment);
        _coment.setText(coment);
        TextView _day1 = findViewById(R.id.Day1);
        _day1.setText("P. " + day1);
        TextView _day2 = findViewById(R.id.Day2);
        _day2.setText("O. " + day2);
        TextView _day3 = findViewById(R.id.Day3);
        _day3.setText("T. " + day3);
        TextView _day4 = findViewById(R.id.Day4);
        _day4.setText("C. " + day4);
        TextView _day5 = findViewById(R.id.Day5);
        _day5.setText("Pk. " + day5);
        TextView _day6 = findViewById(R.id.Day6);
        _day6.setText("Se. " + day6);
        TextView _day7 = findViewById(R.id.Day7);
        _day7.setText("Sv. " + day7);
        TextView _webpage = findViewById(R.id.Web);
        _webpage.setText(webpage);

        image = (ImageView) findViewById(R.id.imageRest);
        image.setImageBitmap(loadImageFromStorage(restoran));

        Map = (MapView) findViewById(R.id.map);
        Map.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        final Button button = findViewById(R.id.Call);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:29464050"));
                startActivity(callIntent);
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


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
