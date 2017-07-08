package com.goyo.grocery_goyo;

import android.location.Address;

/**
 * Created by Admin on 5/23/2017.
 */
public  class GeoSearchResult {
    private Address address;

    //Contructor to assign adress
    public GeoSearchResult(Address address) {
        this.address = address;
    }

    //Method to get the adress
    public String getAddress() {
        String display_adress = "";
        display_adress += address.getAddressLine(0) + "\n";
        for (int i = 1; i < address.getMaxAddressLineIndex(); i++) {
            display_adress += address.getAddressLine(i) + ",";
        }
        display_adress = display_adress.substring(0, display_adress.length() - 1);
        return display_adress;
    }
    //Method to convert the adress into string
    public String toString() {
        String display_adress = "";
        if (address.getFeatureName() != null) {
            display_adress += address + ",";
        }
        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
            display_adress += address.getAddressLine(i);
        }
        return display_adress;
    }
}
