package com.aztheman.uncharted;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    List<String> list;
    DatabaseAccess db;
    int spinnerPosition;
    List<String> listofcountry = new ArrayList<>();
    ArrayAdapter<String> locationAdapter;
    ArrayAdapter<String> dataAdapter;
    User user_obj;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fragment, container, false);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/montserrat-bold.otf");
        TextView textView = (TextView) view.findViewById(R.id.profileName);
        textView.setTypeface(tf);
        TextView tvprofileName = view.findViewById(R.id.profileName);
        TextView tvEmail = view.findViewById(R.id.email);
        final TextView tvLocation = view.findViewById(R.id.location);
        TextView tvDob = view.findViewById(R.id.dob);
        final TextView tvDietary = view.findViewById(R.id.dietary);
        ImageButton editProfilebtn = view.findViewById(R.id.imageButton);

        //Retrieve user obj
        final Activity activity = (HomeActivity) getActivity();
        user_obj = ((HomeActivity) activity).user;
        listofcountry = ((HomeActivity) activity).listofcountry;
        System.out.println("user in profile : " + user_obj.toString());
        list = new ArrayList<String>();
        list.add("Halal");
        list.add("Non Halal");
        list.add("Vegetarian");
        db = DatabaseAccess.getInstance(getContext());
        tvprofileName.setText(user_obj.name);
        tvEmail.setText(user_obj.email);
        tvLocation.setText(user_obj.location);
        tvDob.setText(user_obj.dob);
        tvDietary.setText(user_obj.dietary);

        //upon editProfilebtn clicked
        editProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open EditProfile Dialog
                final Dialog editprofile = new Dialog(getContext());
                editprofile.setContentView(R.layout.editprofile);
                editprofile.show();

                //textview for editprofile dialog
                TextView profileName = editprofile.findViewById(R.id.profileName);
                //TextView profileEmail = editprofile.findViewById(R.id.email);
                final AutoCompleteTextView location = editprofile.findViewById(R.id.AClocation);
                //TextView profileDob = editprofile.findViewById(R.id.dob);
                final Spinner profileDiet = editprofile.findViewById(R.id.dietary);

                Button btnUpdateProfile = editprofile.findViewById(R.id.btnUpdateProfile);
                Button btnCancel = editprofile.findViewById(R.id.btnCancel);

                //set adapter for location -- Country List
                locationAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,listofcountry);
                //create adapter for diet
                dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //set autocompletetextview adapter
                location.setThreshold(2);
                location.setAdapter(locationAdapter);
                profileDiet.setAdapter(dataAdapter);
                spinnerPosition = locationAdapter.getPosition(user_obj.dietary);

                //set text retrieved from database
                //profileName.setText(user_obj.name);
                //profileEmail.setText(user_obj.email);
                location.setText(user_obj.location);
                //profileDob.setText(user_obj.dob);

                btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String location_data = location.getText().toString();
                        String diet_data = profileDiet.getSelectedItem().toString();
                        if(location_data != " " || diet_data != " "){
                            db.open();//location diet
                            boolean result = db.updateData(user_obj.email,location_data,diet_data);

                            if(result == true){
                                ((HomeActivity) activity).user.setLocation(location_data);
                                ((HomeActivity) activity).user.setDietary(diet_data);
                                Toast.makeText(getContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                                tvDietary.setText(diet_data);
                                tvLocation.setText(location_data);
                                editprofile.dismiss();
                            }
                            else{
                                Toast.makeText(getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                            }
                            db.close();
                        }
                        else{
                            Toast.makeText(getContext(), "Please fill in details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //cancel update profile
                        editprofile.dismiss();
                    }
                });
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
