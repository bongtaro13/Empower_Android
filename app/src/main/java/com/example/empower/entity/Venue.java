package com.example.empower.entity;

public class Venue {
    private String venueID;
    private String type;
    private String name;
    private String address;
    private String suburb;
    private String postcode;
    private String businessCategory;
    private String lga;
    private String latitude;
    private String longitude;


    public Venue(String venueID, String type, String name, String address, String suburb, String postcode, String businessCategory, String lga, String latitude, String longitude) {
        this.venueID = venueID;
        this.type = type;
        this.name = name;
        this.address = address;
        this.suburb = suburb;
        this.postcode = postcode;
        this.businessCategory = businessCategory;
        this.lga = lga;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getVenueID() {
        return venueID;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
