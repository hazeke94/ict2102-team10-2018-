package com.aztheman.uncharted;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Define ArrayList options and country
    ArrayList<option> optionlist = new ArrayList<>();
    List<String> listofcountry = new ArrayList<>();
    ArrayList<Travel> countryTraveled = new ArrayList<>();
    ArrayList<String> country = new ArrayList<>();
    ArrayList<String> genielist = new ArrayList<>();
    User user;
    quiz_filter filter_quiz;
    TextView tvProfile;
    TextView tvViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("home");
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.MyTitleTextApperance);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        user = intent.getParcelableExtra("User");
        user.setCountryTravelled(countryTraveled);
        System.out.println("user in home : " + user.toString());
        filter_quiz = new quiz_filter(1500,"solo"," "," ");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        tvProfile = (TextView) headerView.findViewById(R.id.tvProfile);
        tvViewProfile = (TextView) headerView.findViewById(R.id.tvViewProfile);
        navigationView.setNavigationItemSelectedListener(this);

        if(findViewById(R.id.screen_area)!=null){
            if(savedInstanceState != null){
                return;
            }

            GenieFragment fragmentGenie = new GenieFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.screen_area, fragmentGenie).commit();
        }

        //Load option and country list for quiz component after logging in
        try {
            loadOption();
            loadCountry();
            loadTravelled();
            loadgenie();
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setCountryTravelled(countryTraveled);
        tvProfile.setText(user.name);
        tvViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProfileFragment();
                String title="Profile";
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.screen_area,fragment);
                ft.addToBackStack("home");
                ft.commit();
                getSupportActionBar().setTitle(title);
                drawer.closeDrawer(Gravity.LEFT);


            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1 && getSupportFragmentManager().getBackStackEntryCount() < 3) {
            createQuizDialog();
        } else {
            finish();
        }
    }

    private void createQuizDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Your progress won't be saved. Are you sure you want to exit the quiz?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent homeIntent = new Intent(HomeActivity.this, HomeActivity.class);
                homeIntent.putExtra("User", user);
                startActivity(homeIntent);
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private void createLogoutDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure you want to logout?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.create().show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //calls fragment Activity using fragment manager
        Fragment fragment = null;
        String title = "Test";
        int id = item.getItemId();//menu item id

        //for menu id, you can edit in res/menu/activity_main_drawer xml file
        //when item selected, set fragment instance based on fragment class IE.. Filter()
        if (id == R.id.nav_home) {
            fragment = new GenieFragment();
            title="Travel Genie";
        }
        else if (id == R.id.nav_geniehistory) {

            fragment = new geniehistory();
            title="Genie History";
        }
        else if (id == R.id.nav_Travel) {
            fragment = new map_detail();
            title="Travel Progress";

        }
        else if (id == R.id.nav_wishlist) {
//            fragment = new map_detail();
//            title="Travel Progress";
            createLogoutDialog();
        }

        //Once fragment is set, call fragment manager to call and replace current activity/fragment
        if(fragment !=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screen_area,fragment);
            ft.addToBackStack("home");
            ft.commit();
            getSupportActionBar().setTitle(title);
        }

        //Navigation drawer layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadOption() throws IOException {
        InputStream is = getResources().getAssets().open("option.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(",");
                String val = token[0].replaceAll("\\p{C}", "");//remove unprintable char before converting value to integer
                option opt_obj = new option(Integer.valueOf(val), token[1]);
                optionlist.add(opt_obj);
                System.out.println(opt_obj.toString());
            }
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
                Travel t_obj =new Travel(user.email,line,0);
                countryTraveled.add(t_obj);
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTravelled() throws IOException {
        InputStream is = getResources().getAssets().open("usertravelled.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";
        List<String> temp_country = new ArrayList<>();
        try {
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(",");
                System.out.println(user.email + " has travelled : ");
                if(token[0].equals(user.email)){
                    temp_country.add(token[1]);
                    System.out.println(token[1]);
                }
            }
            for(int i =0;i<countryTraveled.size();i++){
                for(int x = 0;x<temp_country.size();x++){
                    if(countryTraveled.get(i).country.equals(temp_country.get(x))){
                        countryTraveled.get(i).setTravelled(1);
                        System.out.println(countryTraveled.get(i).country);
                        country.add(countryTraveled.get(i).country);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadgenie() throws IOException {
        InputStream is = getResources().getAssets().open("genie_suggest.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(",");
                System.out.println(user.email + " has suggest : ");
                if(token[0].equals(user.email)){
                    genielist.add(token[1]);
                    System.out.println(token[1]);
                }
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
