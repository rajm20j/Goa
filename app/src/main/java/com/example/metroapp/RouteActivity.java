package com.example.metroapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RouteActivity extends AppCompatActivity {

    ArrayList <Integer> finalPath = new ArrayList<>();
    ArrayList <String> finalPathString = new ArrayList<>();
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
        Integer u1,v1,stationNo=100;



        try {
            // get JSONObject from JSON file
            // JSONObject obj = new JSONObject(loadJSONFromAsset("maps.json"));
            JSONObject nameToId = new JSONObject(loadJSONFromAsset("mapid.json"));




            JSONArray mapid = nameToId.getJSONArray("map_id");
            for(int s = 0; s < mapid.length(); s++){
                JSONObject temp = mapid.getJSONObject(s);
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


        Graph g = new Graph(stationNo+1);


        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("newmap.json"));
            JSONArray edgeArray = obj.getJSONArray("map");
            // implement for loop for getting users list data
            for (int i = 0; i < edgeArray.length(); i++) {
                // create a JSONObject for fetching single user data

                JSONObject stationDetail = edgeArray.getJSONObject(i);

                u1 = Integer.parseInt(stationDetail.getString("Start"));
                v1 = Integer.parseInt(stationDetail.getString("End"));
                g.addEdge(u1,v1);

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        g.findpath(srcint,destint,shortestDist,finalPath);


        try {
            // get JSONObject from JSON file
            // JSONObject obj = new JSONObject(loadJSONFromAsset("maps.json"));
            JSONObject nameToId = new JSONObject(loadJSONFromAsset("mapid.json"));
            // fetch JSONArray named users



            JSONArray mapid = nameToId.getJSONArray("map_id");
            for(int j=0;j<finalPath.size();j++) {
                for (int i = 0; i < mapid.length(); i++) {
                    JSONObject temp = mapid.getJSONObject(i);
                    //Make all visited 0
                    //visited.put(Integer.parseInt(temp.getString("ID")),0);
                    if (Integer.toString(finalPath.get(j)).equals(temp.getString("ID"))) {
                        finalPathString.add(temp.getString("Stations"));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }





        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(RouteActivity.this, finalPathString);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView



        Integer totalStationsInPath = finalPath.size();
        double temp=0;
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("newmap.json"));
            JSONArray edgeArray = obj.getJSONArray("map");
//            JSONObject conn = new JSONObject(loadJSONFromAsset("connecting.json"));


  //          JSONArray connArray = conn.getJSONArray("connecting");
            // implement for loop for getting users list data
            for (int j = 0; j < finalPath.size() - 2; j++) {
                u1 = finalPath.get(j);
                v1 = finalPath.get(j + 1);



                for (int i = 0; i < edgeArray.length(); i++) {



                    JSONObject stationDetail = edgeArray.getJSONObject(i);
                    if (Integer.parseInt(stationDetail.getString("Start")) == u1 && Integer.parseInt(stationDetail.getString("End")) == v1) {
                        temp += Double.parseDouble(stationDetail.getString("Distance"));

                        //Connecting waala issue fix krna h
                        //if(u1!=finalPath.get(0)){
                        //  for (int k1=0;k1<connArray.length();k1++){
                        //    JSONObject temp1 = connArray.getJSONObject(i);
                        //  if(u1==Integer.parseInt(temp1.getString("ID"))){
                        //    temp+=Double.parseDouble(temp1.getString("Buffer"));
                        //  break;
                        //}
                        //}
                        // }

                        while (Integer.parseInt(edgeArray.getJSONObject(i + 1).getString("Start")) == finalPath.get(j + 1) && Integer.parseInt(edgeArray.getJSONObject(i + 1).getString("End")) == finalPath.get(j + 2)) {
                            temp += Double.parseDouble(edgeArray.getJSONObject(i + 1).getString("Distance"));
                            i++;
                            j++;
                            u1= finalPath.get(j);
                            v1= finalPath.get(j+1);
                            if (j+2>=finalPath.size()-1||i+1>=edgeArray.length()-1){

                                break;
                            }

                        }

                    }

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Integer totalTime = (int) Math.round((temp/35)*60);
        String timetotal = "Total time - "+totalTime+" mins";
        String stationtotal = "No. of statons - "+totalStationsInPath;

        EditText time = (EditText) findViewById(R.id.time);
        EditText station = (EditText) findViewById(R.id.number_of_stations);
        time.setText(timetotal);
        station.setText(stationtotal);
        //Average speed of metro is 35 km/hr





    }




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
            sd = printAllPathsUtil(s, d, isVisited, pathList,fp,sd);

        }

        // A recursive function to print
        // all paths from 'u' to 'd'.
        // isVisited[] keeps track of
        // vertices in current path.
        // localPathList<> stores actual
        // vertices in the current path
        private double printAllPathsUtil(Integer u, Integer d,
                                         boolean[] isVisited,
                                         List<Integer> localPathList, ArrayList<Integer> fp, double sd) {

            // Mark the current node
            isVisited[u] = true;

            if (u.equals(d)) {
                double temp = 0;
                int u1 = 0;
                int v1 = 0;




                try {
                    JSONObject obj = new JSONObject(loadJSONFromAsset("newmap.json"));
                    JSONArray edgeArray = obj.getJSONArray("map");
                    // implement for loop for getting users list data
                    outer : for (int j=0; j< localPathList.size()-2;j++ ) {
                        u1= localPathList.get(j);
                        v1= localPathList.get(j+1);
                        for (int i = 0; i < edgeArray.length(); i++) {
                            // create a JSONObject for fetching single user data

                            JSONObject stationDetail = edgeArray.getJSONObject(i);
                            if(Integer.parseInt(stationDetail.getString("Start"))==u1 && Integer.parseInt(stationDetail.getString("End"))==v1) {
                                temp += Double.parseDouble(stationDetail.getString("Distance"));

                                while (Integer.parseInt(edgeArray.getJSONObject(i + 1).getString("Start")) == localPathList.get(j + 1) && Integer.parseInt(edgeArray.getJSONObject(i + 1).getString("End")) == localPathList.get(j + 2)) {
                                    temp += Double.parseDouble(edgeArray.getJSONObject(i + 1).getString("Distance"));

                                    i++;
                                    j++;
                                    u1= localPathList.get(j);
                                    v1= localPathList.get(j+1);
                                    if (j+2>=localPathList.size()-1||i+1>=edgeArray.length()-1){

                                        break;
                                    }
                                }

                            }
                            // fetch email and name and store it in arraylist
                            //stationList.add(Integer.parseInt(stationDetail.getString("ID")));
                            //connectingList.add(Integer.parseInt(stationDetail.getString("Connecting")));
                            //terminalList.add(Integer.parseInt(stationDetail.getString("Terminal")));
                            //colorList.add(stationDetail.getString("Color"));
                            //distanceList.add(Float.parseFloat(stationDetail.getString("Distance")));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(temp<sd){
                    sd=temp;
                    fp.clear();
                    for(int i=0;i<localPathList.size();i++){
                        fp.add(localPathList.get(i));
                    }

                }



                // System.out.println(localPathList);
                // if match found then no need to traverse more till depth
                isVisited[u] = false;
                return sd ;
            }

            // Recur for all the vertices
            // adjacent to current vertex
            for (Integer i : adjList[u]) {
                if (!isVisited[i]) {
                    // store current node
                    // in path[]
                    localPathList.add(i);
                    sd = printAllPathsUtil(i, d, isVisited, localPathList,fp,sd);

                    // remove current node
                    // in path[]
                    localPathList.remove(i);
                }
            }



            // Mark the current node
            isVisited[u] = false;
            return sd;
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

