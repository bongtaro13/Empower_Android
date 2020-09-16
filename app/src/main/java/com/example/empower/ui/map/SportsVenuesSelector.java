package com.example.empower.ui.map;

import com.example.empower.entity.Venue;

import java.util.ArrayList;

public class SportsVenuesSelector {

    private ArrayList<Venue> sportsVenueList;

    public SportsVenuesSelector(ArrayList sportsVenueList) {
        this.sportsVenueList = sportsVenueList;
    }


    // create a selected sports venues list based on sport selected
    public ArrayList<Venue> createSelectedSportsVenueListBySport(String inputSportName) {
        ArrayList<Venue> selectedSportsVenuList = new ArrayList<>();
        String sportName = inputSportName.toLowerCase();
        if (!inputSportName.equals("sport")) {
            for (Venue tempSportsVenue : sportsVenueList) {
                if (tempSportsVenue.getBusinessCategory().toLowerCase().contains(sportName)) {
                    selectedSportsVenuList.add(tempSportsVenue);
                }
            }
        }

        return selectedSportsVenuList;
    }


    // create a selected sports venues list based on based on postcode input
    public ArrayList<Venue> createSelectedSportsVenueListByPostcode(String inputPostcode) {
        ArrayList<Venue> selectedSportsVenuList = new ArrayList<>();
        if (inputPostcode.length() == 0) {
            return selectedSportsVenuList;
        }
        for (Venue tempSportsVenue : sportsVenueList) {
            if (tempSportsVenue.getPostcode().equals(inputPostcode)) {
                selectedSportsVenuList.add(tempSportsVenue);
            }
        }

        return selectedSportsVenuList;
    }

    // create a selected sports venues list based on  the combination of sport  selected and postcode
    public ArrayList<Venue> createSelectedSportsVenueListByCombination(String inputPostcode, String inputSportName) {
        ArrayList<Venue> selectedSportsVenuList = new ArrayList<>();
        ArrayList<Venue> resultVenueList = new ArrayList<>();
        String sportName = inputSportName.toLowerCase();
        // if spinner has been selected
        if (!inputSportName.equals("sport")) {
            for (Venue tempSportsVenue : sportsVenueList) {
                if (tempSportsVenue.getBusinessCategory().toLowerCase().contains(sportName)) {
                    selectedSportsVenuList.add(tempSportsVenue);
                }
            }
        }

        if (selectedSportsVenuList.size() != 0) {

            if (inputPostcode.length() != 0) {
                for (Venue tempSportsVenue : selectedSportsVenuList) {
                    if (tempSportsVenue.getPostcode().equals(inputPostcode)) {
                        resultVenueList.add(tempSportsVenue);
                    }
                }
                return resultVenueList;
            }

        }
        return selectedSportsVenuList;

    }

}
