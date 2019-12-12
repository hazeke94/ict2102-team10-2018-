package com.aztheman.uncharted;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class quiz extends Fragment {

    Button moption1,moption2,moption3,moption4;
    TextView mquestion;
    ArrayList<quizObject> quizArray = new ArrayList<>();
    ArrayList<option> optionL = new ArrayList<>();
    ArrayList<Travel> c_travelled = new ArrayList<>();
    List<String> cList = new ArrayList<>();
    List<String> filterList = new ArrayList<>();
    public quiz() {
        // Required empty public constructor
    }
    int quiz_i = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Quiz");
    }






    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        Activity activity = (HomeActivity)getActivity();
        //retrieve country list and list of travelled country
        c_travelled = ((HomeActivity) activity).countryTraveled;
        //cList = ((HomeActivity) activity).listofcountry;
        for(int i =0; i< ((HomeActivity) activity).listofcountry.size();i++){
            cList.add(((HomeActivity) activity).listofcountry.get(i));
        }
        filterList = ((HomeActivity) activity).country;
        cList.removeAll(filterList);
        moption1 = (Button) view.findViewById(R.id.btnOption1);
        moption2 = (Button) view.findViewById(R.id.btnOption2);
        moption3 = (Button) view.findViewById(R.id.btnOption3);
        moption4 = (Button) view.findViewById(R.id.btnOption4);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/montserrat-medium.otf");
        moption1.setTypeface(font);
        moption2.setTypeface(font);
        moption3.setTypeface(font);
        moption4.setTypeface(font);

        mquestion = (TextView) view.findViewById(R.id.tvQuestion);
        optionL = ((HomeActivity) activity).optionlist;
        //generate questions
        generateQuestion();


        mquestion.setText(quizArray.get(quiz_i).getQuestion());
        moption1.setTag(1);
        moption1.setText(optionL.get(0).optionDesc);

        moption2.setTag(1);
        moption2.setText(optionL.get(1).optionDesc);

        moption3.setTag(1);
        moption3.setText(optionL.get(2).optionDesc);

        moption4.setTag(1);
        moption4.setText(optionL.get(3).optionDesc);


        moption1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                option o = getOption(String.valueOf(moption1.getText()));
                answered(quizArray.get(quiz_i),o);
                quiz_i = quiz_i + 1;
            }
        });

        moption2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                option o = getOption(String.valueOf(moption2.getText()));
                answered(quizArray.get(quiz_i),o);
                quiz_i = quiz_i + 1;
            }
        });

        moption3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                option o = getOption(String.valueOf(moption3.getText()));
                answered(quizArray.get(quiz_i),o);
                quiz_i = quiz_i + 1;
            }
        });

        moption4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                option o = getOption(String.valueOf(moption4.getText()));
                answered(quizArray.get(quiz_i),o);
                quiz_i = quiz_i + 1;
            }
        });

        return view;
    }

    private void generateQuestion() {

        quizObject qObj = new quizObject(1,"Which of these interest you?","","");
        quizObject qObj1 = new quizObject(2,"Which of these interest you?","","");
        quizObject qObj2 = new quizObject(3,"Which of these interest you?","","");

        quizArray.add(qObj);
        quizArray.add(qObj1);
        quizArray.add(qObj2);
    }

    private void answered(quizObject q,option o){
        //save selected option
        q.setOptionSelected(o.getOptionDesc());
        moption1.setVisibility(View.VISIBLE);
        moption2.setVisibility(View.VISIBLE);
        moption3.setVisibility(View.VISIBLE);
        moption4.setVisibility(View.VISIBLE);
        //update question
        mquestion.setText(quizArray.get(quiz_i).getQuestion());
        System.out.println("Option seleced " + o.getOptionDesc());
        System.out.println("Option ID " + o.getOptID());

        TextView quizques= (TextView) getActivity().findViewById(R.id.quizQuestion);
        TextView quiztitle= (TextView) getActivity().findViewById(R.id.tvQuestion);
        ImageView quizimg= (ImageView) getActivity().findViewById(R.id.quizimage);



        if(o.getOptID() == 1){
            System.out.println("ADVENTURE SELECTED");
            moption1.setText(optionL.get(4).getOptionDesc());
            moption2.setText(optionL.get(5).getOptionDesc());
            moption3.setText(optionL.get(6).getOptionDesc());
            moption4.setVisibility(View.GONE);

            quizques.setText("Question 2");
            quiztitle.setText("What type of adventure attracts you?");
            quizimg.setImageResource(R.drawable.quiz2);

        }
        if(o.getOptID() == 2){
            moption1.setText(optionL.get(7).getOptionDesc());
            moption2.setText(optionL.get(8).getOptionDesc());
            moption3.setText(optionL.get(9).getOptionDesc());
            moption4.setText(optionL.get(10).getOptionDesc());

            quizques.setText("Question 2");
            quiztitle.setText("What type of landmarks do you prefer?");
            quizimg.setImageResource(R.drawable.quiz3);
        }
        if(o.getOptID() == 3){
            moption1.setText(optionL.get(11).getOptionDesc());
            moption2.setText(optionL.get(12).getOptionDesc());
            moption3.setText(optionL.get(13).getOptionDesc());
            moption4.setVisibility(View.GONE);

            quizques.setText("Question 2");
            quiztitle.setText("What type of activity do you prefer?");
            quizimg.setImageResource(R.drawable.quiz4);

        }
        if(o.getOptID() == 4){
            moption1.setText(optionL.get(14).getOptionDesc());
            moption2.setText(optionL.get(15).getOptionDesc());
            moption3.setVisibility(View.GONE);
            moption4.setVisibility(View.GONE);

            quizques.setText("Question 2");
            quiztitle.setText("What type of events will you like to attend?");
            quizimg.setImageResource(R.drawable.quiz5);
        }
        if(o.getOptID() == 5){
            moption1.setText(optionL.get(16).getOptionDesc());
            moption2.setText(optionL.get(17).getOptionDesc());
            moption3.setText(optionL.get(18).getOptionDesc());
            moption4.setText(optionL.get(19).getOptionDesc());

            quizques.setText("Question 3");
            quiztitle.setText("What type of outdoor actvities?");
            quizimg.setImageResource(R.drawable.quiz6);
        }
        if(o.getOptID() == 6){
            moption1.setText(optionL.get(20).getOptionDesc());
            moption2.setText(optionL.get(21).getOptionDesc());
            moption3.setText(optionL.get(22).getOptionDesc());
            moption4.setVisibility(View.GONE);

            quizques.setText("Question 3");
            quiztitle.setText("What type of air actvities?");
            quizimg.setImageResource(R.drawable.quiz7);
        }
        if(o.getOptID() == 7){
            moption1.setText(optionL.get(23).getOptionDesc());
            moption2.setText(optionL.get(24).getOptionDesc());
            moption3.setText(optionL.get(25).getOptionDesc());
            moption4.setText(optionL.get(26).getOptionDesc());

            quizques.setText("Question 3");
            quiztitle.setText("What type of water actvities?");
            quizimg.setImageResource(R.drawable.quiz8);
        }
        if(o.getOptID() == 8){
            result("Egypt");
        }
        if(o.getOptID() == 9){
            //bring to country
            result("France");
        }
        if(o.getOptID() == 10){
            //bring to country
            result("Hong Kong");
        }
        if(o.getOptID() == 11){
            //bring to country
            result("India");
        }
        if(o.getOptID() == 12){
            //bring to country
            result("Australia");
        }
        if(o.getOptID() == 13){
            //bring to country
            result("USA");
        }
        if(o.getOptID() == 14){
            //bring to country
            result("Vietnam");
        }
        if(o.getOptID() == 15){
            //bring to country
            result("Turkey");
        }
        if(o.getOptID() == 16){
            //bring to country
            result("Singapore");
        }
        if(o.getOptID() == 17){
            //bring to country
            result("Africa");
        }
        if(o.getOptID() == 18){
            //bring to country
            result("Africa");
        }
        if(o.getOptID() == 19){
            //bring to country
            result("Australia");
        }
        if(o.getOptID() == 20){
            //bring to country
            result("Australia");
        }
        if(o.getOptID() == 21){
            //bring to country
            result("Australia");
        }
        if(o.getOptID() == 22){
            //bring to country
            result("Australia");
        }
        if(o.getOptID() == 23){
            //bring to country
            result("Australia");
        }
        if(o.getOptID() == 24){
            //bring to country
            result("Bali");
        }
        if(o.getOptID() == 25){
            //bring to country
            result("Bali");
        }
        if(o.getOptID() == 26){
            //bring to country
            result("Bali");
        }
        if(o.getOptID() == 27){
            //bring to country
            result("Bali");
        }


    }

    public void result(String country){

        boolean result = checkifTravelled(country);
        if(result == false) {
           country = suggestAnotherCountry();
        }
        travelpackage quiz_results = new travelpackage();
        Bundle bundle = new Bundle();
        bundle.putString("country",country);
        quiz_results.setArguments(bundle);
        FragmentManager manager = getFragmentManager();

        manager.beginTransaction().replace(R.id.screen_area,quiz_results).addToBackStack(null).commit();
    }

    public option getOption(String desc){
        option o = new option(0," ");
        for(int i =0;i<optionL.size();i++){
            if (optionL.get(i).optionDesc == desc){
                o.setOptID(optionL.get(i).getOptID());
                o.setOptionDesc(optionL.get(i).getOptionDesc());
                break;
            }
        }
        return o;
    }

    public boolean checkifTravelled(String c){
        boolean result = false;
        for(int i =0;i<c_travelled.size();i++){
            if(c.equals(c_travelled.get(i).country)){
                result = true;
            }
        }
        return result;
    }

    public String suggestAnotherCountry(){
        String newCountry = cList.get(new Random().nextInt(cList.size()));




        return newCountry;
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
