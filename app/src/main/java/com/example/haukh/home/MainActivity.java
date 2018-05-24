package com.example.haukh.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String EXTRA_MESSAGE = "com.example.haukh.home";


    public final String myPreference = "my";
    SharedPreferences preferences;
     public EditText textField;
    public Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this.setTitle("Application");

        //Display the toolbar by calling its id name
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);



           //Set up Shared Preference
           preferences = getSharedPreferences(getString(R.string.saved_Message), Context.MODE_PRIVATE);
           textField = (EditText) findViewById(R.id.editText);

           String message = "";
           String savedText = preferences.getString(getString(R.string.type_Message), message);
           textField.setText(savedText);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }



    //Called when  the user taps the Send button
    public void sendMessage(View view){

        textField = (EditText)findViewById(R.id.editText);
        String message = textField.getText().toString();

        if(isValidate(message)) {
            //Set up Shared Preference
            context = getApplication();
            preferences = getSharedPreferences(getString(R.string.saved_Message), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            // String key = "Data";
            editor.putString(getString(R.string.type_Message), message);
            editor.apply();


            //Do something in response to button
            Intent intent = new Intent(this, DisplayMessageActivity.class);

            intent.putExtra(EXTRA_MESSAGE, message);

            startActivity(intent);

        }   else{
            Toast.makeText(getApplicationContext(), "Is not a valid input", Toast.LENGTH_SHORT).show();
            }

    }


    public boolean isValidate(String str){
            return !str.isEmpty();

    }


    //run this method when the user click a button
    //This method will open another activity for movie informations
    public void movieInfo(View view){
           Intent intent = new Intent(this, Movies.class);
           startActivity(intent);

    }


    public void aboutAppInfo(){
        Intent intent = new Intent(this, AboutApp.class);
        startActivity(intent);
    }



    public void ButtonClick(View view){

        Button toastButton = this.findViewById(R.id.toast1);
        //---------------------------
        //Display in short period of time
        Toast.makeText(getApplicationContext(), "You clicked a Toast 1",
                Toast.LENGTH_SHORT).show();
    }



    public void ButtonClick3(View view){

        //Display in short period of time
        //Toast
        Toast.makeText(getApplicationContext(), "You clicked a Toast 2",
                Toast.LENGTH_SHORT).show();

    }


    public void buttonClick4(View view){

        //Display a toast in short period of time
        Toast.makeText(getApplicationContext(), "You clicked a Toast 3",
                Toast.LENGTH_SHORT).show();

    }


    //Run the liveCam class when the
    // user click the livecam button
    public void liveCamera(View view){

        //Display a live camera layout
        Intent intent = new Intent(this, WebCam.class);
        startActivity(intent);

    }


    /*public void locationMap(View v){

        Intent intent = new Intent(this, LocationMap.class);
        startActivity(intent);

    }   */


    public void locationMap(View v){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    //------------------------------------------------------
    @Override
    protected void onStart(){
        Log.d(EXTRA_MESSAGE, "onStart started");
        super.onStart();

    }

    @Override
    protected void onPause(){
        Log.d(EXTRA_MESSAGE, "onPause started");
        super.onPause();

    }

    @Override
    protected void onStop(){
        Log.d(EXTRA_MESSAGE, "onStop started");
        super.onStop();

    }

    @Override
    protected void onDestroy(){
        Log.d(EXTRA_MESSAGE, "onDestroy started");
        super.onDestroy();

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(EXTRA_MESSAGE, "onRestart started");
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override //Menu method
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
            aboutAppInfo();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Handle navigation View item clicks here.
        int id = item.getItemId();

        if(id==R.id.aboutMe){
            //Handle the about me page
          aboutAppInfo();

        }else
        //Run Movies method when the user clicked it
         if(id==R.id.Movies){
            //Handle the movie
             Intent intent = new Intent(this, Movies.class);
             startActivity(intent);
        }

        else if(id==R.id.myLiveCam){
            Intent cam = new Intent(this, WebCam.class);
            startActivity(cam);
         }
         else if(id==R.id.myLocationMap){
            Intent map = new Intent(this, MapsActivity.class);
            startActivity(map);
         }


        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
