package com.goyo.grocery_goyo.Activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goyo.grocery.R;
import com.goyo.grocery_goyo.Adapters.CustomBillAdapter;
import com.goyo.grocery_goyo.Global.global;
import com.goyo.grocery_goyo.model.CustomerBillDetails;
import com.vstechlab.easyfonts.EasyFonts;

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
    Point p;
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
        btnForPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if(p!=null) {
                      showPopUp(CustomerBill.this, p);
                  }

            }
        });
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
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        int[] location = new int[2];
        Button button = (Button) findViewById(R.id.btnForPayment);

        // Get the x, y location and store it in the location[] array
        // location[0] = x, location[1] = y.
        button.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        p = new Point();
        p.x = location[0];
        p.y = location[1];
    }
    private void showPopUp(final Activity cxt,Point p)
    {
        int popUpwidth=1500;
        int popUpHeight=1500;
        RelativeLayout viewGroup=(RelativeLayout)cxt.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater)cxt
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.layout_delivery_address, viewGroup);
        final PopupWindow popup = new PopupWindow(cxt);
        popup.setContentView(layout);
        popup.setWidth(popUpHeight);
        popup.setHeight(popUpwidth);
        popup.setFocusable(true);

        int OFFSET_X = 350;
        int OFFSET_Y = 400;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
        // Getting a reference to Close button, and close the popup when clicked.
        Button ok = (Button)layout.findViewById(R.id.btnOk);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
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
