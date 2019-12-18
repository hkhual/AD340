package com.haukh.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class WebCam extends AppCompatActivity {

    //Declare variables for WebCam
    private String TAG = MainActivity.class.getSimpleName();

    private MyAdapter mAdapter;
    private ArrayList<WebCamHelper> webcamArrList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_cam);


        Toolbar toolbar = (Toolbar) findViewById(R.id.live_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LiveCam");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        RecyclerView camView = findViewById(R.id.my_Cam_recycler_view);
        camView.setHasFixedSize(true);
        camView.setLayoutManager(new LinearLayoutManager(this));
        webcamArrList = new ArrayList<WebCamHelper>();
        mAdapter = new MyAdapter(WebCam.this, webcamArrList);
        camView.setAdapter(mAdapter);


        requestQueue = Volley.newRequestQueue(this);
        getJsonContent();
    }


    private void getJsonContent() {
        String url = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Features");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject feature = jsonArray.getJSONObject(i);

                        JSONArray cameras = feature.getJSONArray("Cameras");
                        for (int j = 0; j < cameras.length(); j++) {
                            JSONObject camera = cameras.getJSONObject(j);
                            String id = camera.getString("Id");
                            String type = camera.getString("Type");
                            String imageURL = camera.getString("ImageUrl");
                            if (type.equals("sdot")) {
                                imageURL = "http://www.seattle.gov/trafficcams/images/" + imageURL;
                            } else {
                                imageURL = "http://images.wsdot.wa.gov/nw/" + imageURL;
                            }
                            String camDescription = camera.getString("Description");
                            webcamArrList.add(new WebCamHelper(id, camDescription, imageURL, type));
                        }
                    }

                    //Notify adapter that dataset changed
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        requestQueue.add(request);
    }



    //My Adapter class
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private Context context;
        private ArrayList<WebCamHelper> CamList;
        private ArrayList<WebCamHelper> mFilteredList;

        //MyAdapther method
        private MyAdapter(Context context, ArrayList<WebCamHelper> CamList) {
            this.context = context;
            this.CamList = CamList;
            mFilteredList = CamList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.live_cam_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            WebCamHelper trafficCam = CamList.get(position);
            String imageURL = trafficCam.getImage();
            String trafficCamDescription = trafficCam.getLabel();
            String trafficCamType = trafficCam.getType();

            holder.camLabel.setText(trafficCamDescription);
            holder.camType.setText(trafficCamType);
            Picasso.with(context).load(imageURL).fit().centerInside().into(holder.imageWebam);
        }


        class ViewHolder extends RecyclerView.ViewHolder {
             ImageView imageWebam;
            TextView camLabel;
             TextView camType;

             ViewHolder(View itemView) {
                super(itemView);
                imageWebam = itemView.findViewById(R.id.live_Cam_Image);
                camLabel = itemView.findViewById(R.id.cam_description);
                camType = itemView.findViewById(R.id.cam_type);
            }
        }

        @Override
        public int getItemCount() {
            return CamList.size();
        }



    }


    //------------------------------
    //Menu items section
    @Override//Menu method
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "You clicked a setting",
                    Toast.LENGTH_SHORT).show();
            return true;
        }else if(id==R.id.action_about){
            Intent intent = new Intent(this, AboutApp.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}