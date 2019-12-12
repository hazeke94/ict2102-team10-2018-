package com.aztheman.uncharted;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GenieFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_genie, container, false);
        Button destBtn = (Button) view.findViewById(R.id.btn_Destination);
        Button resultsBtn = (Button) view.findViewById(R.id.btn_Results);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/montserrat-medium.otf");
        destBtn.setTypeface(font);
        resultsBtn.setTypeface(font);

        destBtn.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            String title="Map";
            @Override
            public void onClick(View view) {
                Log.d("myTag", "Button is presssed");

                fragment = new Filter();

                FragmentManager manager = getFragmentManager();

                manager.beginTransaction().replace(R.id.screen_area,fragment).addToBackStack("home").commit();

            }

        });

        resultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new geniehistory();

                FragmentManager manager = getFragmentManager();

                manager.beginTransaction().replace(R.id.screen_area,fragment).addToBackStack(null).commit();
            }
        });


        return view;


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
