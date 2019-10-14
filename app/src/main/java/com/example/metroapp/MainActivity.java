package com.example.metroapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String source;
    String destination;
    //FrameLayout fragmentcontainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] stations = getResources().getStringArray(R.array.stations);

        AutoCompleteTextView src = (AutoCompleteTextView) findViewById(R.id.main_src);
        ArrayAdapter<String> srcadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stations);
        src.setAdapter(srcadapter);
        src.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                source = (String) adapterView.getItemAtPosition(i);
            }
        });

        AutoCompleteTextView dest = findViewById(R.id.main_dest);
        ArrayAdapter<String> destadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stations);
        dest.setAdapter(destadapter);
        dest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                destination = (String) adapterView.getItemAtPosition(i);
            }
        });

        //source=src.getText().toString();
        //destination=dest.getText().toString();

        Button route = (Button) findViewById(R.id.main_route);
        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intRoute = new Intent(MainActivity.this, RouteActivity.class);
                intRoute.putExtra("SRC", source);
                intRoute.putExtra("DEST", destination);
                startActivity(intRoute);
            }
        });



        ImageButton recharge_btn = (ImageButton) findViewById(R.id.main_recharge);
        recharge_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intRecharge = new Intent(MainActivity.this, Recharge.class);
                //startActivity(intRecharge);
                Intent rechargeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://paytm.com/metro-card-recharge"));
                startActivity(rechargeIntent);


            }
        });



        ImageButton map_btn = (ImageButton) findViewById(R.id.main_map);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent (MainActivity.this, Map.class);
                startActivity(mapIntent);
            }
        });
    }

}