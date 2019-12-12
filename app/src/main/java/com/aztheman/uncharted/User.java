package com.aztheman.uncharted;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Parcelable
{
    String name;
    String email;
    String password;
    String location;
    String dob;
    String dietary;
    ArrayList<Travel> CountryTravelled;

    public User(String email, String name, String password, String location, String dob, String dietary, ArrayList<Travel> countryTravelled) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.location = location;
        this.dob = dob;
        this.dietary = dietary;
        this.CountryTravelled = countryTravelled;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "name='" + this.name + '\'' +
                ", email='" + this.email + '\'' +
                ", password='" + this.password + '\'' +
                ", location='" + this.location + '\'' +
                ", dob='" + this.dob + '\'' +
                ", dietary='" + this.dietary + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeString(location);
        dest.writeString(dob);
        dest.writeString(dietary);
    }

    protected User(Parcel in){
        this.email = in.readString();
        this.name = in.readString();
        this.password = in.readString();
        this.location = in.readString();
        this.dob = in.readString();
        this.dietary = in.readString();
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDietary(String dietary) {
        this.dietary = dietary;
    }

    public void setCountryTravelled(ArrayList<Travel> c){
        this.CountryTravelled=c;
    }
}
