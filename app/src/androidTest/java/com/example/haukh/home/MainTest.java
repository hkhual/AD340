package com.example.haukh.home;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.test.espresso.Espresso;
import android.app.Activity;
import android.widget.EditText;
//import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;



@RunWith(AndroidJUnit4.class)

public class MainTest {

    private SharedPreferences.Editor preferencesEditor;

    private MainActivity mActivity = null;


    @Rule
  // public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.haukh.home", appContext.getPackageName());




    }


//    @Before
//    public void setUp() throws Exception {
//        mActivity = mActivityTestRule.getActivity();
//
//        intent = new Intent();
//        Context context = getInstrumentation().getTargetContext();
//
//        // create a SharedPreferences editor
//        preferencesEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
//
//        editTextBoxEntry = (EditText) mActivity.findViewById(com.greyarea.grey.greyareaad340.R.id.editText1);
//
//    }







}
