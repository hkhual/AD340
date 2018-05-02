package com.example.haukh.home;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Movie extends AppCompatActivity {


    public TextView description;
    public ImageView image;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_item);

        inputDescrption();

    }





    public void inputDescrption(){

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data
        String movieTitle = bundle.getString("movieTitle");

        String moviesDescription = bundle.getString("movieDescription");


        TextView textDescription = (TextView) findViewById(R.id.movie_descrption);

        TextView textTitle = (TextView) findViewById(R.id.movieListTitle);

       textDescription.setText(moviesDescription);

       textTitle.setText(movieTitle);


    }


}
