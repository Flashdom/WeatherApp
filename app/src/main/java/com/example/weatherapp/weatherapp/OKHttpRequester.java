package com.example.weatherapp.weatherapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Вспомогательный класс для работы с API openweathermap.org и скачивания нужных
 * данных
 */

public class OKHttpRequester {

    private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String KEY = "x-api-key";
    private static final String RESPONSE = "cod";
    private static final String NEW_LINE = "\n";
    private static final int ALL_GOOD = 200;

    //Возвращает объект JSON или null
    public static City getWeatherByCity(Context context, String city) {
        try {
            //Используем API (Application programming interface) openweathermap
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty(KEY, context.getString(R.string.api));

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tempVariable;
            while ((tempVariable = reader.readLine()) != null) {
                rawData.append(tempVariable).append(NEW_LINE);
            }
            reader.close();

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            City model = gson.fromJson(rawData.toString(), City.class);

            if (model.cod != ALL_GOOD) {
                return null;
            }
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            return null; //FIXME Обработка ошибки
        }
    }

    //Возвращает объект JSON или null
    public static City getWeatherByCityOkHttp(Context context, String city) {
        try {
            //OkHttpClient client = new OkHttpClient.Builder().build();
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse(String.format(OPEN_WEATHER_MAP_API, city)).newBuilder();
            String url = urlBuilder.build().toString();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(KEY, context.getString(R.string.api))
                    .build();

            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
            if (networkinfo != null && networkinfo.isConnected()) {
                Response response = client.newCall(request).execute();
                String answer = response.body().string();
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                City model = gson.fromJson(answer, City.class);

                if (model.cod != ALL_GOOD) {
                    return null;
                }

                return  model;
            }
            else {
                Toast.makeText(context, "Подключите интернет", Toast.LENGTH_SHORT).show();
                return  null;
            }
        } catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }
}