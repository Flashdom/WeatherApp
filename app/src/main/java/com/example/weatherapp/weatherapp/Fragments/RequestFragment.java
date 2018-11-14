package com.example.weatherapp.weatherapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.weatherapp.CityData.City;
import com.example.weatherapp.weatherapp.NetworkRequesters.Network_constants;
import com.example.weatherapp.weatherapp.NetworkRequesters.OkHttpRequester;
import com.example.weatherapp.weatherapp.R;


public class RequestFragment extends Fragment implements Network_constants {



    private OnFragmentInteractionListener mListener;
    private TextView temperature;
    private ImageView weather_pic;
    private EditText myCity;
    private Button button_getInfo;
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
        temperature=root.findViewById(R.id.temperature);
        weather_pic=root.findViewById(R.id.weather_pic);
        myCity=root.findViewById(R.id.city_name);
        button_getInfo=root.findViewById(R.id.button_send);
        button_getInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (String.valueOf(myCity.getText())!=null) {
                            final City city;
                            //getActivity().startService(new Intent(getActivity().getApplicationContext(), MyService.class).putExtra("ChosenCity", myCity.getText()));
                           // city = (City) getActivity().getIntent().getSerializableExtra(SER_KEY);
                            city = OkHttpRequester.getWeatherByCityOkHttp(getActivity().getApplicationContext(), String.valueOf(myCity.getText()));
                            if (city==null) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.place_not_found),
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                                else
                                {
                                    handler.post(new Runnable() {
                                                     @Override
                                                     public void run() {
                                                         renderUi(city);
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

    /**
     * A simple {@link Fragment} subclass.
     * Activities that contain this fragment must implement the
     * {@link OnFragmentInteractionListener} interface
     * to handle interaction events.
     * Use the {@link MyAnotherFragment#newInstance} factory method to
     * create an instance of this fragment.
     */
    public static class MyAnotherFragment extends Fragment {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;

        private OnFragmentInteractionListener mListener;

        public MyAnotherFragment() {
            // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyAnotherFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static MyAnotherFragment newInstance(String param1, String param2) {
            MyAnotherFragment fragment = new MyAnotherFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View root=inflater.inflate(R.layout.fragment_my_another_fragment, container, false);
            TextView textView;
            textView=root.findViewById(R.id.my_another_fragment_text);
            textView.setText("Some another text");
            return root;
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

    public static class MyFragment extends Fragment {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;

        private OnFragmentInteractionListener mListener;





        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View root=inflater.inflate(R.layout.my_fragment_layout, container, false);
            TextView textView;
            textView=root.findViewById(R.id.fragmenttext);
            textView.setText("Some text");

            return root;
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
}
