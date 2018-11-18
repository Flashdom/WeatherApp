package com.example.weatherapp.weatherapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.weatherapp.CityData.Weather;
import com.example.weatherapp.weatherapp.NetworkRequesters.RetrofitInterface;
import com.example.weatherapp.weatherapp.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RequestFragmentviaRetrofit.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RequestFragmentviaRetrofit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestFragmentviaRetrofit extends Fragment {


        private TextView textView;
        private ImageView imageView;
        private EditText editText;
        private Button button;
        RetrofitInterface weather;

    private OnFragmentInteractionListener mListener;



    public static RequestFragmentviaRetrofit newInstance(String param1, String param2) {
        RequestFragmentviaRetrofit fragment = new RequestFragmentviaRetrofit();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_request_fragmentvia_retrofit, container, false);
        textView=root.findViewById(R.id.tv_temperature);
        editText=root.findViewById(R.id.city_name);
        imageView=root.findViewById(R.id.image_pic);
        initRetorfit();
        button=root.findViewById(R.id.send_request);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          requestRetrofit(String.valueOf(editText.getText()), getString(R.string.request_token));

                                      }
                                  }
        );

        return root;
    }


    private void initRetorfit(){
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
//Базовая часть адреса
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON в объекты
                .build();
//Создаем объект, при помощи которого будем выполнять запросы
         weather = retrofit.create(RetrofitInterface.class);
    }
    private void requestRetrofit(String city, String keyApi){
        weather.loadWeather(city, keyApi)
                .enqueue(new Callback<Weather>() {
                    @Override
                    public void onResponse(Call<Weather> call, Response<Weather> response) {
                        if (response.body() != null)

                            textView.setText(response.body().main);
                    }

                    @Override
                    public void onFailure(Call<Weather> call, Throwable t) {
                        textView.setText("Error");
                    }


                });
        Picasso
                .with(getActivity().getApplicationContext())
                .load("http://avotarov.net/picture/avatarki/23/kartinki/13-5.jpg")
                .into(imageView);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
