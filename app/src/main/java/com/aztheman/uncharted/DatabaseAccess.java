package com.aztheman.uncharted;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance=new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db=openHelper.getWritableDatabase();
    }

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }

    public boolean insert(String email, String name, String password, String location,String dob,String diet){

        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("name",name);
        contentValues.put("password",password);
        contentValues.put("location",location);
        contentValues.put("dob",dob);
        contentValues.put("diet",diet);

        long ins = db.insert("user",null,contentValues);
        System.out.println("insert value is : " + ins);
        if(ins == -1) {
            System.out.println("Returning false");
            return false;
        }
        else {
            System.out.println("Returning true");
            return true;
        }
    }

    public boolean checkEmail(String email){
        c = db.rawQuery("SELECT * FROM user WHERE email=?", new String[]{email});
        System.out.println(email);
        if(c.getCount()>0 ){
            System.out.println(c);
            System.out.println("check value is : " + c.getCount());
            System.out.println("check return false");
            return false;
        }
        else{
            System.out.println(c);
            System.out.println("check value is : " + c.getCount());
            System.out.println("check return true");
            return true;
        }
    }

    public boolean emailPassword(String email, String password){
       c =  db.rawQuery("select * from user where email=? and password=?", new String[]{email,password});
        if(c.getCount() > 0){
            System.out.println(c);
            System.out.println("check value is : " + c.getCount());
            System.out.println("check return true");
            return true;
        }
        else{
            System.out.println(c);
            System.out.println("check value is : " + c.getCount());
            System.out.println("check return true");
            return false;
        }
    }

    public User retrieveProfile(String email){
        User user = null;
        ArrayList<Travel> countryTravelled = new ArrayList<>();
        String name,password,location,dob,diet;
        System.out.println("Where email is : " + email);
        c =  db.rawQuery("select * from user where email=?", new String[]{email});
        if(c.getCount() > 0){
            System.out.println(c);
            System.out.println("check value is : " + c.getCount());

            System.out.println("check return true");
            while (c.moveToNext()) {
                // Read columns data
                name = c.getString(c.getColumnIndex("name"));
                password = c.getString(c.getColumnIndex("password"));
                location = c.getString(c.getColumnIndex("location"));
                dob = c.getString(c.getColumnIndex("DOB"));
                diet = c.getString(c.getColumnIndex("diet"));
                user = new User(email,name,password,location,dob,diet,countryTravelled);
                System.out.println("Retrieved " + user.toString());
            }
        }

        return user;

    }

    public void display(){

        String selectQuery = "select * from  user";

        c =  db.rawQuery(selectQuery,null);
        if(c.getCount() >0)
        {
            while (c.moveToNext()) {
                // Read columns data
                String email = c.getString(c.getColumnIndex("email"));
                String password = c.getString(c.getColumnIndex("password"));
                //String outlet_type = c.getString(c.getColumnIndex("outlet_type"));
                System.out.println("Data is now : " + email + ", " + password);
            }
        }
    }

    public boolean updateData(String email, String location, String diet){
        boolean result = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("location",location);
        contentValues.put("diet",diet);

        long ins = db.update("user",contentValues,"email = ?", new String[] {email});
        System.out.println("Update value is : " + ins);
        if(ins == -1) {
            System.out.println("Returning false");
            result = false;
        }
        else {
            System.out.println("Profile Updated");
            result = true;
        }
        return result;
    }





}
