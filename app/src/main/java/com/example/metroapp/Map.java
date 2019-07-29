package com.example.metroapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jsibbold.zoomage.ZoomageView;


public class Map extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ZoomageView imageZoom = (ZoomageView)findViewById(R.id.myZoomageView);
    }
}
