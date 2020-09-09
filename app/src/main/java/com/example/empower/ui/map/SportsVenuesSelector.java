package com.example.empower.ui.map;

import com.example.empower.entity.SportsVenue;

import java.util.ArrayList;

public class SportsVenuesSelector {

    private ArrayList<SportsVenue> sportsVenueList;

    public SportsVenuesSelector(ArrayList sportsVenueList) {
        this.sportsVenueList = sportsVenueList;
    }



    // create a selected sports venues list based on sport selected
    public ArrayList<SportsVenue> createSelectedSportsVenueListBySport(String inputSportName){
        ArrayList<SportsVenue> selectedSportsVenuList = new ArrayList<>();
        String sportName = inputSportName.toLowerCase();
        if (! inputSportName.equals("sport")){
            for (SportsVenue tempSportsVenue : sportsVenueList){
                if (tempSportsVenue.getBusinessCategory().toLowerCase().contains(sportName)){
                    selectedSportsVenuList.add(tempSportsVenue);
                }
            }
        }

        return selectedSportsVenuList;
    }


    // create a selected sports venues list based on based on postcode input
    public ArrayList<SportsVenue> createSelectedSportsVenueListByPostcode(String inputPostcode) {
        ArrayList<SportsVenue> selectedSportsVenuList = new ArrayList<>();
        if (inputPostcode.length() == 0) {
            return selectedSportsVenuList;
        }
        for (SportsVenue tempSportsVenue : sportsVenueList) {
            if (tempSportsVenue.getPostcode().equals(inputPostcode)) {
                selectedSportsVenuList.add(tempSportsVenue);
            }
        }

        return selectedSportsVenuList;
    }

    // create a selected sports venues list based on  the combination of sport  selected and postcode
//    public ArrayList<SportsVenue> createSelectedSportsVenueListByCombination(String inputPostcode, String inputSportName){
//        ArrayList<SportsVenue> selectedSportsVenuList = new ArrayList<>();
//        String sportName = inputSportName.toLowerCase();
//        // if spinner has been selected
//        if (! inputSportName.equals("sport")){
//            for (SportsVenue tempSportsVenue : sportsVenueList){
//                if (tempSportsVenue.getBusinessCategory().toLowerCase().contains(sportName)){
//                    selectedSportsVenuList.add(tempSportsVenue);
//                }
//            }
//        }else if ()
//
//
//
//
//    }

}
