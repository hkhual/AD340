package com.haukh.home;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.haukh.home.MainActivity;

public class InputValidationTest {


    // private final LiveCam mPopup;
    private MainActivity mMain = new MainActivity();


    //Function for validating logic for input text
    @Test
    public void entry_isValid() {
        Boolean result = mMain.isValidate(("john"));
        assertTrue(result);
    }


    //Function for validating logic for empty input
    @Test
    public void entry_isNotValid(){
        Boolean result = mMain.isValidate("");
        assertFalse(result);


    }



}
