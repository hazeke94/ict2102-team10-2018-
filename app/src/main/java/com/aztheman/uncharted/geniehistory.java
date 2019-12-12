package com.aztheman.uncharted;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class geniehistory extends Fragment {
    ArrayList<String> history = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_geniehistory, container, false);
        TextView tvgenie = view.findViewById(R.id.tvGenie);
        ListView lvgenie = view.findViewById(R.id.lvGenie);

        final Activity activity = (HomeActivity) getActivity();
        history = ((HomeActivity) activity).genielist;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, history);
        lvgenie.setAdapter(adapter);

        lvgenie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //bring to travel packages
                travelpackage quiz_results = new travelpackage();
                Bundle bundle = new Bundle();
                bundle.putString("country",history.get(position));
                quiz_results.setArguments(bundle);
                FragmentManager manager = getFragmentManager();

                manager.beginTransaction().replace(R.id.screen_area,quiz_results).addToBackStack("home").commit();
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
