package com.example.metroapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    private static final String[] Stations = new String[]
            {
                    "Vaishali", "Kaushambi", "Anand Vihar", "Karkarduma", "Preet Vihar", "Nirman Vihar", "Laxmi Nagar", "Yamuna Bank"
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoCompleteTextView src = findViewById(R.id.srchome);
        ArrayAdapter<String> srcadapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, Stations);
        src.setAdapter(srcadapter);

        AutoCompleteTextView dest = findViewById(R.id.desthome);
        ArrayAdapter<String> destadapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, Stations);
        dest.setAdapter(destadapter);
    }
}
