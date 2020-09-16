package com.example.empower.entity;

import com.google.android.gms.maps.model.LatLng;

public class LocationAddressPair {
    private LatLng latLngInfo;
    private Venue sportsVenueInfo;

    public LocationAddressPair(LatLng latLngInfo, Venue sportsVenueInfo) {
        this.latLngInfo = latLngInfo;
        this.sportsVenueInfo = sportsVenueInfo;
    }

    public LatLng getLatLngInfo() {
        return latLngInfo;
    }

    public void setLatLngInfo(LatLng latLngInfo) {
        this.latLngInfo = latLngInfo;
    }

    public Venue getSportsVenueInfo() {
        return sportsVenueInfo;
    }

    public void setSportsVenueInfo(Venue sportsVenueInfo) {
        this.sportsVenueInfo = sportsVenueInfo;
    }
}
