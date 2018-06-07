package com.haukh.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class CustomInfoWindowMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowMap(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;

    }

    @Override
    public View getInfoWindow(Marker marker) {

        @SuppressLint("InflateParams") View view = ((Activity) context).getLayoutInflater().inflate(R.layout.activity_maps_customs_window, null);

        TextView camName = view.findViewById(R.id.camName);
        ImageView camPic = view.findViewById(R.id.live_cam_image);
        TextView camDes = view.findViewById(R.id.live_cam_description);

        camName.setText(marker.getTitle());
        camDes.setText(marker.getSnippet());

        WebCamHelper camData = (WebCamHelper) marker.getTag();
        String imageURL = camData.getImage();
        String camDataLabel = camData.getLabel();

        String camDescription = camData.getLabel();
        String camOwnership = camData.getType();


        Picasso.with(view.getContext()).load(imageURL).error(R.mipmap.ic_launcher_round).resize(480, 400).into(camPic,  new MarkerCallback(marker));

        return view;
    }

    // Picasso interface called Callback
    class MarkerCallback implements Callback {
        Marker marker = null;

        MarkerCallback(Marker marker) {
            this.marker = marker;
        }

        @Override
        public void onError() {
        }

        @Override
        public void onSuccess() {
            if (marker == null) {
                return;
            }

            if (!marker.isInfoWindowShown()) {
                return;
            }

            // refresh if InfoWindowData activity is showing
            marker.hideInfoWindow(); // Need to hide first or else error is thrown
            marker.showInfoWindow();
        }
    }


}