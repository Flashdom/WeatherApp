package com.example.weatherapp.weatherapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.weatherapp.CityData.City;
import com.example.weatherapp.weatherapp.Database.Note;
import com.example.weatherapp.weatherapp.Database.NoteManager;
import com.example.weatherapp.weatherapp.NetworkRequesters.Network_constants;
import com.example.weatherapp.weatherapp.NetworkRequesters.OkHttpRequester;
import com.example.weatherapp.weatherapp.R;

import java.util.List;


public class RequestFragment extends Fragment implements Network_constants {



    private OnFragmentInteractionListener mListener;
    private TextView temperature;
    private EditText myCity;
    private Button button_getInfo;
    List<Note> elements;
    ArrayAdapter<Note> adapter;
    NoteManager noteManager;
    Handler handler = new Handler();


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
        noteManager= new NoteManager(getActivity().getApplicationContext());
        //elements=noteManager.getAllNotes();
        temperature=root.findViewById(R.id.temperature);
        myCity=root.findViewById(R.id.city_name);

        if (noteManager.getNote((getString(R.string.ChosenCity)))!=null)
            myCity.setText(noteManager.getNote(getString(R.string.ChosenCity)));
        else
            myCity.setText(" ");
        button_getInfo=root.findViewById(R.id.button_send);
        button_getInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (String.valueOf(myCity.getText())!=null) {
                            final City city;
                            if (noteManager.getNote((getString(R.string.ChosenCity)))==null)
                            noteManager.addNote(getString(R.string.ChosenCity), String.valueOf(myCity.getText()));
                            //getActivity().startService(new Intent(getActivity().getApplicationContext(), MyService.class).putExtra("ChosenCity", myCity.getText()));
                           // city = (City) getActivity().getIntent().getSerializableExtra(SER_KEY);
                            city = OkHttpRequester.getWeatherByCityOkHttp(getActivity().getApplicationContext(), String.valueOf(myCity.getText()));
                            if (city==null) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.place_not_found), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                                else
                                {
                                    handler.post(new Runnable() {
                                                     @Override
                                                     public void run() { renderUi(city);
                                                     }
                                                 });
                                }
                        }

                    }
                }).start();
            }
        });
        return root;
    }

    public void renderUi(City mycity)
    {
        temperature.setText(String.valueOf(mycity.main.temp));
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




