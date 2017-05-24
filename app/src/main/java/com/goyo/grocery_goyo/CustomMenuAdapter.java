package com.goyo.grocery_goyo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goyo.grocery.R;

import java.util.ArrayList;
import java.util.HashMap;
public class CustomMenuAdapter extends BaseAdapter {
    Context context;
    private ArrayList<MenuItems> dataList;
    private Integer addTocart = 0;
    ImageButton addQty, subQty;
    ImageView checkout;
    private ImageButton btnAddQty, btnsubQty;
    private int resturant_id;
    private String resturant_name;
    Holder h1 = null;
    //Declaring static variable for total amount to add total cost
    private int totalAmount = 0;
    private BottomNavigationView navigationView;
    //HashMap<String,Integer> positiveNumbers=new HashMap<String,Integer>();
    SharedPreferences settings;
    public CustomMenuAdapter(Context activity, ArrayList<MenuItems> xyz,String resturant_name) {
        context = activity;
        dataList = xyz;
        //Created listener for checkout imageview so that it can intent to screen of bill
        ResturantProfile.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent io=new Intent(context,CustomerBill.class);
                context.startActivity(io);
            }
        });
        settings = context.getSharedPreferences("PREF_NAME", 0);
        resturant_id = settings.getInt("Resturant_id", 0);
        this.resturant_name=resturant_name;
    }
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //View rowView;
        LayoutInflater mInflater = null;
        if (convertView == null) {
            mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_menu_items, null);
            h1 = new Holder(convertView);
            convertView.setTag(h1);

        } else {
            h1 = (Holder) convertView.getTag();
        }
        final MenuItems mtem = dataList.get(position);
        navigationView = (BottomNavigationView) convertView.findViewById(R.id.btmNavCart);
        btnAddQty = (ImageButton) convertView.findViewById(R.id.btnAddQty);
        btnAddQty.setTag(position);
        btnsubQty = (ImageButton) convertView.findViewById(R.id.btnSubQty);
        btnsubQty.setTag(position);
        h1.uniqueKey = String.valueOf(position);
        btnAddQty.setTag(position + "");
        btnAddQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer currQty = mtem.getCartQty();
                //AddToCart Method which will increment quantity on add button and will help to set it on the cart icon
                //Checking for maximum quantity selected by user
                Toast.makeText(context,String.valueOf(resturant_id),Toast.LENGTH_LONG).show();
                Toast.makeText(context,resturant_name,Toast.LENGTH_LONG).show();

                if (mtem.getCartQty() >= 10) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context);
                    }
                    builder.setTitle("Quantity Maximum")
                            .setMessage("Order value maximum cannot able to proceed")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else
                    {
                    //Creating Alert Dialog Box if the order reaches to the maximum value
                        CalculateTotalPrice(mtem.getRate());
                        AddToCart();
                        mtem.setCartQty(currQty + 1);
                        notifyDataSetChanged();
                    }

            }
        });
        btnsubQty.setTag(position + "");
        btnsubQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer currQty = mtem.getCartQty();
                if (currQty == 0) {
                    Toast.makeText(context, "Qty cannot be less than 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(ResturantProfile.totalAmount.getText().toString().equals("0") || ResturantProfile.QTY.getText().toString().equals("0"))
                {

                }
                else
                {
                    DeductTotalPrice(mtem.getRate());
                    DeductToCart();
                    mtem.setCartQty(currQty-1);
                    notifyDataSetChanged();
                }

            }
        });
        h1.txtQty.setText(mtem.getCartQty() + "");
        h1.textItemName.setText(mtem.getItemName());
        h1.textMenuPrice.setText(String.valueOf(mtem.getRate()));
        if (h1.textMenuDesc.getText().toString().trim().equals("")) {
            h1.textMenuDesc.setVisibility(View.GONE);
        }
        h1.textMenuDesc.setText(mtem.getMenuDesc());
        /*if(TextUtils.isEmpty(h1.textMenuDesc.getText().toString()))
        {
            h1.textMenuDesc.setEnabled(false);
        }*/
        return convertView;
    }

    public int AddToCart() {
        addTocart = addTocart + 1;
        ResturantProfile.QTY.setText(String.valueOf(addTocart));
        notifyDataSetChanged();
        return addTocart;
    }

    //Method to update the total bill of the user in the bottom Navigation View
    public void CalculateTotalPrice(int rate) {
        //Each time user will increment the quantity of 1 so the total amount will added in
        //the variable name totalAmount
        totalAmount = totalAmount + rate * 1;
        ResturantProfile.totalAmount.setText("₹" + String.valueOf(totalAmount));
        notifyDataSetChanged();
    }

    public void DeductTotalPrice(int rate) {
        //Each time user will decrement the quantity of 1 so the total amount will be deducted in
        //the variable name totalAmount
        totalAmount = totalAmount - rate * 1;
        ResturantProfile.totalAmount.setText("₹" + String.valueOf(totalAmount));
        notifyDataSetChanged();
    }
    public int DeductToCart() {
        addTocart = addTocart - 1;
        ResturantProfile.QTY.setText(String.valueOf(addTocart));
        return addTocart;
    }
    public class Holder {
        private TextView textItemName, textMenuPrice, textMenuDesc, txtQty;
        private String uniqueKey;
        public Holder(View item) {
            textItemName = (TextView) item.findViewById(R.id.txtMenuItem);
            textMenuPrice = (TextView) item.findViewById(R.id.txtMenuRate);
            textMenuDesc = (TextView) item.findViewById(R.id.txtMenuDesc);
            txtQty = (TextView) item.findViewById(R.id.txtRate);
        }
    }
}
