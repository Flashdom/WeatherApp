package com.example.weatherapp.weatherapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class RequestFragment extends Fragment {



    private OnFragmentInteractionListener mListener;
    private TextView textView;
    private String LOG_TAG = "ABC";
    private final Handler handler = new Handler();
    private Button button;
    private String city;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment RequestFragment.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_request, container, false);
        city="London";
        textView=root.findViewById(R.id.tv_temperature);
        button=root.findViewById(R.id.button_city);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChangeCity(city);
            }
        });

        return root;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }



    }


    //Обновление/загрузка погодных данных
    private void updateWeatherData(final String city) {
        //new MyAsT().execute(city);
        new Thread() {//Отдельный поток для получения новых данных в фоне
            public void run() {
                final City model = OKHttpRequester.getWeatherByCityOkHttp(getActivity().getApplicationContext(), city);
                // Вызов методов напрямую может вызвать runtime error
                // Мы не можем напрямую обновить UI, поэтому используем handler, чтобы обновить интерфейс в главном потоке.
                if (model == null) {
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.notfound),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            renderWeather(model);
                        }
                    });
                }
            }
        }.start();
    }

    //Обработка загруженных данных и обновление UI
    private void renderWeather(City model) {
        try {
            textView.setText(String.format("%.2f", model.temp) + " ℃");

            String description = "";
            long id = 0;

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(model.dt * 1000));
        } catch (Exception e) {
            Log.d(LOG_TAG, "One or more fields not found in the JSON data");//FIXME Обработка ошибки
        }
    }

    public void onChangeCity(String city) {
        city="London";
        updateWeatherData(city);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
