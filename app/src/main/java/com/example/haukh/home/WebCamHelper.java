package com.example.haukh.home;


//The helper class
//It is to hold data for other class to access
public class WebCamHelper {

    private String LiveCamID;  //get the ID tag
    private String LiveCamLabel; //Image Description
    private String LiveCamImage; //Image URL
    private String LiveCamOwnership; //Image type etc (sdot)

    private double Latitude; // PointCoordinate: (0) Latitude
    private double Longitude; // PointCoordinate: (1) Longitude



    //Main Constructor method
    public WebCamHelper(){}

    //Overloading method 1
    public WebCamHelper(double Latitude, double Longitude){
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    //Overloading method 2
    public WebCamHelper(String LiveCamID, String LiveCamLabel, String LiveCamImage, String LiveCamOwnership){

        this.LiveCamID = LiveCamID;
        this.LiveCamLabel = LiveCamLabel; //Description
        this.LiveCamImage = LiveCamImage; //Image
        this.LiveCamOwnership = LiveCamOwnership; //Type

    }


    //Get the Camera ID
    public String getLiveCamID(){ return this.LiveCamID; }

    //Get the type
    public String getLiveCamOwnership(){
        return this.LiveCamOwnership;
    }

    //Get the Image URL
    public String getImage(){
        return this.LiveCamImage;
    }

    //Get the Description
    public String getLabel(){
        return this.LiveCamLabel;
    }


    //Get the Latitude
    public double getLatitude(){ return this.Latitude; }

    //Get the Longitude
    public double getLongitude(){ return this.Longitude; }




}
