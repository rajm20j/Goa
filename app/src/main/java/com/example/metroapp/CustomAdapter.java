package com.example.metroapp;

import android.animation.FloatArrayEvaluator;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.metroapp.R;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<String> stationList;
    ArrayList<String> connectingList;
    ArrayList<String> terminalList;
    ArrayList<String> colorList;
    Context context;

    public CustomAdapter(Context context, ArrayList<String> stationList, ArrayList<String> connectingList, ArrayList<String> terminalList, ArrayList<String> colorList) {
        this.context = context;
        this.stationList = stationList;
        this.connectingList = connectingList;
        this.terminalList = terminalList;
        this.colorList = colorList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.station.setText(stationList.get(position));
        holder.connecting.setText(connectingList.get(position));
        holder.terminal.setText(terminalList.get(position));
        holder.color.setText(colorList.get(position));
       // holder.distance.setText(distanceList.get(position));
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText(context, stationList.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return stationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView station, connecting,color,terminal,distance;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            station = (TextView) itemView.findViewById(R.id.station);
            connecting = (TextView) itemView.findViewById(R.id.connecting);
            terminal = (TextView) itemView.findViewById(R.id.terminal);
            color = (TextView) itemView.findViewById(R.id.color);

        }
    }
}