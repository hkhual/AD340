package com.haukh.home;


import android.content.Context;
import android.content.SharedPreferences;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import android.content.Intent;
import android.preference.PreferenceManager;
import androidx.test.espresso.Espresso;

import android.widget.EditText;
//import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)

public class MainTest {

    private SharedPreferences.Editor preferencesEditor;
    private MainActivity mActivity = null;
    private EditText editText;
    private Intent intent;


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class, true,
            false);


    @Before
    public void setUp()  throws Exception{
        mActivity = mActivityTestRule.getActivity();

        intent = new Intent();

        Context appContext = getInstrumentation().getTargetContext();

        preferencesEditor = PreferenceManager.getDefaultSharedPreferences(appContext).edit();

        editText = (EditText) mActivity.findViewById(R.id.editText);


    }

    //Checking whether main Activity and edit text are null
    //return true if it is not null
    @Test
    public void ViewCreated(){
        assertNotNull(mActivity);
        assertNotNull(editText);

    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.haukh.home", appContext.getPackageName());

    }

    //To test stored data and retrieve data
@Test
    public void testStoredData(){

        String testString = "testSubject";

    preferencesEditor.putString("input", testString);
    preferencesEditor.commit();

    mRule.launchActivity(intent);

    }







}
