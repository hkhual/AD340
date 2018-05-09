package com.example.haukh.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Popup extends AppCompatActivity {


    public final String myPreference = "my";
    public Context content;

    //Button popup_enter;


    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);


    }


    public Popup(Context context) {
        this.content = context;
    }


    public void storeSharedPreference(String text){

        //Set up Shared Preference
       preferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = preferences.edit();

       //Set Values
       String key = "Data";
        editor.putString(key,text);
        editor.apply();

    }




    public String getSharedPreference(){

        EditText inputText = (EditText)findViewById(R.id.editText);

        //Get Shared Preference
        preferences = getSharedPreferences(
                myPreference, Context.MODE_PRIVATE);
        String key = "Data";

        String userInput = preferences.getString(key,"");


        return userInput;
    }

}