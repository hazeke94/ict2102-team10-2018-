package com.aztheman.uncharted;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class promotions extends Fragment {
    ArrayList<promo> promoArray = new ArrayList<>();
    private ArrayList<String> promoDetail = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_promotions, container, false);

        ListView lvPromo = view.findViewById(R.id.lvPromoList);
        getImages();
         customListviewAdapter customListviewAdapter = new customListviewAdapter(getContext(),R.layout.layout_promoitem);
        for(int i = 0;i<promoArray.size();i++){
            customListviewAdapter.add(promoArray.get(i));
        }
        System.out.println("custom size " + customListviewAdapter.getCount());
         lvPromo.setAdapter(customListviewAdapter);

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
        promo obj = new promo("Honshu Japan",R.drawable.promo1,"https://tripzilla.sg/tour/package/82153/6-days-honshu-japan-mandarin-speaking-guide","6 Days 5 Nights", "SGD 1258");
        promoArray.add(obj);
        promoDetail.add("6 Days 5 Nights Honshu Japan");

        promo obj2 = new promo("Tokyo & Osaka Twin City Plus",R.drawable.promo2,"https://tripzilla.sg/tour/package/85684/7d6n-6d5n-tokyo-osaka-twin-city-plus", "6 Days 5 Nights", "SGD 1168");
        promoArray.add(obj2);

        promo obj3 = new promo("Spring Flower",R.drawable.promo3,"https://tripzilla.sg/tour/package/85799/8n-spring-flowers-2019-diamond-princess", "9 Days 8 Nights", "SGD 1956");
        promoArray.add(obj3);

        promo obj4 = new promo("Osaka & Kyoto Delight",R.drawable.promo4,"https://tripzilla.sg/tour/package/75550/osaka-and-kyoto-delight-3n4d", "4 Days 3 Nights", "SGD 820");
        promoArray.add(obj4);
    }

    public String toString(){
        return "Promotions";
    }
}
