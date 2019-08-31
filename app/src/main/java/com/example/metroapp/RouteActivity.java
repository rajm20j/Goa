package com.example.metroapp;

import android.content.Intent;
import android.os.Bundle;

import android.view.ViewDebug;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;

public class RouteActivity extends AppCompatActivity {
    //ArrayList<Integer> stationList= new ArrayList<>();
    //ArrayList<Integer> connectingList= new ArrayList<>();
    //ArrayList<Integer> terminalList= new ArrayList<>();
    //ArrayList<Float> distanceList= new ArrayList<>();
    //ArrayList<String> colorList= new ArrayList<>();
    //Queue <Integer> sourceq = new LinkedList<Integer>();
    //Queue <Integer> destq = new LinkedList<Integer>();
    //HashMap<Integer,Integer> visited = new HashMap<Integer, Integer>();

    ArrayList <Integer> finalPath = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Intent intRoute = getIntent();
        String src = intRoute.getStringExtra("SRC");
        String dest = intRoute.getStringExtra("DEST");
        Integer srcint=0;
        double shortestDist = 99999999.0;
        Integer destint=0;
        Integer u1,v1,stationNo=-1;

       // RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        // recyclerView.setLayoutManager(linearLayoutManager);


        try {
            // get JSONObject from JSON file
            // JSONObject obj = new JSONObject(loadJSONFromAsset("maps.json"));
            JSONObject nameToId = new JSONObject(loadJSONFromAsset("mapid.json"));
            // fetch JSONArray named users



            JSONArray mapid = nameToId.getJSONArray("map_id");
            for(int i = 0; i < mapid.length(); i++){
                JSONObject temp = mapid.getJSONObject(i);
                //Make all visited 0
                //visited.put(Integer.parseInt(temp.getString("ID")),0);

                if(src.equals(temp.getString("Stations"))){
                    srcint=Integer.parseInt(temp.getString("ID"));
                }
                if (dest.equals(temp.getString("Stations"))){
                    destint=Integer.parseInt(temp.getString("ID"));
                }
                stationNo = Integer.parseInt(temp.getString("ID"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Graph g = new Graph(stationNo);


        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("newmap.json"));
            JSONArray edgeArray = obj.getJSONArray("map");
            // implement for loop for getting users list data
            for (int i = 0; i < edgeArray.length(); i++) {
                // create a JSONObject for fetching single user data

                JSONObject stationDetail = edgeArray.getJSONObject(i);
                // fetch email and name and store it in arraylist
                //stationList.add(Integer.parseInt(stationDetail.getString("ID")));
                //connectingList.add(Integer.parseInt(stationDetail.getString("Connecting")));
                //terminalList.add(Integer.parseInt(stationDetail.getString("Terminal")));
                //colorList.add(stationDetail.getString("Color"));
                //distanceList.add(Float.parseFloat(stationDetail.getString("Distance")));

                u1 = Integer.parseInt(stationDetail.getString("Start"));
                v1 = Integer.parseInt(stationDetail.getString("End"));
                g.addEdge(u1,v1);

            }



        } catch (JSONException e) {
           e.printStackTrace();
        }

        g.findpath(srcint,destint,shortestDist,finalPath);



        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        //CustomAdapter customAdapter = new CustomAdapter(RouteActivity.this, stationList, connectingList, terminalList, colorList);
        //recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

    }



    // A directed graph using
// adjacency list representation
    public class Graph {

        // No. of vertices in graph
        public int v;

        // adjacency list
        public ArrayList<Integer>[] adjList;

        //Constructor
        public Graph(int vertices) {

            //initialise vertex count
            this.v = vertices;

            // initialise adjacency list
            initAdjList();
        }

        // utility method to initialise
        // adjacency list
        @SuppressWarnings("unchecked")
        private void initAdjList() {
            adjList = new ArrayList[v];

            for (int i = 0; i < v; i++) {
                adjList[i] = new ArrayList<>();
            }
        }

        // add edge from u to v
        public void addEdge(int u, int v) {
            // Add v to u's list.
            adjList[u].add(v);
        }

        // Prints all paths from
        // 's' to 'd'
        public void findpath(int s, int d, double sd, ArrayList<Integer> fp) {
            boolean[] isVisited = new boolean[v];
            ArrayList<Integer> pathList = new ArrayList<>();

            //add source to path[]
            pathList.add(s);

            //Call recursive utility
            printAllPathsUtil(s, d, isVisited, pathList,fp,sd);

        }

        // A recursive function to print
        // all paths from 'u' to 'd'.
        // isVisited[] keeps track of
        // vertices in current path.
        // localPathList<> stores actual
        // vertices in the current path
        private void printAllPathsUtil(Integer u, Integer d,
                                       boolean[] isVisited,
                                       List<Integer> localPathList, ArrayList<Integer> fp, Integer sd) {

            // Mark the current node
            isVisited[u] = true;

            if (u.equals(d)) {
                double temp = 0;
                int u1 = 0;
                int v1 = 0;


                //INCLUDE PATH STORING HERE
                //EditText test = (EditText) findViewById(R.id.testsrcint);
                //String path="";
                //for (int i =0; i<localPathList.size(); i++){
                //    path = path + " " + Integer.toString(localPathList.get(i));
                //}
                //test.setText(path);

                try {
                    JSONObject obj = new JSONObject(loadJSONFromAsset("newmap.json"));
                    JSONArray edgeArray = obj.getJSONArray("map");
                    // implement for loop for getting users list data
                    for (int j=0; j< localPathList.size()-1;j++ ) {
                        u1= localPathList[j];
                        v1= localPathList[j+1];
                        for (int i = 0; i < edgeArray.length(); i++) {
                            // create a JSONObject for fetching single user data

                            JSONObject stationDetail = edgeArray.getJSONObject(i);
                            // fetch email and name and store it in arraylist
                            //stationList.add(Integer.parseInt(stationDetail.getString("ID")));
                            //connectingList.add(Integer.parseInt(stationDetail.getString("Connecting")));
                            //terminalList.add(Integer.parseInt(stationDetail.getString("Terminal")));
                            //colorList.add(stationDetail.getString("Color"));
                            //distanceList.add(Float.parseFloat(stationDetail.getString("Distance")));
                            //Refer shanky ki copy

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // System.out.println(localPathList);
                // if match found then no need to traverse more till depth
                isVisited[u] = false;
                return;
            }

            // Recur for all the vertices
            // adjacent to current vertex
            for (Integer i : adjList[u]) {
                if (!isVisited[i]) {
                    // store current node
                    // in path[]
                    localPathList.add(i);
                    printAllPathsUtil(i, d, isVisited, localPathList);

                    // remove current node
                    // in path[]
                    localPathList.remove(i);
                }
            }



            // Mark the current node
            isVisited[u] = false;
        }
    }





        public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}

