package com.example.haukh.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.LocationListener;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
GoogleMap.OnMarkerClickListener, LocationListener{

    private GoogleMap mMap;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Location mLastLocation;

    private Hashtable<String, String> markers = new Hashtable<>();

    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        //Display the toolbar by calling its id name
        Toolbar myToolbar = (Toolbar) findViewById(R.id.map_toolbar);
        //setSupportActionBar(myToolbar);
        myToolbar.setTitle("Google Map");




        // Toolbar toolbar = (Toolbar) findViewById(R.id.about_toolbar);
        // setSupportActionBar(toolbar);
        // getSupportActionBar().setTitle("About");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





        //Instantiates the mGoogleApiClient filed if it's null.
        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }





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

        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setOnMarkerClickListener(this);


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(47.6, -122.33);
        LatLng cam1 = new LatLng(47, -123.3);


       // mMap.addMarker(new MarkerOptions().position(sydney).title("Seattle"));
        mMap.addMarker(new MarkerOptions().position(cam1));
        mMap.moveCamera((CameraUpdateFactory.newLatLng(cam1)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cam1, 12));
       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12));


        getJsonContent();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setUpMap();



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    @Override
    protected void onStart(){
        super.onStart();
        //Initiates a background connection of the
        // client to Google Play Services.
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop(){
        super.onStop();
        //Closes the connection to Google Play
        //Services if the client is not null and is connected.
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }



    //Request permission from the use to use
    //the phone GPS Location
    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        // setMyLocationEnabled enables the my-location layer
        // which draws a light blue dot on the user’s location.
        // It also adds a button to the map that, when tapped,
        // centers the map on the user’s location.
        mMap.setMyLocationEnabled(true);


        // getLocationAvailability determines
        // the availability of location data on the device.
        LocationAvailability locationAvailability =
                LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (null != locationAvailability && locationAvailability.isLocationAvailable()) {
            // getLastLocation gives you the most recent location currently available
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            // If you were able to retrieve the the most recent location, then move the camera to the user’s current location.
            if (mLastLocation != null) {
                LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation
                        .getLongitude());
                //add pin marker at user's location
                placeMarkerOnMap(currentLocation);

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
            }
        }






    }





    private void getJsonContent() {

         final WebCamHelper myHelper = new WebCamHelper();


        String url = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Features");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject feature = jsonArray.getJSONObject(i);

                        JSONArray camLocation = feature.getJSONArray("PointCoordinate");
                        //Assign latitude and longtitude IDs
                        double latitude = camLocation.getDouble(0);
                        double longtitude = camLocation.getDouble(1);
                            //Pass the latitude and longtitude to webCamHelper method
                            new WebCamHelper(latitude, longtitude);

                        for (int j = 0; j < camLocation.length(); j++) {
                            JSONObject camera = camLocation.getJSONObject(j);
                            String id = camera.getString("Id");
                            String camDescription = camera.getString("Description");
                            String imageURL = camera.getString("ImageUrl");
                            String type = camera.getString("Type");


                            //Pass the data to WebCamHelper method S
                            new WebCamHelper(id, camDescription, imageURL, type);



                            final MarkerOptions markerOptions = new MarkerOptions();
                            LatLng latLng = new LatLng(myHelper.getLatitude(),
                                    myHelper.getLongitude());


                            if (type.equals("sdot")) {
                                imageURL = "http://www.seattle.gov/trafficcams/images/" + imageURL;
                                markerOptions.position(latLng).title(myHelper.getLabel()).snippet(myHelper.getLabel());
                            } else {
                                imageURL = "http://images.wsdot.wa.gov/nw/" + imageURL;
                                markerOptions.position(latLng).title(myHelper.getLabel()).snippet(myHelper.getLabel());
                            }


                            Marker marker = mMap.addMarker(markerOptions);
                            markers.put(marker.getId(), myHelper.getImage() );
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        }
                    }

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
       RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }



    //To place Marker on map
    protected void placeMarkerOnMap(LatLng location){
        //sets the user's current location as for the marker
        MarkerOptions markerOptions = new MarkerOptions().position(location);

        //Add Custom icon for user current location
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource
                (getResources(), R.mipmap.ic_user_location)));


        //Get user current location Address
        String titleStr = getAddress(location);
        markerOptions.title(titleStr);

        //Add the marker to the map
        mMap.addMarker(markerOptions);

    }



    //Get user current location Address
    private String getAddress( LatLng latLng ) {
        // 1
        Geocoder geocoder = new Geocoder( this );
        String addressText = "";
        List<Address> addresses = null;
        Address address = null;
        try {
            // 2
            addresses = geocoder.getFromLocation( latLng.latitude, latLng.longitude, 1 );
            // 3
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    addressText += (i == 0)?address.getAddressLine(i):("\n" + address.getAddressLine(i));
                }
            }
        } catch (IOException e ) {
        }
        return addressText;
    }




}
