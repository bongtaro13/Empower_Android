package com.example.empower.entity;

import java.util.ArrayList;
import java.util.Random;

public class ActiveHour {
    private ArrayList<String> activeHour;

    public ActiveHour() {
        this.activeHour = new ArrayList<>();
        this.activeHour.add("Active: 9:30am–4:30pm");
        this.activeHour.add("Active: 9:00am–5:00pm");
        this.activeHour.add("Active: 10:30am–5:00pm");
        this.activeHour.add("Active: 10:30am–4:30pm");
        this.activeHour.add("Active: 10:30am–4:30pm");
    }

    public ArrayList<String> getActiveHour() {
        return activeHour;
    }

    public void setActiveHour(ArrayList<String> activeHour) {
        this.activeHour = activeHour;
    }

    public String getAciveHours(){
        int num = activeHour.size();
        Random a =new Random();

        int random = a.nextInt(num);
        return activeHour.get(random);
    }
}
