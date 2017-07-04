package com.goyo.grocery_goyo.LocalDB;

/**
 * Created by Admin on 6/29/2017.
 */

public class UserContract {
    //Each static abstract class for each and every table of Database
    public static abstract class AddressDetails
    {
        public static final String USER_ID="USER_ID";
        public static final String USER_ADDRESS="USER_ADDRESS";
        public static final String ADDRESS_TYPE="AD_TYPE";
        public static final String TABLE_NAME="Address_Details";
    }
    public static abstract class CartDetails
    {
        public static final String USER_ID="USER_ID";
        public static final String RES_ID="RES_ID";
        public static final String RES_NAME="RES_NAME";
        public static final String ITEM_ID="ITEM_ID";
        public static final String ITEM_NAME="ITEM_NAME";
        public static final String ITEM_QTY="ITEM_QTY";
        public static final String ITEM_PRICE="ITEM_PRICE";
        public static final String TABLE_NAME="Cart_Details";
    }
}
