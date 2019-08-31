package com.example.metroapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class RouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Intent intRoute = getIntent();
        String src = intRoute.getStringExtra("SRC");
        String dest = intRoute.getStringExtra("DEST");

        TextView source = (TextView) findViewById(R.id.route_src);
        source.setText(src);

        TextView destination = (TextView) findViewById(R.id.route_dest);
        destination.setText(dest);

    }
}

