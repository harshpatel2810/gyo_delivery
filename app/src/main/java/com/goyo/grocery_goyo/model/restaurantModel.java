package com.goyo.grocery_goyo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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

   @SerializedName("minordamt")
    public Double min_order;
    public Integer getRestid() {
        return restid;
    }

    public void setRestid(Integer restid) {
        this.restid = restid;
    }

    public String getRestname() {
        return restname;
    }

    public void setRestname(String restname) {
        this.restname = restname;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getResttype() {
        return resttype;
    }

    public void setResttype(String resttype) {
        this.resttype = resttype;
    }

    public Double getMin_order() {
        return min_order;
    }
    public void setMin_order(Double min_order) {
        this.min_order = min_order;
    }
    public static class Timings
    {
        @SerializedName("c1")
         public String c1;
        @SerializedName("c2")
         public String c2;
        @SerializedName("o1")
         public String o1;
        @SerializedName("o2")
         public String o2;
        public String getC1() {
            return c1;
        }

        public void setC1(String c1) {
            this.c1 = c1;
        }

        public String getC2() {
            return c2;
        }

        public void setC2(String c2) {
            this.c2 = c2;
        }

        public String getO1() {
            return o1;
        }

        public void setO1(String o1) {
            this.o1 = o1;
        }

        public String getO2() {
            return o2;
        }

        public void setO2(String o2) {
            this.o2 = o2;
        }
    }
}
