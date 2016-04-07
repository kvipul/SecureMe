package cs654.secureme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class F1 extends android.support.v4.app.Fragment{

    View fragmentRootView;
    Button b1;
    Button saveLocation;
    Button stopSavingLocation;
    public static Boolean saveStopLocation = true;
    Button flash;
    int toggleFlash=0;
    static Camera cam = null;
    public static TextView txt;
    public static TextView balance;

    public F1(){

    }


//    private Activity mActivity;
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        mActivity = activity;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentRootView =  inflater.inflate(R.layout.fragment_f1, container, false);
        b1=(Button)fragmentRootView.findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MapsActivity.class));
            }
        });
        saveLocation = (Button) fragmentRootView.findViewById(R.id.saveMyLocation);
        saveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStopLocation = true;
                getActivity().startService(new Intent(getActivity(), SaveLocationOnDemand.class));
            }
        });
        stopSavingLocation = (Button) fragmentRootView.findViewById(R.id.stopSavingMyLocation);
        stopSavingLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStopLocation = false;
            }
        });

        flash = (Button) fragmentRootView.findViewById(R.id.flashLight);
        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleFlash==0) {
                    cam = Camera.open();
                    Camera.Parameters p = cam.getParameters();
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    cam.setParameters(p);
                    cam.startPreview();
//                    cam.release();
                    toggleFlash=1;
                    flash.setText("Flashlight OFF");
                }
                else{
                    cam.release();
                    cam = Camera.open();
                    Camera.Parameters p = cam.getParameters();
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    cam.setParameters(p);
                    cam.stopPreview();
                    cam.release();
                    toggleFlash=0;
                    flash.setText("FlashLight ON");
                }
            }
        });

        txt=(TextView)fragmentRootView.findViewById(R.id.text);
        balance=(TextView)fragmentRootView.findViewById(R.id.balanceDisp);
        return fragmentRootView;
    }

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
// * {@link F1.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link F1#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class F1 extends android.support.v4.app.Fragment {
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
//     * @return A new instance of fragment F1.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static F1 newInstance(String param1, String param2) {
//        F1 fragment = new F1();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public F1() {
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
//        return inflater.inflate(R.layout.fragment_f1, container, false);
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
