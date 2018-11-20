package com.example.weatherapp.weatherapp.CityData;

import com.google.gson.annotations.SerializedName;

public class Coordinates {
    @SerializedName("lon")
    public double longtitude;
    @SerializedName("lat")
    public double latitude;
}
