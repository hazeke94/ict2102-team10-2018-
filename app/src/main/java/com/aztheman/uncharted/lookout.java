package com.aztheman.uncharted;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class lookout extends Fragment {
    private ArrayList<String> mFoodNames = new ArrayList<>();
    private ArrayList<Integer> mFoodImageUrls = new ArrayList<>();
    private ArrayList<String> mLandmarkNames = new ArrayList<>();
    private ArrayList<Integer> mLandmarkImageUrls = new ArrayList<>();
    private ArrayList<String> mActivityNames = new ArrayList<>();
    private ArrayList<Integer> mActivityImageUrls = new ArrayList<>();
    RecyclerView recyclerFood,recyclerLandmark,recyclerActivity;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lookout, container, false);

        getImages();
        LinearLayoutManager layoutManagerFood = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManagerLandmark = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManagerActivity = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        //initialise recycler views
        recyclerFood = view.findViewById(R.id.RFoodView);
        recyclerLandmark = view.findViewById(R.id.RLandmark);
        recyclerActivity = view.findViewById(R.id.RActivityView);

        //Set Respective layoutManager
        recyclerFood.setLayoutManager(layoutManagerFood);
        recyclerLandmark.setLayoutManager(layoutManagerLandmark);
        recyclerActivity.setLayoutManager(layoutManagerActivity);

        //set Respective adapter
        RecyclerViewAdapter foodAdapter = new RecyclerViewAdapter(getContext(),mFoodImageUrls, mFoodNames);
        RecyclerViewAdapter landmarkAdapter = new RecyclerViewAdapter(getContext(),mLandmarkImageUrls, mLandmarkNames);
        RecyclerViewAdapter activityAdapter = new RecyclerViewAdapter(getContext(),mActivityImageUrls, mActivityNames);
        recyclerFood.setAdapter(foodAdapter);
        recyclerLandmark.setAdapter(landmarkAdapter);
        recyclerActivity.setAdapter(activityAdapter);

        //avoid displaying half elements
        SnapHelper startSnapHelper1 = new PagerSnapHelper();
        SnapHelper startSnapHelper2 = new PagerSnapHelper();
        SnapHelper startSnapHelper3 = new PagerSnapHelper();

        //set snaphelp into recyclers
        startSnapHelper1.attachToRecyclerView(recyclerFood);
        startSnapHelper2.attachToRecyclerView(recyclerLandmark);
        startSnapHelper3.attachToRecyclerView(recyclerActivity);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/montserrat-bold.otf");

        TextView foodTitle = (TextView) view.findViewById(R.id.tvFood);
        TextView landmarkTitle = (TextView) view.findViewById(R.id.tvLandmark);
        TextView activityTitle = (TextView) view.findViewById(R.id.tvActivity);

        foodTitle.setTypeface(font);
        landmarkTitle.setTypeface(font);
        activityTitle.setTypeface(font);

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

    private void getImages(){
        System.out.println("Printing stuff");

        mFoodImageUrls.add(R.drawable.yakitori);
        mFoodNames.add("Yakitori");

        mFoodImageUrls.add(R.drawable.ramen);
        mFoodNames.add("Ramen");

        mFoodImageUrls.add(R.drawable.okonomiyaki);
        mFoodNames.add("Okonomiyaki");

        mLandmarkImageUrls.add(R.drawable.shinsaibashi);
        mLandmarkNames.add("Shinsaibashi");

        mLandmarkImageUrls.add(R.drawable.tennoji);
        mLandmarkNames.add("Tennoji Zoo");

        mLandmarkImageUrls.add(R.drawable.museum);
        mLandmarkNames.add("Museum");

        mActivityImageUrls.add(R.drawable.usj);
        mActivityNames.add("Universal Studio Japan");

        mActivityImageUrls.add(R.drawable.maikoya);
        mActivityNames.add("Maikoya Tea Ceremony");

        mActivityImageUrls.add(R.drawable.tonbori);
        mActivityNames.add("Tonbori");
    }

    public String toString(){
        return "Lookout";
    }
}
