package com.abols.davis.vrvt_rest_ned_app;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> RestoranList;
    String MainImageurl = "https://firebasestorage.googleapis.com/v0/b/vrvt-android.appspot.com/o/placeholder.jpg?alt=media&token=0ea191a1-4875-4017-a966-aee4d61e4a03";
    String JsonURL = "https://firebasestorage.googleapis.com/v0/b/vrvt-android.appspot.com/o/Android-MD.json?alt=media&token=e3abae8f-4b7b-4955-a8f8-6d30d9390dcb";
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Premition();

        RestoranList = new ArrayList<>();
        pDialog = new ProgressDialog(this);
        new GetJSONData().execute();
        if (loadImageFromStorage("Main") == null) {
            Log.e("aaa", "bbb");
            new GetMainImage().execute();
        } else {
            Log.e("aaa", "bbb");
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(loadImageFromStorage("Main"));
        }
    }
    public void Premition(){
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CALL_PHONE)) {

            }
            else {
                int MY_PERMISSIONS_REQUEST_READ_CONTACTS =1;
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    public String saveToInternalStorage(Bitmap bitmapImage, String name) {
        //saglabā bildi kašatmiņā
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        File mypath = new File(directory, name);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    public Bitmap loadImageFromStorage(String name) {
        // ielādē bildi no kešatmiņas
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        Bitmap b;
        try {
            File mypath = new File(directory, name);
            b = BitmapFactory.decodeStream(new FileInputStream(mypath));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class GetJSONData extends AsyncTask<Void, Void, Void> {
        // ielādē JSON datus par restoraniem
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Notiek ielāde...");
            pDialog.setCancelable(false);
            pDialog.show();
            Toast.makeText(MainActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = JsonURL;
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray City = jsonObj.getJSONArray("Valmiera");
                    // looping through All Contacts
                    for (int i = 0; i < City.length(); i++) {
                        JSONObject c = City.getJSONObject(i);
                        String restoran = c.getString("Restorans");
                        String image = c.getString("Attels");
                        String adress = c.getString("Adrese");
                        String phone = c.getString("Telefons");
                        String webpage = c.getString("Majaslapa");
                        String price = c.getString("Cena");
                        JSONObject eventTime = c.getJSONObject("Laikaposms");
                        String from = eventTime.getString("No");
                        String till = eventTime.getString("Lidz");
                        JSONObject workingTime = c.getJSONObject("Darbalaiks");
                        String day1 = workingTime.getString("P");
                        String day2 = workingTime.getString("O");
                        String day3 = workingTime.getString("T");
                        String day4 = workingTime.getString("C");
                        String day5 = workingTime.getString("Pk");
                        String day6 = workingTime.getString("Se");
                        String day7 = workingTime.getString("Sv");
                        JSONObject menu = c.getJSONObject("Edienkarte");
                        String food1 = menu.getString("Pirmais");
                        String food2 = menu.getString("Otrais");
                        String food3 = menu.getString("Deserts");
                        String coment = menu.getString("Komentars");

                        // tmp hash map for single contact
                        HashMap<String, String> Restorane = new HashMap<>();

                        // adding each child node to HashMap key => value
                        Restorane.put("restoran", restoran);
                        Restorane.put("image", image);
                        Restorane.put("adress", adress);
                        Restorane.put("phone", phone);
                        Restorane.put("webpage", webpage);
                        Restorane.put("price", price);
                        Restorane.put("from", from);
                        Restorane.put("till", till);
                        Restorane.put("day1", day1);
                        Restorane.put("day2", day2);
                        Restorane.put("day3", day3);
                        Restorane.put("day4", day4);
                        Restorane.put("day5", day5);
                        Restorane.put("day6", day6);
                        Restorane.put("day7", day7);
                        Restorane.put("food1", food1);
                        Restorane.put("food2", food2);
                        Restorane.put("food3", food3);
                        Restorane.put("coment", coment);
                        // adding contact to contact list
                        RestoranList.add(Restorane);
                    }
                } catch (final JSONException e) {
                    Log.e("resp", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e("resp", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();

            for (int i = 0; i < RestoranList.size(); i++) {
                if (loadImageFromStorage(String.valueOf(RestoranList.get(i).get("restoran"))) == null) {
                    new LoudJosnImages().execute(RestoranList.get(i).get("image"), String.valueOf(i));
                }
            }
Log.e("aaa", "aaa");
            final Button button = findViewById(R.id.mainButton);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    intent.putExtra("arraylist", RestoranList);
                    startActivityForResult(intent, 500);
                }
            });
        }
    }

    private class GetMainImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!pDialog.isShowing())
                pDialog.show();
        }
        //Ielādē sākuma attēlu
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(MainImageurl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                Log.d("image", "bilde novilkta");
                return myBitmap;
            } catch (Exception e) {
                Log.d("image", e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            saveToInternalStorage(result, "Main");
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(loadImageFromStorage("Main"));
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }

    private class LoudJosnImages extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!pDialog.isShowing())
                pDialog.show();
        }
        //ielādē restoranu bildes un saglaba telefona kešatmiņā
        public int index;
        @Override
        protected Bitmap doInBackground(String... sUrl) {
            try {
                URL url = new URL(sUrl[0]);
                index = Integer.parseInt(sUrl[1]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                Log.d("image", e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            saveToInternalStorage(result, RestoranList.get(index).get("restoran"));
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }
}




