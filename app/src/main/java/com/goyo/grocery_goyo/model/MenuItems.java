package com.goyo.grocery_goyo.model;

import android.content.Intent;
import android.view.MenuItem;
/**
 * Created by Admin on 5/13/2017.
 */
public class MenuItems {
    public Integer getItemId() {
        return itemId;
    }
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    private Integer itemId;
    private String itemName;
    private Integer Rate;
    public String getMenuDesc() {
        return menuDesc;
    }
    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }
    private String menuDesc;
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public MenuItems(Integer _itemId,String itemName, Integer rate, String menuDesc) {
        this.itemName = itemName;
        Rate = rate;
        this.menuDesc = menuDesc;
        this.itemId = _itemId;
    }
    public Integer getRate() {
        return Rate;
    }
    public void setRate(Integer rate) {
        Rate = rate;
    }
    public MenuItems(String itemName, Integer rate) {
        this.itemName = itemName;
        Rate = rate;
    }
    public Integer getCartQty() {
        return cartQty;
    }
    public void setCartQty(Integer cartQty) {
        this.cartQty = cartQty;
    }
    private Integer cartQty = 0;
    public MenuItems()
    {
    }
}
