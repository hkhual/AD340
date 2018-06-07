package com.haukh.home;

import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    //Keys for saving values in SharedPreferences
    final static String KEY_ENTRY = "TEST_TEXT";


    private final SharedPreferences mSharedPreferences;

    /**
     * Constructor with dependency injection.
     *
     * @param sharedPreferences The {@link SharedPreferences} that will be used in this DAO.
     */

    public SharedPreferencesHelper(SharedPreferences sharedPreferences){

        mSharedPreferences = sharedPreferences;


    }




    /**
     * Saves the given string to {@link SharedPreferences}.
     *
     * @param message contains string entry to save to {@link SharedPreferences}.
     * @return {@code true} if writing to {@link SharedPreferences} succeeded. {@code false}
     *         otherwise.
     */

    public boolean saveEntry(String message){

        //Start a SharedPreferences transaction
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(KEY_ENTRY, message);

        //Commit changes to SharedPreferences
        //Reutn sucess or failure result.
        return mEditor.commit();

    }

    public String getEntry(){

        return mSharedPreferences.getString(KEY_ENTRY, "");
    }

}
