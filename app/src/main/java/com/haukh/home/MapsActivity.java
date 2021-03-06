package com.haukh.home;

import android.Manifest;
import android.app.ProgressDialog;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    GoogleMap mMap;
    List<WebCamHelper> myWebcamList;


    String url = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_maps);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.map_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Map");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        myWebcamList = new ArrayList<>();

        myWebcamList = parseJson();


        locationPermitted();
        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);


        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(6);

        mMap.setOnMarkerClickListener(this);

        // Set a listener for info window events.
        mMap.setOnInfoWindowClickListener(this);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();
        marker.getTag();
        marker.getTitle();
        marker.getId();

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("MARKERCLICK", "value: ");
        return false;
    }

    public List<WebCamHelper> parseJson() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting Things Ready..");
        progressDialog.show();

        final WebCamHelper myHelper = new WebCamHelper();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("Features");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject camObj = array.getJSONObject(i);

                        JSONArray camLocation = camObj.getJSONArray("PointCoordinate");

                        myHelper.setLatitude(camLocation.getDouble(0));
                        myHelper.setLongitude(camLocation.getDouble(1));


                        JSONArray camArray = camObj.getJSONArray("Cameras");
                        for (int j = 0; j < camArray.length(); j++) {
                            JSONObject cameras = camArray.getJSONObject(j);


                            myHelper.setLiveCamID(cameras.getString("Id"));
                            myHelper.setLabel(cameras.getString("Description"));
                            myHelper.setType(cameras.getString("Type"));
                            myHelper.setImageUrl(cameras.getString("ImageUrl"));

                        }

                        MarkerOptions markerOptions = new MarkerOptions();
                        LatLng newCamLocation = new LatLng(myHelper.getLatitude(),
                                myHelper.getLongitude());
                        if (myHelper.getType().equals(("wsdot"))) {
                            markerOptions.position(newCamLocation).title(myHelper.getLabel())
                                    .snippet(myHelper.getLabel())
                                    .icon(BitmapDescriptorFactory
                                            .defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        } else {
                            markerOptions.position(newCamLocation).title(myHelper.getLabel())
                                    .snippet(myHelper.getLabel())
                                    .icon(BitmapDescriptorFactory
                                            .defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                        }

                        mMap.setInfoWindowAdapter(new CustomInfoWindowMap(MapsActivity.this));
                        Marker m = mMap.addMarker((markerOptions));
                        m.setTag(myHelper);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(newCamLocation));

                        myWebcamList.add(myHelper);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

        return myWebcamList;
    }


    private void locationPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showDefaultLocation() {
        LatLng Seattle = new LatLng(47.692830562, -122.333);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Seattle));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermitted();

                } else {
                    showDefaultLocation();
                }
                //  return;
            }

        }
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(6);
                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {

                    mMap.setMinZoomPreference(6);

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));

                    circleOptions.radius(220);
                    circleOptions.fillColor(Color.RED);
                    circleOptions.strokeWidth(6);

                    mMap.addCircle(circleOptions);
                }
            };




    //-------------------------------------------------------------------------------
    //Menu and Navigation
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





