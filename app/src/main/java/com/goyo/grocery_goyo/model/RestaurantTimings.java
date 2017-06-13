package com.goyo.grocery_goyo.model;

/**
 * Created by Admin on 6/13/2017.
 */
import com.google.gson.annotations.SerializedName;
public class RestaurantTimings
{
    @SerializedName("c1")
    public String close1;

    @SerializedName("c2")
    public String close2;

    @SerializedName("o1")
    public String open1;

    @SerializedName("o2")
    public String open2;

}
