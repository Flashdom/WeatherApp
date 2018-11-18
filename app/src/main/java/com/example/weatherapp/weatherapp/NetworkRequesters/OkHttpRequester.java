package com.example.weatherapp.weatherapp.NetworkRequesters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.weatherapp.weatherapp.CityData.City;
import com.example.weatherapp.weatherapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpRequester implements Network_constants
{


    public static City getWeatherByCityOkHttp(Context context, String city) {
        try {
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse(String.format(request, city)).newBuilder();
            String url = urlBuilder.build().toString();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(KEY, context.getString(R.string.request_token))
                    .build();

            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
            if (networkinfo != null && networkinfo.isConnected()) {
                Response response = client.newCall(request).execute();
                String answer = response.body().string();
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                City myCity = gson.fromJson(answer, City.class);

                if (myCity.code != SUCCESS) {
                    return null;
                }
                //Log.d("test", myCity.descripti);


                return myCity;
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
