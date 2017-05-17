package com.goyo.grocery_goyo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goyo.grocery.R;
import com.goyo.grocery_goyo.model.restaurantModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/12/2017.
 */

public class CustomResturantAdapter extends BaseAdapter {
    Context context;
    String abc[];
    private List<restaurantModel> x;
    public CustomResturantAdapter(HomeActivity activity,List<restaurantModel> xyz) {
        context = activity;
        x=xyz;
    }

    public int getCount() {
        return x.size();
    }

    public class Holder {
        ImageView imgDisplay;
        TextView txtRname, txtRtype, txtVegNonVeg, txtRating, txtDeliveryTime;
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
        Holder h1 = new Holder();
        //View rowView;
        LayoutInflater mInflater=null;
        if (convertView == null) {
             mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.layout_list_resturant, null);
        }
        h1.imgDisplay = (ImageView)convertView.findViewById(R.id.ImageResturants);
        h1.txtRname = (TextView) convertView.findViewById(R.id.txtResturantName);
        h1.txtRtype = (TextView) convertView.findViewById(R.id.txtResturantType);
        h1.txtVegNonVeg = (TextView)convertView.findViewById(R.id.txtExpenseCategory);
        h1.txtRating = (TextView)convertView.findViewById(R.id.txtRating);
        h1.txtDeliveryTime = (TextView)convertView.findViewById(R.id.txtDeliveryTime);
        restaurantModel resturant=x.get(position);
        h1.txtRname.setText(resturant.restname);
        h1.txtRtype.setText(resturant.restname);
        h1.txtRating.setText("0");
        h1.txtDeliveryTime.setText("20");
        return convertView;
    }
}
