package com.example.haukh.home;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        String myMovieYear = bundle.getString("movieYear");
        String myMovieDirector = bundle.getString("movieDirector");
        String moviesDescription = bundle.getString("movieDescription");


        TextView textTitle = (TextView) findViewById(R.id.movieListTitle);
        TextView textYear = (TextView) findViewById(R.id.movie_year);
        TextView textDirector = (TextView) findViewById(R.id.movie_director);
        TextView textDescription = (TextView) findViewById(R.id.movie_descrption);



        textTitle.setText(movieTitle);
        textYear.setText(myMovieYear);
        textDirector.setText(myMovieDirector);
       textDescription.setText(moviesDescription);



    }


    @Override
    //Menu method
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
