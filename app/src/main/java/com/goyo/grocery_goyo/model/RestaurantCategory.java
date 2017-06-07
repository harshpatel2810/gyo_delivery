package com.goyo.grocery_goyo.model;

/**
 * Created by Admin on 6/7/2017.
 */

public class RestaurantCategory
{
    int restuarant_cat_id;
    String category_name;
    boolean selected=false;
    public int getRestuarant_cat_id() {
        return restuarant_cat_id;
    }
    public void setRestuarant_cat_id(int restuarant_cat_id) {
        this.restuarant_cat_id = restuarant_cat_id;
    }

    public RestaurantCategory(int restuarant_cat_id, String category_name, boolean selected) {
        this.restuarant_cat_id = restuarant_cat_id;
        this.category_name = category_name;
        this.selected = selected;
    }

    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
