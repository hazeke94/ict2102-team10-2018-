package com.aztheman.uncharted;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class travelpackage extends Fragment implements TabLayout.OnTabSelectedListener {
    ViewPager vp;
    TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_travelpackage, container, false);
        vp= (ViewPager) view.findViewById(R.id.pager);

        this.addPages();

        //TABLAYOUT
        tabLayout= (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(vp);
        tabLayout.setOnTabSelectedListener(this);

        TextView tv = (TextView) view.findViewById(R.id.countryTitle);
        TextView tv2 = (TextView) view.findViewById(R.id.countryTitle2);
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/montserrat.otf");
        Typeface face2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/montserrat-bold.otf");
        tv.setTypeface(face);
        tv2.setTypeface(face2);

        Bundle bundle = this.getArguments();
        if(bundle != null)
        {
            Toast.makeText(getContext(), bundle.getString("country"), Toast.LENGTH_SHORT).show();
            System.out.println("Bundle received " + bundle.getString("country"));
        }

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

    private void addPages()
    {
        FragmentPagerAdapter pagerAdapter=new FragmentPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new lookout());
        pagerAdapter.addFragment(new promotions());


        //SET ADAPTER TO VP
        vp.setAdapter(pagerAdapter);
    }

    public void onTabSelected(TabLayout.Tab tab) {
        vp.setCurrentItem(tab.getPosition());
    }


    public void onTabUnselected(TabLayout.Tab tab) {

    }


    public void onTabReselected(TabLayout.Tab tab) {

    }


    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    public void onPageSelected(int position) {

    }


    public void onPageScrollStateChanged(int state) {

    }
}
