package com.example.haukh.home;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.haukh.home.MainActivity;

public class ActivityTest {


    // private final LiveCam mPopup;
    private MainActivity mMain = new MainActivity();


    @Test
    public void entry_isValid() {
        Boolean result = mMain.isValidate(("john"));
        assertTrue(result);
    }

    @Test
    public void entry_isNotValid(){
        Boolean result = mMain.isValidate("");
        assertFalse(result);


    }



}
