package com.goyo.grocery_goyo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
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
import com.goyo.grocery_goyo.Global.global;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class CustomMenuAdapter extends BaseAdapter {
    Context context;
    private ArrayList<MenuItems> dataList;
    private Integer addTocart = 0;
    ImageButton addQty, subQty;
    ImageView checkout;
    private ImageButton btnAddQty, btnsubQty;
    private int resturant_id;
    private String resturant_name;
    PlaceOrder p1;
    Holder h1 = null;
    //Declaring static variable for total amount to add total cost
    private static int totalAmount = 0;
    //Declaring static variable to get the order amount according to the restaurant by calling method
    public static int totalAmountValidate;
    //Variable to fetch value of Minimum Order according to restaurant
    private int MinOrder;
    public BottomNavigationView navigationView;
    CustomerBillDetails customerBillDetails;
    //public static ArrayList<CustomerBillDetails> customerBillDetailsList;
    //HashMap<String,Integer> positiveNumbers=new HashMap<String,Integer>();
    private final String PREF_BILL = "Bill Details";
    SharedPreferences settings, settings1;
    ArrayList<CustomerBillDetails> cc;

    public CustomMenuAdapter(Context activity, final ArrayList<MenuItems> xyz, final String resturant_name) {
        context = activity;
        dataList = xyz;
        settings = context.getSharedPreferences("PREF_NAME", 0);
        settings1 = context.getSharedPreferences("PREF_BILL", 0);
        resturant_id = settings.getInt("Resturant_id", 0);
        totalAmountValidate = 0;
        //Fetching Minimum Order Value According to the Resturant selection
        MinOrder = CustomResturantAdapter.MinOrder.intValue();
        cc = new ArrayList<>();
        //Fetching the current value of the total amount ordered by the customer
        ResturantProfile.totalAmount.setText("₹" + String.valueOf(settings1.getInt("Total Amount", 0)));
        ResturantProfile.QTY.setText(String.valueOf(settings1.getInt("CurrentCart", 0)));
        ResturantProfile.totalAmount.setTextColor(Color.RED);
        //Getting Name of the Restaurants
        this.resturant_name = resturant_name;
        //customerBillDetailsList = new ArrayList<CustomerBillDetails>();
        //Created listener for checkout imageview so that it can intent to screen of bill
        //Since there is need of resturant id i have stored it in object of shared preference
        //and i am fetching it through same object of shared preference
        if (global.resturantNames.contains(this.resturant_name)) {
            //Code to to validate the wether the resturant name is already included in array list if availaible
            //than it will not include it
        } else {
            //Else it will include it in the array list
            global.resturantNames.add(this.resturant_name);
        }
        ResturantProfile.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global.myCart == null) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context);
                    }
                    builder.setTitle("Cart Empty")
                            .setMessage("Purchase your receipe to proceed for cart..")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(R.drawable.ic_empty_cart)
                            .show();
                }
                else if(totalAmountValidate < MinOrder) {

                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context);
                    }
                    builder.setTitle("Minimum Amount not satisy for" + " " + resturant_name)
                            .setMessage("Unable to proceed please select more items..")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(R.drawable.ic_empty_cart)
                            .show();
                } else {
                    Intent io = new Intent(context, CustomerBill.class);
//                      io.putExtra("bill", (Serializable) customerBillDetailsList);
//                      Toast.makeText(context,"Hello",Toast.LENGTH_LONG).show();
                    context.startActivity(io);
                }
                //Checking with the Method FetchOrderAmount wether Order Value is Greater Than Minimum Value
            }
        });
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
                            .setIcon(R.drawable.ic_warning_icon)
                            .show();
                } else {
                    //Creating Alert Dialog Box if the order reaches to the maximum value
                    totalAmount = CalculateTotalPrice(mtem.getRate());
                    totalAmountValidate = AmountToValidateAdd(mtem.getRate());
                    //Code to validate total Order value with minimum value if order value
                    //is less than minimum order value than the color will be red or else color wiil be
                    //green
                    if (totalAmountValidate < MinOrder) {
                        ResturantProfile.totalAmount.setTextColor(Color.RED);
                    } else {
                        ResturantProfile.totalAmount.setTextColor(Color.GREEN);
                    }
                    AddToCart();
                    mtem.setCartQty(currQty + 1);
                    notifyDataSetChanged();
                    //Code to set item id  and Customer Bill Details according to items purchased by the customer
                    if (global.myCart == null) {
                        global.myCart = new HashMap<Integer, CustomerBillDetails>();
                    }
                    if (global.myCart.containsKey(mtem.getItemId())) {

                        customerBillDetails = global.myCart.get(mtem.getItemId());
                        customerBillDetails.setQuantity(mtem.getCartQty());
                    } else {
                        customerBillDetails = new CustomerBillDetails(mtem.getItemId(), resturant_id, resturant_name, mtem.getItemName(), mtem.getCartQty(), mtem.getRate(), totalAmount);
                        global.myCart.put(mtem.getItemId(), customerBillDetails);
                    }
                    //Code for validating the Minimum Order of each and every resturant
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
                    global.myCart.remove(mtem.getItemId());
                    return;
                } else if (ResturantProfile.totalAmount.getText().toString().equals("0") || ResturantProfile.QTY.getText().toString().equals("0")) {

                } else {

                    DeductTotalPrice(mtem.getRate());
                    totalAmountValidate = AmountToValidateSub(mtem.getRate());
                    //Code to validate total Order value with minimum value if order value
                    //is less than minimum order value than the color will be red or else color wiil be
                    //green
                    if (totalAmountValidate > MinOrder) {
                        ResturantProfile.totalAmount.setTextColor(Color.GREEN);
                    } else {
                        ResturantProfile.totalAmount.setTextColor(Color.RED);
                    }
                    DeductToCart();
                    mtem.setCartQty(currQty - 1);
                    notifyDataSetChanged();
                    if (global.myCart == null) {
                        global.myCart = new HashMap<Integer, CustomerBillDetails>();
                    }
                    if (global.myCart.containsKey(mtem.getItemId())) {
                        customerBillDetails = global.myCart.get(mtem.getItemId());
                        customerBillDetails.setQuantity(mtem.getCartQty());

                    } else {
                        customerBillDetails = new CustomerBillDetails(mtem.getItemId(), resturant_id, resturant_name, mtem.getItemName(), mtem.getCartQty(), mtem.getRate(), totalAmount);
                        global.myCart.put(mtem.getItemId(), customerBillDetails);
                    }
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
        addTocart = settings1.getInt("CurrentCart", 0) + 1;
        SharedPreferences.Editor editor = settings1.edit();
        editor.putInt("CurrentCart", addTocart);
        editor.commit();
        ResturantProfile.QTY.setText(String.valueOf(addTocart));
        notifyDataSetChanged();
        return addTocart;
    }

    //Method to update the total bill of the user in the bottom Navigation View
    public Integer CalculateTotalPrice(int rate) {
        //Each time user will increment the quantity of 1 so the total amount will added in
        //the variable name totalAmount
        totalAmount = totalAmount + rate * 1;
        //Storing current amount according to the items selected by the customer
        SharedPreferences.Editor editor = settings1.edit();
        editor.putInt("Total Amount", totalAmount);
        editor.commit();
        ResturantProfile.totalAmount.setText("₹" + String.valueOf(totalAmount));
        notifyDataSetChanged();
        return totalAmount;
    }

    //Method to get the order amount of the restaurant indivisually
    public Integer AmountToValidateAdd(int rate) {
        totalAmountValidate = totalAmountValidate + rate * 1;
        return totalAmountValidate;
    }

    public Integer AmountToValidateSub(int rate) {
        totalAmountValidate = totalAmountValidate - rate * 1;
        return totalAmountValidate;
    }

    public Integer DeductTotalPrice(int rate) {
        //Each time user will decrement the quantity of 1 so the total amount will be deducted in
        //the variable name totalAmount
        totalAmount = totalAmount - rate * 1;
        //Similarly when amount will be deducted it will also stored in the object of shared preferences
        SharedPreferences.Editor editor = settings1.edit();
        editor.putInt("Total Amount", totalAmount);
        editor.commit();
        ResturantProfile.totalAmount.setText("₹" + String.valueOf(totalAmount));
        notifyDataSetChanged();
        return totalAmount;
    }

    public int DeductToCart() {
        addTocart = settings1.getInt("CurrentCart", 0) - 1;
        SharedPreferences.Editor editor = settings1.edit();
        editor.putInt("CurrentCart", addTocart);
        editor.commit();
        ResturantProfile.QTY.setText(String.valueOf(addTocart));
        return addTocart;
    }
    //Getting Total Amount of Bill According to Restaurant Wise

    //Approach to get the order of the restaurant
    /*public Integer FetchOrderAmount() {

        for (Map.Entry<Integer, CustomerBillDetails> entry : global.myCart.entrySet()) {
            cc.add(entry.getValue());
        }
        for (int i = 0; i < cc.size(); i++) {
            if (cc.get(i).getResturant_id()==resturant_id)
            {
                totalSum += cc.get(i).getQuantity() * cc.get(i).getRate();
            }
        }
        return totalSum;
    }*/
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
