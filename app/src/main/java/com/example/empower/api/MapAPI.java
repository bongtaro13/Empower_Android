//package com.example.empower.api;
//
//import android.content.Context;
//import android.location.Address;
//import android.location.Geocoder;
//
//import com.example.empower.entity.LocationAddressPair;
//import com.example.empower.entity.SportsVenue;
//import com.google.android.gms.maps.model.LatLng;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MapAPI {
//
//    public MapAPI() {
//    }
//
//
//    // get Location
//    public ArrayList<LocationAddressPair> getNearbyLocationAddressPair (ArrayList<SportsVenue> sportsVenueArrayList){
//
//        ArrayList<LocationAddressPair> combineLocationMapping = new ArrayList<>();
//
//        for (SportsVenue tempSportsVenue: sportsVenueArrayList){
//
//            String tempSportsVenueAddress = tempSportsVenue.getAddress() + " "
//                    + tempSportsVenue.getPostcode() + " "
//                    + tempSportsVenue.getState();
//            LatLng tempSportsVenueLatlng = getLocationFromAddress(,tempSportsVenueAddress);
//        }
//
//
//    }
//
//
//
//
//    // get location with lat & lng from the address
//    public LatLng getLocationFromAddress(Context context, String venueAddress) {
//        Geocoder geocoder = new Geocoder(context);
//        List<Address> addresses;
//        LatLng resultLatLon = null;
//
//        try {
//            addresses = geocoder.getFromLocationName(venueAddress, 3);
//
//            if (addresses == null) {
//                return null;
//            }
//
//            Address location = addresses.get(0);
//            if (location != null) {
//                resultLatLon = new LatLng(location.getLatitude(), location.getLongitude());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return resultLatLon;
//
//    }
//}
