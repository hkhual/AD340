package com.example.haukh.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.haukh.home";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //Called when  the user taps the Send button
    public void sendMessage(View view){

        //Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        EditText editText = (EditText)findViewById(R.id.editText);

        String message = editText.getText().toString();

        intent.putExtra(EXTRA_MESSAGE, message);

         startActivity(intent);


    }


    //run this method when the user click a button
    //This method will open another activity for movie informations
    public void movieInfo(View view){

           Intent intent = new Intent(this, Movies.class);
           startActivity(intent);

    }

    public void ButtonClick(View view){

        Button toastButton = this.findViewById(R.id.button3);

        //---------------------------
        //Display in short period of time
        Toast.makeText(getApplicationContext(), "You clicked a button 2",
                Toast.LENGTH_SHORT).show();

        //--------------------------------
        //Display in long period of time
        //Toast.makeText(getApplicationContext(), "You clicked button3",
               // Toast.LENGTH_LONG).show();
    }



    public void ButtonClick3(View view){

        //Display in short period of time
        //Toast
        Toast.makeText(getApplicationContext(), "You clicked a button 3",
                Toast.LENGTH_SHORT).show();

    }


    public void buttonClick4(View view){

        //Display a toast in short period of time
        Toast.makeText(getApplicationContext(), "You clicked a button 4",
                Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onStart(){
        super.onStart();

        Log.d(EXTRA_MESSAGE, "onStart started");
    }

    @Override
    protected void onPause(){
        super.onPause();

        Log.d(EXTRA_MESSAGE, "onPause started");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(EXTRA_MESSAGE, "onStop started");

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(EXTRA_MESSAGE, "onDestroy started");
    }


    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(EXTRA_MESSAGE, "onRestart started");
    }


}
