package com.example.weatherapp.weatherapp.CityData;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class City implements Serializable {

    @SerializedName("coord")
    public Coordinates coordinates;
    public List<Weather> weather=new ArrayList<Weather>();
    public String base;
    public MainInfo main;
    public long visibility;
    public Wind wind;
    public Clouds clouds;
    public long dt;
    @SerializedName("sys")
    public System system;
    public long id;
    public String name;
    public int cod;


}