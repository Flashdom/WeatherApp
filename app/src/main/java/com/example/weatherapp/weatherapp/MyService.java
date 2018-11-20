package com.example.weatherapp.weatherapp;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import android.os.Process;

import com.example.weatherapp.weatherapp.Fragments.RequestFragment;
import com.example.weatherapp.weatherapp.CityData.City;
import com.example.weatherapp.weatherapp.NetworkRequesters.Network_constants;
import com.example.weatherapp.weatherapp.NetworkRequesters.OkHttpRequester;

public class MyService extends Service implements Network_constants {

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private  String city;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
           // getWeatherData();
            stopSelf(msg.arg1);
        }
    }


    public void getWeatherData()
    {
       City myCity = OkHttpRequester.getWeatherByCityOkHttp(getApplicationContext(), city);
           sendDataToUI(myCity);

    }


    public void sendDataToUI(City mycity)
    {

        Intent mIntent = new Intent(this,RequestFragment.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SER_KEY,mycity);

        mIntent.putExtras(mBundle);

        startActivity(mIntent);

    }





    @Override
    public void onCreate() {
        Log.d("Dto", "onCreate");
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Dto", "onStartCommand");
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        city=intent.getStringExtra("ChosenCity");
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Dto", "onBind");
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("Dto", "onDestroy");
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}
