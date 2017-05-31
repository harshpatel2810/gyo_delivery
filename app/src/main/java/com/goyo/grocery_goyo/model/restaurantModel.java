package com.goyo.grocery_goyo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 5/17/2017.
 */

public class restaurantModel {

    @SerializedName("restid")
    public Integer restid = 0;

    @SerializedName("restname")
    public String restname = "";

    @SerializedName("lat")
    public String lat= "0.0";

    @SerializedName("lon")
    public String lon= "0.0";

    @SerializedName("adr")
    public String adr = "";

    @SerializedName("cont")
    public String cont = "";

    @SerializedName("imgpath")
    public String image_path;

    @SerializedName("rating")
    public String rating;

    @SerializedName("resttyp")
    public String resttype;

}
