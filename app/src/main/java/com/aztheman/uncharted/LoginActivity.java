package com.aztheman.uncharted;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    String name,email,password,location,dob,dietval;
    List<String> listofcountry = new ArrayList<>();
    ArrayAdapter<String> locationAdapter;
    ArrayAdapter<String> dataAdapter;
    List<String> list;
    DatabaseAccess db;

    EditText useremail;
    EditText userpass;

    String uEmail;
    String uPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login_btn = (Button) findViewById(R.id.login_btn);
        Button Reg_btn = (Button) findViewById(R.id.register_btn);

//      Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/lobster.otf");
//      TextView textView = (TextView) findViewById(R.id.titleText);
//      textView.setTypeface(tf);
        db = DatabaseAccess.getInstance(LoginActivity.this);

        list = new ArrayList<String>();
        list.add("Halal");
        list.add("Non Halal");
        list.add("Vegetarian");
        useremail = (EditText) this.findViewById(R.id.userName);
        userpass = (EditText) this.findViewById(R.id.Password);

        //load list of country
        try {
            loadCountry();
        } catch (IOException e) {
            e.printStackTrace();
        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get login data and check
                //userName Password

                uEmail = useremail.getText().toString();
                uPassword = userpass.getText().toString();
                db.open();
                Boolean checklogin = db.emailPassword(uEmail,uPassword);
                if(checklogin == true)
                {
                    db.close();
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    //retrieve user details
                    db.open();
                    db.display();
                    User u = db.retrieveProfile(uEmail);

                    Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
//                    ArrayList<String> userTravelled = new ArrayList<>();
//                    User user = new User(email,name,password,location,dob,dietval,userTravelled);
                    //Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    System.out.println("Sending : " + u.toString());
                    homeIntent.putExtra("User", u);
                    startActivity(homeIntent);

                    finish();
                    db.close();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //create adapter for country
        locationAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listofcountry);

        //create adapter for diet
        dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, register.class);
                startActivity(registerIntent);
                finish();
            }
        });
    }

    private void loadCountry() throws IOException {
        InputStream is = getResources().getAssets().open("countrylist.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                String token = line;
                listofcountry.add(line);
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}