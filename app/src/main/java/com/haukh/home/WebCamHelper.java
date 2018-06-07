package com.haukh.home;


//The helper class
//It is to hold data for other class to access
public class WebCamHelper {

    private String LiveCamID;  //get the ID tag
    private String LiveCamLabel; //Image Description
    private String LiveCamImage; //Image URL
    private String camType; //Image type etc (sdot)

    private double Latitude; // PointCoordinate: (0) Latitude
    private double Longitude; // PointCoordinate: (1) Longitude



    //Main Constructor method
    public WebCamHelper(){}

    public WebCamHelper(String camLabel, String camImage, String camOwnership) {

        this.LiveCamLabel = camLabel;
        this.LiveCamImage = camImage;
        this.camType = camOwnership;

        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }


//    //Overloading method 1
//    public WebCamHelper(double Latitude, double Longitude){
//        this.Latitude = Latitude;
//        this.Longitude = Longitude;
//    }
//
    //Overloading method 2
    public WebCamHelper(String LiveCamID, String LiveCamLabel, String LiveCamImage, String camType){

        this.LiveCamID = LiveCamID;
        this.LiveCamLabel = LiveCamLabel; //Description
        this.LiveCamImage = LiveCamImage; //Image
        this.camType = camType; //Type

    }


    //Get the Camera ID
    public String getLiveCamID(){ return this.LiveCamID; }
    //Get the type
    public String getType(){
        return this.camType;
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



    public void setImageUrl(String imageUrl) {
        if(getType().equals("sdot")) {
            this.LiveCamImage = "http://www.seattle.gov/trafficcams/images/" + imageUrl;
        } else {
            this.LiveCamImage = "http://images.wsdot.wa.gov/nw/" + imageUrl;
        }
    }


    public void setLiveCamID(String id){ this.LiveCamID = id; }

    public void setType(String type){ this.camType = type; }

    public void setLabel(String label){ this.LiveCamLabel = label; }

    public void setLatitude(double latitude){ this.Latitude = latitude; }

    public void setLongitude(double longitude){ this.Longitude = longitude; }



}
