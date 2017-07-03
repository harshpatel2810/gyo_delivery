package com.goyo.grocery_goyo.model;

import com.goyo.grocery_goyo.Global.global;

/**
 * Created by Admin on 5/25/2017.
 */

public class CustomerBillDetails {
    private Integer ItemId;
    private Integer resturant_id;
    private String resturant_name;
    private String item_name;
    private Integer quantity;
    private Integer rate;
    private Integer totalAmount;


    public CustomerBillDetails() {
    }

    public CustomerBillDetails(Integer _ItemId, Integer resturant_id, String resturant_name, String item_name, Integer quantity, Integer rate, Integer totalAmount) {
        this.resturant_id = resturant_id;
        this.resturant_name = resturant_name;
        this.item_name = item_name;
        this.quantity = quantity;
        this.rate = rate;
        this.totalAmount = totalAmount;
        this.ItemId = _ItemId;
    }

    public Integer getResturant_id() {
        return resturant_id;
    }

    public void setResturant_id(Integer resturant_id) {
        this.resturant_id = resturant_id;
    }

    public String getResturant_name() {
        return resturant_name;
    }

    public void setResturant_name(String resturant_name) {
        this.resturant_name = resturant_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getItemId() {
        return ItemId;
    }

    public void setItemId(Integer itemId) {
        ItemId = itemId;
    }

}
