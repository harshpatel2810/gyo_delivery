package com.goyo.grocery_goyo;

/**
 * Created by Admin on 5/13/2017.
 */

public class ResturantDetails {
    protected String ResturantName;
    protected String ResturantDesc;
    protected Double ResturantRating;
    protected String DeliveryTime;

    public ResturantDetails(String resturantName, String resturantDesc, Double resturantRating, String deliveryTime) {
        ResturantName = resturantName;
        ResturantDesc = resturantDesc;
        ResturantRating = resturantRating;
        DeliveryTime = deliveryTime;
    }

    public String getResturantName() {
        return ResturantName;
    }

    public void setResturantName(String resturantName) {
        ResturantName = resturantName;
    }

    public String getResturantDesc() {
        return ResturantDesc;
    }

    public void setResturantDesc(String resturantDesc) {
        ResturantDesc = resturantDesc;
    }

    public Double getResturantRating() {
        return ResturantRating;
    }

    public void setResturantRating(Double resturantRating) {
        ResturantRating = resturantRating;
    }

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }
}
