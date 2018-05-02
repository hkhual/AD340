package com.example.haukh.home;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Movie");

        Toolbar toolbar = (Toolbar) findViewById(R.id.movie_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Movie List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        inputDescrption();

    }


    public void inputDescrption(){
        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data
        String movieTitle = bundle.getString("movieTitle");

        String moviesDescription = bundle.getString("movieDescription");

        TextView textTitle = (TextView) findViewById(R.id.movieListTitle);


        TextView textDescription = (TextView) findViewById(R.id.movie_descrption);

        textTitle.setText(movieTitle);

       textDescription.setText(moviesDescription);



    }

}
