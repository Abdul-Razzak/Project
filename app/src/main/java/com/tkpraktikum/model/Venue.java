package com.tkpraktikum.model;

public class Venue {
    public String id;
    public String name;
    public Double lat;
    public Double lng;
    public Double rating;
    public String phone;

    public String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getRating() { return rating; }

    public void setRating(Double rating) { this.rating = rating; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getLocation() { return location;}

    public void setLocation(String location) { this.location = location;}


}
