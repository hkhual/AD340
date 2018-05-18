package com.example.haukh.home;


//The helper class
//It is to hold data for other class to access
public class WebCamHelper {

    private String LiveCamLabel;
    private String LiveCamImage;
    private String LiveCamOwnership;



    public WebCamHelper(String LiveCamLabel, String LiveCamImage, String LiveCamOwnership){
        this.LiveCamLabel = LiveCamLabel;
        this.LiveCamImage = LiveCamImage;
        this.LiveCamOwnership = LiveCamOwnership;

    }

    public String getLiveCamOwnership(){
        return this.LiveCamOwnership;
    }
    public String getImage(){
        return this.LiveCamImage;
    }

    public String getLabel(){
        return this.LiveCamLabel;
    }



}
