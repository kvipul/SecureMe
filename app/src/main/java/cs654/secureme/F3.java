package cs654.secureme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class F3 extends android.support.v4.app.Fragment {

    MapView mapView;
    private GoogleMap mMap;
    Button receiveTrack;
    View fragmentRootView;
    Marker marker;
    SharedPreferences sharedPreferences;
    String yourMobile = "11", helpMobile;
    String url1;
    float f1, f2;

    int counter = 0;
    float latCollection[];
    float longCollection[];

    public F3() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRootView = inflater.inflate(R.layout.fragment_f3, container, false);
        latCollection = new float[10000];
        longCollection = new float[10000];

        receiveTrack = (Button) fragmentRootView.findViewById(R.id.receiveTracking);
        receiveTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("details", Context.MODE_PRIVATE);
                yourMobile = sharedPreferences.getString("yourMobile", "");
                helpMobile = sharedPreferences.getString("helpMobile", "");
                System.out.println("mobile " + yourMobile);
                System.out.println("sunil123 " + helpMobile);
                url1 = "http://172.20.176.195/cs654/project/tracking.php/rec_track/" + yourMobile + "/" + helpMobile;


                GetLocation getLocation = new GetLocation();
                getLocation.execute(url1);

            }
        });

        mapView = (MapView) fragmentRootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMap = mapView.getMap();
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
//        marker = mMap.addMarker(new MarkerOptions().position(new LatLng(25.5122135, 80.2371741)).title("hi"));


        return fragmentRootView;
    }


    private class GetLocation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            while (true) {
                for (String url1 : urls) {
                    try {
                        URL url = new URL(url1);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        sb = new StringBuilder();
                        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String json = sb.toString();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String l1 = jsonObject.getString("lat");
                    String l2 = jsonObject.getString("long");
                    System.out.println("location sunil" + l1 + l2);
                    latCollection[counter] = f1 = Float.parseFloat(l1);
                    longCollection[counter] = f2 = Float.parseFloat(l2);
//                receiveTrack.performClick();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latCollection[counter], longCollection[counter])).title("" + counter));
                            ++counter;

                        }
                    });
                    Thread.sleep(5000);

                } catch (Exception e) {
                }
            }
//            return sb.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.print("hi this" + result);
        }
    }

//    @Override
//    public void onStop() {
//        counter = 0;
//    }
}
//package cs654.secureme;
//
//import android.app.Activity;
//import android.net.Uri;
//import android.os.Bundle;
//import android.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link F3.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link F3#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class F3 extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment F3.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static F3 newInstance(String param1, String param2) {
//        F3 fragment = new F3();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public F3() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_f3, container, false);
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }
//
//}
