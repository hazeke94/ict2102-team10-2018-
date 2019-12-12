package com.aztheman.uncharted;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class register extends AppCompatActivity {
    String name,email,password,location,dob,dietval;
    List<String> listofcountry = new ArrayList<>();
    ArrayAdapter<String> locationAdapter;
    ArrayAdapter<String> dataAdapter;
    List<String> list;
    DatabaseAccess db;
    int spinnerPosition;

    EditText Ename;
    EditText Eemail;
    EditText Epassword;
    AutoCompleteTextView Elocation;
    DatePicker Edob;
    Spinner diet;

    Button btnRegister, btncancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = DatabaseAccess.getInstance(register.this);
        btnRegister = (Button) findViewById(R.id.btnConfirmRegister);
        btncancel = (Button) findViewById(R.id.btnCancel);

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        Ename = findViewById(R.id.EDName);
        Eemail =  findViewById(R.id.EDEmail);
        Epassword =  findViewById(R.id.EDPassword);
        Elocation =  findViewById(R.id.ACLocation);
        Edob =  findViewById(R.id.EDob);
        diet = findViewById(R.id.spDiet);

        list = new ArrayList<String>();
        list.add("Halal");
        list.add("Non Halal");
        list.add("Vegetarian");

        locationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listofcountry);

        //create adapter for diet
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set autocompletetextview adapter
        Elocation.setThreshold(2);
        Elocation.setAdapter(locationAdapter);
        diet.setAdapter(dataAdapter);
        spinnerPosition = locationAdapter.getPosition("Halal");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = Ename.getText().toString();
                email = Eemail.getText().toString();
                password = Epassword.getText().toString();
                location = Elocation.getText().toString();
                dob = Edob.getDayOfMonth() + "/" + Edob.getMonth() + "/" + Edob.getYear();
                dietval = diet.getSelectedItem().toString();

                System.out.println(name);
                System.out.println(password);
                System.out.println(email);
                System.out.println(location);
                System.out.println(dob);
                System.out.println(dietval);

                db.open();
                db.display();
                db.close();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                //implement database insert
                if(name.length() == 0  || password.length() == 0 || email.length() == 0 || location.length() == 0){
                    Toast.makeText(register.this, "Please fill out the fields", Toast.LENGTH_SHORT).show();
                }
                else if(!email.matches(emailPattern)){
                    Toast.makeText(register.this, "Email is not Valid", Toast.LENGTH_SHORT).show();
                }
                else{
                    db.open();
                    Boolean check = db.checkEmail(email);
                    if(check == true){
                        Boolean insert = db.insert(email,name,password,location,dob,dietval);
                        if(insert == true){
                            Toast.makeText(register.this, "Successfully Registered", Toast.LENGTH_SHORT).show();

                            //instantiate user class
                            ArrayList<Travel> userTravelled = new ArrayList<>();
                            //String FName, String LName, String email, String password, String location, String dob, String dietary, ArrayList<String> countryTravelled
                            User user = new User(email,name,password,location,dob,dietval,userTravelled);
                            Intent homeIntent = new Intent(register.this, HomeActivity.class);
                            System.out.println("Sending : " + user.toString());
                            homeIntent.putExtra("User", user);
                            startActivity(homeIntent);
                            finish();
                            db.close();
                        }
                        else{
                            Toast.makeText(register.this, "Error inserting into database", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(register.this, "Email already in used, try a different email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog leave_dialog = new Dialog(register.this);
                //register.setContentView(R.layout.register);
                leave_dialog.setContentView(R.layout.confirmation_dialog);
                leave_dialog.show();

                Button leave = (Button) leave_dialog.findViewById(R.id.btnleave);
                Button stay = (Button) leave_dialog.findViewById(R.id.btnstay);

                leave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent LoginIntent = new Intent(register.this, LoginActivity.class);
                        startActivity(LoginIntent);
                        finish();
                        leave_dialog.dismiss();
                    }
                });
                stay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        leave_dialog.dismiss();
                    }
                });
            }
        });

        try {
            loadCountry();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
