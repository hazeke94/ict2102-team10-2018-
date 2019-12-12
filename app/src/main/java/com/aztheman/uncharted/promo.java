package com.aztheman.uncharted;

public class promo {
    String name;
    Integer image;
    String promo_url;
    String duration;
    String price;

    public promo(String name, Integer image, String promo_url, String duration, String price) {
        this.name = name;
        this.image = image;
        this.promo_url = promo_url;
        this.duration = duration;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getPromo_url() {
        return promo_url;
    }

    public void setPromo_url(String promo_url) {
        this.promo_url = promo_url;
    }
}
