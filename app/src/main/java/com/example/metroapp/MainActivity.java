package com.example.metroapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("Tag","THIS IS LOGCAT");

        String[] stations=  getResources().getStringArray(R.array.stations);

        AutoCompleteTextView src = findViewById(R.id.main_src);
        ArrayAdapter<String> srcadapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, stations);
        src.setAdapter(srcadapter);

        AutoCompleteTextView dest = findViewById(R.id.main_dest);
        ArrayAdapter<String> destadapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, stations);
        dest.setAdapter(destadapter);



    }
}
