package com.goyo.grocery_goyo;

import java.util.ArrayList;

/**
 * Created by Admin on 5/26/2017.
 */

public class PlaceOrder {
    public static ArrayList<CustomerBillDetails> customerDetails=new ArrayList<CustomerBillDetails>();;
    public PlaceOrder()
    {
    }
    public void AddToBill(CustomerBillDetails customerBillDetails)
    {
        customerDetails.add(customerBillDetails);
    }

}
