package com.example.weatherapp.weatherapp.NetworkRequesters;

import com.example.weatherapp.weatherapp.CityData.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RetrofitInterface {



    @GET("data/2.5/weather")
    Call<Weather> loadWeather(@Query("q") String cityCountry, @Query("appid") String keyApi);





}
