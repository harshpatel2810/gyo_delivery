package com.goyo.grocery_goyo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

/**
 * Created by Admin on 5/13/2017.
 */

public class CustomMenuAdapter extends BaseAdapter {
    Context context;
    private ArrayList<MenuItems> dataList;
    private   Integer addTocart=0;
    ImageButton addQty, subQty;
    private ImageButton btnAddQty, btnsubQty;
private  BottomNavigationView navigationView;
    //HashMap<String,Integer> positiveNumbers=new HashMap<String,Integer>();
    public CustomMenuAdapter(Context activity, ArrayList<MenuItems> xyz) {
        context = activity;
        dataList = xyz;


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

        final Holder h1;

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
        navigationView=(BottomNavigationView)convertView.findViewById(R.id.btmNavCart);
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
                mtem.setCartQty(currQty + 1);
                AddToCart();
                notifyDataSetChanged();
            }
        });
        btnsubQty.setTag(position + "");
        btnsubQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer currQty = mtem.getCartQty();
                if(currQty == 0){
                    Toast.makeText(context, "Qty cannot be less than 0",Toast.LENGTH_SHORT).show();
                    return;
                }
                mtem.setCartQty(currQty - 1);
                DeductToCart();
                notifyDataSetChanged();
            }
        });
        h1.txtQty.setText(mtem.getCartQty() + "");
        h1.textItemName.setText(mtem.getItemName());
        h1.textMenuPrice.setText(String.valueOf(mtem.getRate()));
        if(h1.textMenuDesc.getText().toString().trim().equals("")){
            h1.textMenuDesc.setVisibility(View.GONE);
        }

        h1.textMenuDesc.setText(mtem.getMenuDesc());
        /*if(TextUtils.isEmpty(h1.textMenuDesc.getText().toString()))
        {
            h1.textMenuDesc.setEnabled(false);
        }*/

        return convertView;
    }
    public void AddToCart()
    {

         addTocart=addTocart+1;
         ResturantProfile.QTY.setText(String.valueOf(addTocart));
         notifyDataSetChanged();
    }
    public int DeductToCart()
    {
        addTocart=addTocart-1;
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
