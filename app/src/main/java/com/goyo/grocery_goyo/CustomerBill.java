package com.goyo.grocery_goyo;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.goyo.grocery.R;
import com.goyo.grocery_goyo.Global.global;
import com.vstechlab.easyfonts.EasyFonts;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CustomerBill extends AppCompatActivity {
    private ExpandableListView mListView;
    private ExpandableListAdapter expandableListAdapter;
    private TextView txtBillDisplay, txtAmountDisplay, txtDeliveryDisplay, txtAmountValue, txtDeliveryValue;
    private Button btnForPayment;
    HashMap<String, List<String>> itemNames;
    HashMap<String, List<Integer>> quantity;
    HashMap<String, List<Double>> totalRates;
    //Array List to store item names of each and every restaurant wise
    private List<String> nowShowing;
    private List<Integer> totalQuantity;
    private List<Double> Rates;
    public static List<Integer> sumQuantity;
    public static List<Double> sumAmount;

    private android.support.v7.app.ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_bill);
        Intent io = getIntent();
        itemNames = new HashMap<String, List<String>>();
        quantity = new HashMap<String, List<Integer>>();
        totalRates = new HashMap<String, List<Double>>();
        //Initializing two arraylist to add the sum and quantity of each and everr restaurant
        sumQuantity=new ArrayList<>();
        sumAmount=new ArrayList<>();
        // customerBillDetailsList= (ArrayList<CustomerBillDetails>)io.getSerializableExtra("bill");
        mListView = (ExpandableListView) findViewById(R.id.list_display_bill);
        ArrayList<CustomerBillDetails> cc = new ArrayList<>();
        for (Map.Entry<Integer, CustomerBillDetails> entry : global.myCart.entrySet()) {
            cc.add(entry.getValue());
            //System.out.println(entry.getKey() + "/" + entry.getValue());
        }
        //Code to seperate each and every item according to resturant wise purchased by the customer
        for (int i = 0; i < global.resturantNames.size(); i++) {
            //Intializing three array list according to the resturant size to store Item Name,total Quantity,Rates
            nowShowing = new ArrayList<>();
            totalQuantity = new ArrayList<>();
            Rates = new ArrayList<>();
            int qty_sum=0;
            double sum_amount=0;
            for (int j = 0; j < cc.size(); j++) {
                //Seperating all the values from CustomerBillDetails ArratList according to the Match of Resturant Names
                if (global.resturantNames.get(i).contains(cc.get(j).getResturant_name())) {
                    nowShowing.add(cc.get(j).getItem_name());
                    totalQuantity.add(cc.get(j).getQuantity());
                    qty_sum=qty_sum+cc.get(j).getQuantity();
                    sum_amount=sum_amount+cc.get(j).getQuantity() * cc.get(j).getRate();
                    Rates.add(Double.valueOf((cc.get(j).getQuantity()) * (cc.get(j).getRate())));
                }
            }
            sumQuantity.add(qty_sum);
            sumAmount.add(sum_amount);
            //Putting each resturant and the items in the appropriate HashMap
            itemNames.put(global.resturantNames.get(i), nowShowing);
            quantity.put(global.resturantNames.get(i), totalQuantity);
            totalRates.put(global.resturantNames.get(i), Rates);
        }
        txtAmountValue = (TextView) findViewById(R.id.txtAmountValue);
        btnForPayment = (Button) findViewById(R.id.btnForPayment);
        //Setting the total amount of the bill in the final cart
        txtAmountValue.setText(ResturantProfile.totalAmount.getText().toString() + ".00");
        btnForPayment.setText(btnForPayment.getText().toString() + "   " + ResturantProfile.totalAmount.getText().toString() + ".00");
        btnForPayment.setTypeface(EasyFonts.caviarDreams(this));
        expandableListAdapter = new CustomBillAdapter(CustomerBill.this, global.resturantNames, itemNames, quantity, totalRates);
        mListView.setAdapter(expandableListAdapter);
        actionBar = getSupportActionBar();
        actionBar.hide();
        setFonts();
    }
    private void setFonts()
    {
        txtBillDisplay = (TextView) findViewById(R.id.txtBillDisplay);
        txtAmountDisplay = (TextView) findViewById(R.id.txtAmountDisplay);
        txtDeliveryDisplay = (TextView) findViewById(R.id.txtDeliveryDisplay);
        txtDeliveryValue = (TextView) findViewById(R.id.txtDeliveryValue);
        //EasyFonts library added by me in the gradle dependency to use
        //custom fonts for displaying on the screens
        txtBillDisplay.setTypeface(EasyFonts.robotoLight(this));
        txtAmountValue.setTypeface(EasyFonts.droidSerifItalic(this));
        txtAmountDisplay.setTypeface(EasyFonts.caviarDreamsBold(this));
        txtDeliveryDisplay.setTypeface(EasyFonts.caviarDreamsBold(this));
        txtDeliveryValue.setTypeface(EasyFonts.droidSerifItalic(this));
    }
}
