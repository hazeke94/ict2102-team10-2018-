package com.aztheman.uncharted;

public class Travel {
    String email;
    String country;
    int travelled;

    public Travel(String email, String country, int travelled) {
        this.email = email;
        this.country = country;
        this.travelled = travelled;
    }

    public void setTravelled(int travelled) {
        this.travelled = travelled;
    }
}
