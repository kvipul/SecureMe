package cs654.secureme;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class F2 extends android.support.v4.app.Fragment {


    View fragmentRootView;
    Button sendTracking;
    Button stopTracking;
    public static Boolean sendMyTrack = false;
    public F2(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRootView =  inflater.inflate(R.layout.fragment_f2, container, false);
        sendTracking = (Button) fragmentRootView.findViewById(R.id.sendMyTracking);
        sendTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMyTrack = true;
                getActivity().startService(new Intent(getActivity(),SendMyTracking.class));
            }
        });
        stopTracking = (Button) fragmentRootView.findViewById(R.id.stopMyTracking);
        stopTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMyTrack = false;
            }
        });

//        setDest = (Button) fragmentRootView.findViewById(R.id.setDestination);
//        setDest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleElements=0;
//                Intent i = new Intent(getActivity(),F3.class);
//                startActivity(i);
//
//            }
//        });
//
//        openTracking  = (Button)fragmentRootView.findViewById(R.id.otOther);
//        openTracking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleElements=1;
////                Intent i = new Intent(getActivity(),F3.class);
////                startActivity(i);
//
//                Fragment newFragment = new F3();
//                // consider using Java coding conventions (upper first char class names!!!)
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//                // Replace whatever is in the fragment_container view with this fragment,
//                // and add the transaction to the back stack
//                transaction.replace(R.id.fragment_container, newFragment);
//                transaction.addToBackStack(null);
//
//                // Commit the transaction
//                transaction.commit();
//            }
//        });

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
// * {@link F2.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link F2#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class F2 extends android.support.v4.app.Fragment {
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
//     * @return A new instance of fragment F2.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static F2 newInstance(String param1, String param2) {
//        F2 fragment = new F2();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public F2() {
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
//        return inflater.inflate(R.layout.fragment_f2, container, false);
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
