package com.example.empower.entity;

import com.google.android.gms.maps.model.LatLng;

public class LocationAddressPair {
    private LatLng latLngInfo;
    private SportsVenue sportsVenueInfo;

    public LocationAddressPair(LatLng latLngInfo, SportsVenue sportsVenueInfo) {
        this.latLngInfo = latLngInfo;
        this.sportsVenueInfo = sportsVenueInfo;
    }

    public LatLng getLatLngInfo() {
        return latLngInfo;
    }

    public void setLatLngInfo(LatLng latLngInfo) {
        this.latLngInfo = latLngInfo;
    }

    public SportsVenue getSportsVenueInfo() {
        return sportsVenueInfo;
    }

    public void setSportsVenueInfo(SportsVenue sportsVenueInfo) {
        this.sportsVenueInfo = sportsVenueInfo;
    }
}
