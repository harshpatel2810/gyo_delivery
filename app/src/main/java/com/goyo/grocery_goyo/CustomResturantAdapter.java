package com.goyo.grocery_goyo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static butterknife.ButterKnife.findById;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.grocery.R;
import com.goyo.grocery_goyo.model.restaurantModel;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
/**
 * Created by Admin on 5/12/2017.
 */
public class CustomResturantAdapter extends BaseAdapter {
    Context context;
    Holder h1;
    private List<restaurantModel> x;
    private ArrayList<restaurantModel> arrayRestaurantModel;
    restaurantModel resturant;
    //Created Shared Preferences at app level to store resturant_id of particular resturant
    private final String PREF_NAME = "Resturant_id";
    public String openingTime, closingTime, openingTime1, closingTime1;
    public static Double MinOrder;
    SharedPreferences settings;
     public int position;
    public CustomResturantAdapter(HomeActivity activity, List<restaurantModel> xyz) {
        context = activity;
        x = xyz;
        this.arrayRestaurantModel = new ArrayList<restaurantModel>();
        this.arrayRestaurantModel.addAll(x);

        //Created Listener of resturant list because resturant_id can be easily availaible from the webservice
        HomeActivity.resturant_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                TextView txtResturant = (TextView) view.findViewById(R.id.txtResturantName);
                Intent io = new Intent(context, ResturantProfile.class);
                io.putExtra("resturantName", txtResturant.getText().toString());
                settings = context.getSharedPreferences("PREF_NAME", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("Resturant_id", resturant.restid);
                MinOrder = (Double) x.get(position).min_order;
                editor.commit();
                context.startActivity(io);
            }
        });
    }

    public int getCount() {
        return x.size();
    }

    public class Holder {
        ImageView imgDisplay;
        TextView txtRname, txtRtype, txtVegNonVeg, txtRating, txtDeliveryTime, txtMinimumOrderValue;
        TextView txtMoriningTime,txtEvening;


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
        h1 = new Holder();
        //View rowView;
        LayoutInflater mInflater = null;
        if (convertView == null) {
            mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_list_resturant, null);
            GetRestaurantTimings(position);

        }
        h1.imgDisplay = (ImageView) convertView.findViewById(R.id.imgResturantDisplay);
        h1.txtRname = (TextView) convertView.findViewById(R.id.txtResturantName);
        h1.txtRtype = (TextView) convertView.findViewById(R.id.txtResturantType);
        h1.txtVegNonVeg = (TextView) convertView.findViewById(R.id.txtExpenseCategory);
        h1.txtRating = (TextView) convertView.findViewById(R.id.txtRating);
        h1.txtDeliveryTime = (TextView) convertView.findViewById(R.id.txtDeliveryTime);
        h1.txtMinimumOrderValue = (TextView) convertView.findViewById(R.id.txtSetMinimumPrice);
        h1.txtMoriningTime=(TextView)convertView.findViewById(R.id.txtMorningTime);
        h1.txtEvening=(TextView)convertView.findViewById(R.id.txtEveningTime);
        resturant = x.get(position);
        h1.txtRname.setText(x.get(position).getRestname());
        h1.txtRtype.setText(x.get(position).getAdr());
        //Below is the code
        if (x.get(position).getResttype().equals("Veg")) {
            h1.txtVegNonVeg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_veg_resturant_icon, 0, 0, 0);
        } else if (x.get(position).getResttype().equals("Veg/Nonveg")) {
            h1.txtVegNonVeg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.veg_icon_nonveg_icon, 0, 0, 0);
        }
        h1.txtRating.setText(x.get(position).getRating());
        h1.txtMinimumOrderValue.setText(String.valueOf("Minimum Order" + "  " + "₹" + x.get(position).getMin_order()));
        h1.txtDeliveryTime.setText("20");
        GetRestaurantTimings(position);
        return convertView;
    }
    //Code to implement Search Functionality for the search of Restaurants in that particular area
    public void filter(String charText) {
        charText = charText.toString().toLowerCase(Locale.getDefault());
        x.clear();
        if (charText.length() == 0) {
            x.addAll(arrayRestaurantModel);
        } else {

            for (restaurantModel res : arrayRestaurantModel) {
                if (res.getRestname().toLowerCase(Locale.getDefault()).contains(charText)) {
                    x.add(res);
                }
            }
        }
        notifyDataSetChanged();
    }
    //Android code to call web api to get the timings of the restaurants and set it in appropriate view.
    private void GetRestaurantTimings(final int position) {
        final JsonObject json = new JsonObject();
        json.addProperty("flag", "all");
        Ion.with(context)
                .load("http://35.154.230.244:8085/getRestaurantMaster")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        Gson gson = new Gson();
                          openingTime = result.get("data").getAsJsonArray().get(position).getAsJsonObject().get("tm").getAsJsonArray().get(0).getAsJsonObject().get("o1").getAsString();
                          closingTime = result.get("data").getAsJsonArray().get(position).getAsJsonObject().get("tm").getAsJsonArray().get(0).getAsJsonObject().get("c1").getAsString();
                           h1.txtMoriningTime.setText("Morning Time:"+openingTime+"AM"+" TO "+closingTime+"AM");
                          openingTime1 = result.get("data").getAsJsonArray().get(position).getAsJsonObject().get("tm").getAsJsonArray().get(0).getAsJsonObject().get("o2").getAsString();
                          closingTime1 = result.get("data").getAsJsonArray().get(position).getAsJsonObject().get("tm").getAsJsonArray().get(0).getAsJsonObject().get("c2").getAsString();
                          h1.txtEvening.setText("Evening Time:"+openingTime1+"PM"+" TO "+closingTime1+"PM");
                    }
                });
    }
}
