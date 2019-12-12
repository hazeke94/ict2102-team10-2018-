package com.aztheman.uncharted;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class map_detail extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    Marker markerName;
    private ExpandableListView listViewCountry;
    private ContinentListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    private static final float DEFAULT_ZOOM = 5f;
    private ArrayList<Travel> travelled = new ArrayList<>();
    HashMap<String,MarkerOptions> hashMapMarker = new HashMap<>();

    public static map_detail newInstance(String param1, String param2) {
        map_detail fragment = new map_detail();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Travel Progress");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_detail, container, false);

        listViewCountry =(ExpandableListView) view.findViewById(R.id.lvCountries);//To be continued
        final Activity activity = (HomeActivity) getActivity();
        travelled = ((HomeActivity) activity).user.CountryTravelled;
        System.out.println("Size of travel : " + travelled.size());

        initData();
        listAdapter = new ContinentListAdapter(getContext(),listDataHeader,listHash);
        listViewCountry.setAdapter(listAdapter);
        //Bundle bundle = getArguments();
        //Toast.makeText(getContext(),bundle.getString("location") , Toast.LENGTH_SHORT).show();

        mMapView = (MapView) view.findViewById(R.id.map);
        if(mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }


        //setOnItemClick
        listViewCountry.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                 String searchString  = listHash.get(listDataHeader.get(i)).get(i1);

                 //GeoCoder

                Geocoder geocoder = new Geocoder(getContext());
                List<Address>  addressList = new ArrayList<>();

                try{
                    addressList = geocoder.getFromLocationName(searchString,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(addressList.size() > 0){
                    Address address = addressList.get(0);
                    Toast.makeText(getContext(), address.getCountryName(), Toast.LENGTH_SHORT).show();
                    moveCamera(new LatLng(address.getLatitude(),address.getLongitude()), DEFAULT_ZOOM,searchString);

                }


                return false;
            }
        });



        return view;
    }

    private void moveCamera(LatLng latLng,float zoom, String searchString){
        ///Bring to selected destination
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        if(markerName !=null){
            markerName.remove();
            //hashMapMarker.remove(searchString,markerName);
        }

        markerName  = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(searchString + " has not been visited!"));
        //hashMapMarker.put(searchString,markerName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        markerName = googleMap.addMarker(new MarkerOptions().position(new LatLng(40.689247,-74.044502)).title("Status of Liberty").snippet("You have traveled here"));
        boolean success = mGoogleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getContext(), R.raw.map_style));
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                //toast(marker.getTitle());
                Toast.makeText(getContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();


                //Open dialog to set whether travelled to that place
                final Dialog visited_dialog = new Dialog(getContext());
                visited_dialog.setContentView(R.layout.visited_dialog);
                visited_dialog.show();

                Button btnvisited = visited_dialog.findViewById(R.id.btnVisited);
                Button btnNotvisited = visited_dialog.findViewById(R.id.btnNotVisited);

                btnvisited.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //iterate loop to find country
                        for(int i =0;i<travelled.size();i++){
                            if(marker.getTitle().contains(travelled.get(i).country)){
                                //set user.countryTravelled to 1
                                travelled.get(i).setTravelled(1);

                                //add marker to map
                                Address address = getLatLag(travelled.get(i).country);
                                LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                                MarkerOptions options = new MarkerOptions().position(latLng).title(travelled.get(i).country + " has been visited");
                                hashMapMarker.put(travelled.get(i).country,options);
                                Toast.makeText(getContext(), travelled.get(i).country + " has been visited", Toast.LENGTH_SHORT).show();
                                System.out.println(travelled.get(i).country + " has been visited");
                                mGoogleMap.clear();
                                refreshmap();

                            }
                        }
                        visited_dialog.dismiss();
                    }
                });

                btnNotvisited.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i =0;i<travelled.size();i++){
                            if(marker.getTitle().contains(travelled.get(i).country)){
                                travelled.get(i).setTravelled(0);
                                //MarkerOptions marker = hashMapMarker.get(travelled.get(i).country);
                                //marker.remove();
                                hashMapMarker.remove(travelled.get(i).country);
                                mGoogleMap.clear();
                                refreshmap();
                                //System.out.println(travelled.get(i).country + " has been Unvisited");

                            }
                        }
                        visited_dialog.dismiss();
                    }
                });

                return false;
            }
        });
        CameraPosition pos = CameraPosition.builder().target(new LatLng(40.689247,-74.044502)).zoom(16).bearing(0).tilt(45).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(pos));
        setMarker();
        refreshmap();
    }


    private void initData(){
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        //Add continent
        listDataHeader.add("East Asia");
        listDataHeader.add("South East Asia");
        listDataHeader.add("South Asia/Middle East");
        listDataHeader.add("Oceania/Africa");
        listDataHeader.add("Europe");
        listDataHeader.add("North America");

        List<String> EA = new ArrayList<>();
        EA.add("HK & Macau");
        EA.add("Taiwan");
        EA.add("Japan");
        EA.add("South Korea");
        EA.add("China");

        List<String> SEA = new ArrayList<>();
        SEA.add("Singapore");
        SEA.add("Thailand");
        SEA.add("Malaysia");
        SEA.add("Indonesia");
        SEA.add("Philippines");
        SEA.add("Vietnam");
        SEA.add("Cambodia");
        SEA.add("Myanmar");
        SEA.add("Laos");

        List<String> SA = new ArrayList<>();//South Asia/Middle East
        SA.add("Nepal");
        SA.add("Bhutan");
        SA.add("India");
        SA.add("UAE");
        SA.add("Qatar");
        SA.add("Oman");
        SA.add("Turkey");
        SA.add("Israel");
        SA.add("Sri Lanka");

        List<String> Oce = new ArrayList<>();
        Oce.add("Australia");
        Oce.add("New Zealand");
        Oce.add("South Africa");
        Oce.add("Mauritius");
        Oce.add("Morocco");

        List<String> EU = new ArrayList<>();
        EU.add("United Kingdom");
        EU.add("France");
        EU.add("Netherlands");
        EU.add("Germany");
        EU.add("Austria");
        EU.add("Switzerland");
        EU.add("Italy");
        EU.add("Spain");
        EU.add("Finland");
        EU.add("Sweden");
        EU.add("Norway");
        EU.add("Denmark");
        EU.add("Greece");
        EU.add("Serbia");
        EU.add("Russia");
        EU.add("Ireland");
        EU.add("Portugal");


        List<String> NA = new ArrayList<>();
        NA.add("USA");

        listHash.put(listDataHeader.get(0),EA );//SEA  SA Oce EU NA
        listHash.put(listDataHeader.get(1),SEA );
        listHash.put(listDataHeader.get(2),SA );
        listHash.put(listDataHeader.get(3),Oce );
        listHash.put(listDataHeader.get(4),EU );
        listHash.put(listDataHeader.get(5),NA );

    }

    public void setMarker(){
        //
        for(int i =0;i<travelled.size();i++){

            if(travelled.get(i).travelled == 1) {
                Address address = getLatLag(travelled.get(i).country);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                System.out.println(address.getLatitude() + ", " + address.getLongitude());
                System.out.println(travelled.get(i).country);
                MarkerOptions options = new MarkerOptions().position(latLng).title(travelled.get(i).country + " has been visited!");
                //Marker m = mGoogleMap.addMarker(options);

                hashMapMarker.put(travelled.get(i).country, options);
            }
        }
    }

    public Address getLatLag(String searchString){
        Geocoder geocoder = new Geocoder(getContext());
        List<Address>  addressList = new ArrayList<>();
        Address address =  null;
        System.out.println("Searching for " + searchString);
        try{
            addressList = geocoder.getFromLocationName(searchString,1);
            System.out.println("Addresslist size " + addressList.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addressList.size() > 0){
            address = addressList.get(0);
        }
        System.out.println("Address is " + address);
        return address;
    }

    public void refreshmap(){
        Iterator mapiterator = hashMapMarker.keySet().iterator();
        while(mapiterator.hasNext()) {
            String key=(String)mapiterator.next();
            MarkerOptions value=hashMapMarker.get(key).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            //Toast.makeText(ctx, "Key: "+key+" Value: "+value, Toast.LENGTH_LONG).show();
            System.out.println("Country : " + key);
            mGoogleMap.addMarker(value);
        }
    }


}
