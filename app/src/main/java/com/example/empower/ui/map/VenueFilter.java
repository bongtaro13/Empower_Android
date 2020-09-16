package com.example.empower.ui.map;

import android.location.Location;

import com.example.empower.entity.Venue;

import java.util.ArrayList;

public class VenueFilter {
    public ArrayList<Venue> getVenueWithPostCode(String inputPostCode, ArrayList<Venue> inputVeneus) {
        ArrayList<Venue> outputVenue = new ArrayList<>();

        if (inputPostCode.equals("")){
            return inputVeneus;
        }
        for (Venue tempVenue : inputVeneus) {
            if (tempVenue.getPostcode().equals(inputPostCode)) {
                outputVenue.add(tempVenue);
            }
        }
        if (outputVenue.size() > 0){
            return outputVenue;
        }else {
            return inputVeneus;
        }



    }


    public ArrayList<Venue> getVenueWithType(ArrayList<String> venueTypeList, ArrayList<Venue> inputVeneus) {
        ArrayList<Venue> outputVenue = new ArrayList<>();
        if (venueTypeList.size() == 0) {
            return outputVenue;
        }
        ArrayList test = venueTypeList;

        for (int i = 0; i < venueTypeList.size(); i++) {
            for (Venue tempVenue : inputVeneus) {
                if (tempVenue.getType().equals(venueTypeList.get(i))) {
                    outputVenue.add(tempVenue);
                }
            }
        }

        if (outputVenue.size() > 0){
            return outputVenue;
        }else {
            return inputVeneus;
        }


    }

    public ArrayList<Venue> getVenueWithSports(ArrayList<String> venueSportList, ArrayList<Venue> inputVeneus) {
        ArrayList<Venue> outputVenue = new ArrayList<>();
        if (venueSportList.size() == 0) {
            return inputVeneus;
        }

        for (int i = 0; i < venueSportList.size(); i++) {
            for (Venue tempVenue : inputVeneus) {
                if (tempVenue.getBusinessCategory().contains(venueSportList.get(i))) {
                    outputVenue.add(tempVenue);
                }
            }
        }

        if (outputVenue.size() > 0){
            return outputVenue;
        }else {
            return inputVeneus;
        }
    }

    public ArrayList<Venue> getVenueNearby(String inputNearby, ArrayList<Venue> inputVeneus, Location inputCurrentLocaiton) {
        ArrayList<Venue> outputVenue = new ArrayList<>();

        double currentLat = inputCurrentLocaiton.getLatitude();
        double currentLng = inputCurrentLocaiton.getLongitude();

        if (inputNearby.equals("5km")) {
            for (Venue temp : inputVeneus) {
                if (temp.getLatitude().equals("") || temp.getLongitude().equals("")){
                    continue;
                }

                double tempLat = Double.parseDouble(temp.getLatitude());
                double tempLng = Double.parseDouble(temp.getLongitude());
                double theta = currentLng - tempLng;

                double dist = Math.sin(deg2rad(currentLat))
                        * Math.sin(deg2rad(tempLat))
                        + Math.cos(deg2rad(currentLat))
                        * Math.cos(deg2rad(tempLat))
                        * Math.cos(deg2rad(theta));
                dist = Math.acos(dist);
                dist = rad2deg(dist);
                dist = dist * 60 * 1.1515;
                dist = dist * 1.609344;

                if (dist <= 5) {
                    outputVenue.add(temp);
                }


            }

            return outputVenue;
        }

        if (inputNearby.equals("10km")) {
            for (Venue temp : inputVeneus) {
                if (temp.getLatitude() == null || temp.getLongitude() == null){
                    continue;
                }
                double tempLat = Double.parseDouble(temp.getLatitude());
                double tempLng = Double.parseDouble(temp.getLongitude());
                double theta = currentLng - tempLng;

                double dist = Math.sin(deg2rad(currentLat))
                        * Math.sin(deg2rad(tempLat))
                        + Math.cos(deg2rad(currentLat))
                        * Math.cos(deg2rad(tempLat))
                        * Math.cos(deg2rad(theta));
                dist = Math.acos(dist);
                dist = rad2deg(dist);
                dist = dist * 60 * 1.1515;
                dist = dist * 1.609344;

                if (dist <= 10) {
                    outputVenue.add(temp);
                }

            }

            return outputVenue;
        }

        return inputVeneus;

    }


    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
