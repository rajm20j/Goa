package com.example.metroapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] stations=  getResources().getStringArray(R.array.stations);

        AutoCompleteTextView src = findViewById(R.id.srchome);
        ArrayAdapter<String> srcadapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, stations);
        src.setAdapter(srcadapter);

        AutoCompleteTextView dest = findViewById(R.id.desthome);
        ArrayAdapter<String> destadapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, stations);
        dest.setAdapter(destadapter);
    }
}
