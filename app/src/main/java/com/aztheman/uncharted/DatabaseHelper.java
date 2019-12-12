package com.aztheman.uncharted;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(email text primary key,name text,password text,location text," +
                "dob text, diet text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //email,name,password,location,dob,diet
    public boolean insert(String email, String name, String password, String location,String dob,String diet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("name",name);
        contentValues.put("password",password);
        contentValues.put("location",location);
        contentValues.put("dob",dob);
        contentValues.put("diet",diet);

        long ins = db.insert("user",null,contentValues);
        db.execSQL("Insert into user(email,name,password,location,dob,diet) VALUES('"+ email+"','"+name+"','"+password+"','"+location+"','"+dob+"','"+diet+"');");
        if(ins == 1) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email=?", new String[]{email});
        if(cursor.getCount()>0 ){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean emailPassword(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * from user where email=? and password=?", new String[]{email,password});
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }

    }
}
