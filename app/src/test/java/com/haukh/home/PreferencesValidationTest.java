package com.haukh.home;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import android.content.SharedPreferences;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PreferencesValidationTest {
    @Mock
    SharedPreferences sharedPreferences;
    @Mock
    SharedPreferences.Editor mEditor;


    private SharedPreferencesHelper sharedPreferencesHelper;

    private static final String TEST_TEXT = "text";


    @Before
    public void initMocks(){
        sharedPreferencesHelper = createMockSharedPreference();
    }


    @Test
    public void sharedPreferences_SaveAndReadEntry(){

        //Save the information to SharedPreferences
        boolean success = sharedPreferencesHelper.saveEntry(TEST_TEXT);

        assertThat("SharedPreferenceEntry.save... returns true",
                success, is(true));

        assertEquals(TEST_TEXT, sharedPreferencesHelper.getEntry());


    }





    private SharedPreferencesHelper createMockSharedPreference(){

        // Mocking reading the SharedPreferences as if mMockSharedPreferences was previously written
        // correctly.
        when(sharedPreferences.getString(eq("TEST_TEXT"), anyString()))
                .thenReturn(TEST_TEXT);

        // Mocking a successful commit.
        when(mEditor.commit()).thenReturn(true);

        // Return the MockEditor when requesting it.
        when(sharedPreferences.edit()).thenReturn(mEditor);

        return new SharedPreferencesHelper(sharedPreferences);


    }

}

