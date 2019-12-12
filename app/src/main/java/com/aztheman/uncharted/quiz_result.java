package com.aztheman.uncharted;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class quiz_result extends Fragment {

    public quiz_result() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz_result, container, false);
        TextView result = (TextView) view.findViewById(R.id.tvResult);
        Button btnresult = (Button) view.findViewById(R.id.btnresult);

        Bundle bundle = getArguments();
        if(bundle != null){
            result.setText("You got " + bundle.getString("country"));
        }

        btnresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                travelpackage travel = new travelpackage();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.screen_area,travel).addToBackStack(null).commit();
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
