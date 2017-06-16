package com.goyo.grocery_goyo.model;

/**
 * Created by Admin on 6/16/2017.
 */
public class RestaurantsTimings {
    public String c1;

    public String c2;

    public    String o1;

    public   String o2;

    public RestaurantsTimings(String c1, String c2, String o1, String o2) {
        this.c1 = c1;
        this.c2 = c2;
        this.o1 = o1;
        this.o2 = o2;
    }

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

