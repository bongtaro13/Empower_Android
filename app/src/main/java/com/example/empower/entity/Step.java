package com.example.empower.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {

    private String distance;
    private String travelMode;
    private String shortName;
    private String vehicleName;


    public Step(){
        distance = "0 Km";
        travelMode = "travel mode";
        shortName = "No Vehicle";
        vehicleName = "No Vehicle";

    }

    public Step(String distacen, String travelMode, String shortName, String vehicleName) {
        this.distance = distacen;
        this.travelMode = travelMode;
        this.shortName = shortName;
        this.vehicleName = vehicleName;
    }


    protected Step(Parcel in) {
        distance = in.readString();
        travelMode = in.readString();
        shortName = in.readString();
        vehicleName = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(distance);
        dest.writeString(travelMode);
        dest.writeString(shortName);
        dest.writeString(vehicleName);


    }
}
