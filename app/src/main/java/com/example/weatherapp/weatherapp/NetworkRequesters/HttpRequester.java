package com.example.weatherapp.weatherapp.NetworkRequesters;

import android.content.Context;

import com.example.weatherapp.weatherapp.CityData.City;
import com.example.weatherapp.weatherapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequester implements Network_constants {


    public static City getWeatherByCity(Context context, String city) {
        try {
            URL url = new URL(String.format(request, city));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty(KEY, context.getString(R.string.request_token));

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tempVariable;
            while ((tempVariable = reader.readLine()) != null) {
                rawData.append(tempVariable).append(NEXT_LINE);
            }
            reader.close();

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            City myCity = gson.fromJson(rawData.toString(), City.class);

            if (myCity.cod != SUCCESS) {
                return null;
            }
            return myCity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
