package com.example.empower.ui.map;

import android.location.Location;

import com.example.empower.database.LikedVenue;
import com.example.empower.entity.Venue;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Iterator;

public class VenueFilter {

    // filter venue with postcode input
    public ArrayList<Venue> getVenueWithPostCode(String inputPostCode, ArrayList<Venue> inputVeneus) {
        ArrayList<Venue> outputVenue = new ArrayList<>();

        if (inputPostCode.equals("")) {
            return inputVeneus;
        }
        for (Venue tempVenue : inputVeneus) {
            if (tempVenue.getPostcode().equals(inputPostCode)) {
                outputVenue.add(tempVenue);
            }
        }
        return outputVenue;
    }


    // filter venue with venue type
    public ArrayList<Venue> getVenueWithType(ArrayList<String> venueTypeList, ArrayList<Venue> inputVeneus) {
        ArrayList<Venue> outputVenue = new ArrayList<>();
        if (venueTypeList.size() == 0) {
            return outputVenue;
        }

        for (int i = 0; i < venueTypeList.size(); i++) {
            for (Venue tempVenue : inputVeneus) {
                if (tempVenue.getType().equals(venueTypeList.get(i))) {
                    outputVenue.add(tempVenue);
                }
            }
        }


        return outputVenue;


    }

    // filter venue with supported sports
    public ArrayList<Venue> getVenueWithSports(ArrayList<String> venueSportList, ArrayList<Venue> inputVeneus) {
        ArrayList<Venue> outputVenue = new ArrayList<>();
        if (venueSportList.size() == 0) {
            return outputVenue;
        }

        if (venueSportList.contains("all")){
            return inputVeneus;
        }

        for (int i = 0; i < venueSportList.size(); i++) {
            for (Venue tempVenue : inputVeneus) {
                if (tempVenue.getBusinessCategory().toLowerCase().contains(venueSportList.get(i).toLowerCase())) {
                    outputVenue.add(tempVenue);
                }
            }
        }


        return outputVenue;

    }

    // filter venue with distance
    public ArrayList<Venue> getVenueNearby(String inputNearby, ArrayList<Venue> inputVeneus, Location inputCurrentLocaiton) {
        ArrayList<Venue> outputVenue = new ArrayList<>();

        double currentLat = inputCurrentLocaiton.getLatitude();
        double currentLng = inputCurrentLocaiton.getLongitude();

        if (inputNearby.equals("5km")) {
            for (Venue temp : inputVeneus) {
                if (temp.getLatitude().equals("") || temp.getLongitude().equals("")) {
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
                if (temp.getLatitude() == null || temp.getLongitude() == null) {
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


    public Boolean checkIfCurrentVeneuLiked(Venue currentSelectedVeneu, ArrayList<LikedVenue> likedVenueArrayList) {
        if (likedVenueArrayList.isEmpty()) {
            return false;
        }

        for (LikedVenue tempVenue : likedVenueArrayList) {
            if (currentSelectedVeneu.getVenueID().equals(tempVenue.venueID)) {
                return true;
            }
        }

        return false;

    }


    public Venue findVenueByLatLng(LatLng location, ArrayList<Venue> inputVeneus) {
        Venue outputVenue = null;
        for (Venue tempVenue : inputVeneus) {
            if (tempVenue.getLatitude().equals(String.valueOf(location.latitude)) && tempVenue.getLongitude().equals(String.valueOf(location.longitude))) {
                outputVenue = tempVenue;
                break;
            }
        }
        return outputVenue;
    }

    public Venue findVenueByVenueId(String inputVenueID, ArrayList<Venue> inputVeneus) {
        Venue outputVenue = null;
        for (Venue tempVenue : inputVeneus) {
            if (inputVenueID.equals(tempVenue.getVenueID())) {
                outputVenue = tempVenue;
                break;
            }
        }
        return outputVenue;
    }


}
