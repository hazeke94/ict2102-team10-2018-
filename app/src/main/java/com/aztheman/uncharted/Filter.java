package com.aztheman.uncharted;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Filter extends Fragment {

    DatePickerDialog picker;
    EditText eText;
    EditText eText2;
    boolean people = false;
    boolean startDate = false;
    boolean returnDate = false;
    ImageButton couplebtn,solobtn,familybtn,friendbtn;
    boolean couple_ispressed,family_ispressed,solo_ispressed,friend_ispressed = false;
    quiz_filter filter;
    SimpleDateFormat dfDate;

    public Filter() {
        // Required empty public constructor
    }

//    public static Filter newInstance(String param1, String param2) {
//        Filter fragment = new Filter();
//        Bundle args = new Bundle();
//
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_filter, container, false);
        //Declare seekbar for Budget
        SeekBar budgetSeek = (SeekBar) view.findViewById(R.id.budgetSeeker);
        TextView tvMin = (TextView) view.findViewById(R.id.tvMin);
        TextView tvMax = (TextView) view.findViewById(R.id.tvMax);
        final TextView tvBudget = (TextView) view.findViewById(R.id.tvBudget);
        tvMin.setText("$" + budgetSeek.getMin());
        tvMax.setText("$" + budgetSeek.getMax());

        final Activity activity = (HomeActivity) getActivity();
        filter = ((HomeActivity) activity).filter_quiz;

        couplebtn = view.findViewById(R.id.imgButtonCouple);
        solobtn = view.findViewById(R.id.imgButtonSolo);
        familybtn = view.findViewById(R.id.imgButtonFamily);
        friendbtn = view.findViewById(R.id.imgButtonFriends);

        dfDate  = new SimpleDateFormat("dd/mm/yyyy");

        Button nextBtn = (Button) view.findViewById(R.id.next_btn);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/montserrat-medium.otf");
        nextBtn.setTypeface(font);

        //set a listener when user interact with seekbar to change budget value
        budgetSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            //get value from seekbar
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                filter.budget = progressChangedValue;
                tvBudget.setText( "$" + String.valueOf(progressChangedValue));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //Display seekbar value
                Toast.makeText(getContext(), "Budget set to : " + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
                tvBudget.setText( "$" + String.valueOf(progressChangedValue));
            }
        });

        //couplebtn,solobtn,familybtn,friendbtn;

        couplebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                people = true;
                filter.companion = "Couple";
                couple_ispressed = true;
                friend_ispressed = false;
                solo_ispressed = false;
                family_ispressed = false;
                btnchangecolor();
            }
        });

        solobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                people = true;
                filter.companion = "Solo";
                couple_ispressed = false;
                friend_ispressed = false;
                solo_ispressed = true;
                family_ispressed = false;
                btnchangecolor();
            }
        });
        familybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                people = true;
                filter.companion = "Family";
                couple_ispressed = false;
                friend_ispressed = false;
                solo_ispressed = false;
                family_ispressed = true;
                btnchangecolor();
            }
        });
        friendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                people = true;
                filter.companion = "Friend";
                couple_ispressed = false;
                friend_ispressed = true;
                solo_ispressed = false;
                family_ispressed = false;
                btnchangecolor();
            }
        });

        // Calendar Start Date
        eText=(EditText) view.findViewById(R.id.start_date);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                filter.departdate = eText.getText().toString();
                                startDate = true;
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        // Calendar Start Date 2
        eText2=(EditText) view.findViewById(R.id.end_date);
        eText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText2.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                filter.returndate = eText2.getText().toString();
                                returnDate = true;
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = null;
            String title="Quiz";
            @Override
            public void onClick(View view) {
                if (people == true && startDate == true && returnDate == true) {
                    boolean datecheck = false;
                    datecheck = CheckDates(filter.departdate,filter.returndate);
                    if(datecheck == true) {
                       fragment = new quiz();
                       FragmentManager manager = getFragmentManager();
                       manager.beginTransaction().replace(R.id.screen_area, fragment).addToBackStack(null).commit();
                    }
                    else{
                        Toast.makeText(getContext(), "Departure date cannot be before today's date!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    if(people == false) {
                        Toast.makeText(getContext(), "Choose your travelling companion!", Toast.LENGTH_SHORT).show();
                    }
                    if(startDate == false){
                        Toast.makeText(getContext(), "Choose your departure date.", Toast.LENGTH_SHORT).show();
                    }
                    if(returnDate == false){
                        Toast.makeText(getContext(), "Choose your return date.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public void btnchangecolor(){
        if(solo_ispressed == true){
            solobtn.setSelected(true);
            solobtn.setBackgroundResource(R.drawable.filter_btn_selection);

            couplebtn.setSelected(false);
            couplebtn.setBackgroundResource(R.drawable.filter_btn);

            familybtn.setSelected(false);
            familybtn.setBackgroundResource(R.drawable.filter_btn);

            friendbtn.setSelected(false);
            friendbtn.setBackgroundResource(R.drawable.filter_btn);
        }
        else if (couple_ispressed == true){
            couplebtn.setSelected(true);
            couplebtn.setBackgroundResource(R.drawable.filter_btn_selection);

            solobtn.setSelected(false);
            solobtn.setBackgroundResource(R.drawable.filter_btn);

            familybtn.setSelected(false);
            familybtn.setBackgroundResource(R.drawable.filter_btn);

            friendbtn.setSelected(false);
            friendbtn.setBackgroundResource(R.drawable.filter_btn);
        }
        else if (family_ispressed == true) {
            familybtn.setSelected(true);
            familybtn.setBackgroundResource(R.drawable.filter_btn_selection);

            couplebtn.setSelected(false);
            couplebtn.setBackgroundResource(R.drawable.filter_btn);

            solobtn.setSelected(false);
            solobtn.setBackgroundResource(R.drawable.filter_btn);

            friendbtn.setSelected(false);
            friendbtn.setBackgroundResource(R.drawable.filter_btn);
        }
        else if (friend_ispressed == true) {
            friendbtn.setSelected(true);
            friendbtn.setBackgroundResource(R.drawable.filter_btn_selection);

            familybtn.setSelected(false);
            familybtn.setBackgroundResource(R.drawable.filter_btn);

            couplebtn.setSelected(false);
            couplebtn.setBackgroundResource(R.drawable.filter_btn);

            solobtn.setSelected(false);
            solobtn.setBackgroundResource(R.drawable.filter_btn);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Filter your needs");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public  boolean CheckDates(String d1, String d2)    {
        boolean b = false;
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String day          = (String) DateFormat.format("dd",   c); // 20
        //String monthString  = (String) DateFormat.format("MMM",  c); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   c); // 06
        String year         = (String) DateFormat.format("yyyy", c); // 2013
        String formattedDate = day + "/" + monthNumber + "/" + year;
        System.out.println("Current Date => " + formattedDate);
        try {
            if(dfDate.parse(d1).after(dfDate.parse(formattedDate)) || dfDate.parse(d1).equals(dfDate.parse(formattedDate))){
                b = true;
                //Toast.makeText(getContext(), "Start date is after/equal current date.", Toast.LENGTH_SHORT).show();
                System.out.println("Start date is after/equal current date");
            }
            if(dfDate.parse(d1).before(dfDate.parse(d2)))
            {
                b = true;//If start date is before end date
                //Toast.makeText(getContext(), "Start date is before end date.", Toast.LENGTH_SHORT).show();
                System.out.println("Start date is before end date.");
            }
            if(dfDate.parse(d1).equals(dfDate.parse(d2)))
            {
                b = false;//If two dates are equal
                Toast.makeText(getContext(), "The two dates cannot be the same.", Toast.LENGTH_SHORT).show();
                System.out.println("The two dates cannot be the same.");
            }
            if(dfDate.parse(d1).before(dfDate.parse(formattedDate))){
                b = false;
                Toast.makeText(getContext(), "Start date is before current date.", Toast.LENGTH_SHORT).show();
                System.out.println("Start date is before current date");
            }
            if(dfDate.parse(d1).after(dfDate.parse(d2)))
            {
                b = false; //If start date is after the end date
                Toast.makeText(getContext(), "Start date is after the end date.", Toast.LENGTH_SHORT).show();
                System.out.println("Start date is after the end date");
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }
}