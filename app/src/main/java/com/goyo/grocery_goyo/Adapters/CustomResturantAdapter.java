package com.goyo.grocery_goyo.Adapters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static butterknife.ButterKnife.findById;

import com.goyo.grocery.R;
import com.goyo.grocery_goyo.Activity.HomeActivity;
import com.goyo.grocery_goyo.Activity.ResturantProfile;
import com.goyo.grocery_goyo.Classes.TimeValidate;
import com.goyo.grocery_goyo.model.RestaurantsTimings;
import com.goyo.grocery_goyo.model.restaurantModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CustomResturantAdapter extends BaseAdapter {
    private final String PREF_NAME = "Resturant_id";
    public static Double MinOrder;
    private String am_pm;
    Context context;
    Holder h1;
    restaurantModel resturant;
    RestaurantsTimings resTime;
    SharedPreferences settings;
    TextView txtResturant;
    public static TimeValidate t1;
    private List<restaurantModel> x;
    private List<RestaurantsTimings> resTimings;
    private ArrayList<RestaurantsTimings> arrayresTimings;
    private ArrayList<restaurantModel> arrayRestaurantModel;
    //Created Shared Preferences at app level to store resturant_id of particular resturant
    DateFormat sdf = new SimpleDateFormat("hh:mm");
    public static String restaurantTimings;
    public CustomResturantAdapter(HomeActivity activity, List<restaurantModel> xyz, final List<RestaurantsTimings> resTimings) {
        context = activity;
        x = xyz;
        this.resTimings = resTimings;
        this.arrayRestaurantModel = new ArrayList<restaurantModel>();
        this.arrayRestaurantModel.addAll(x);
        t1=new TimeValidate(context);
        am_pm=t1.GetAmPm();
        h1 = new Holder();
        settings = context.getSharedPreferences("PREF_NAME", 0);

        //Created Listener of resturant list because resturant_id can be easily availaible from the webservice
        HomeActivity.resturant_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
               txtResturant = (TextView) view.findViewById(R.id.txtResturantName);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("Resturant_id", resturant.restid);
                MinOrder = (Double) x.get(position).min_order;
                //Initializing totalAmount variable to zero every time on the click of restaurant
                //to maintain seperate amount for validations..
                if(am_pm.equals("PM"))
                {
                   restaurantTimings=resTimings.get(position).o2.concat("-").concat(resTimings.get(position).c2);
                    //Toast.makeText(context,restaurantTimings,Toast.LENGTH_LONG).show();
                }
                else if(am_pm.equals("AM"))
                {
                   restaurantTimings=resTimings.get(position).o1.concat("-").concat(resTimings.get(position).c1);
                    //Toast.makeText(context,restaurantTimings,Toast.LENGTH_LONG).show();
                }
                editor.commit();
                Intent io = new Intent(context, ResturantProfile.class);
                io.putExtra("resturantName", txtResturant.getText().toString());
                editor.putString("Restaurant_Name",txtResturant.getText().toString());
                if(x.get(position).restname.equals(settings.getString("Restaurant_Name",null)))
                {
                    CustomMenuAdapter.totalAmountValidate=CustomMenuAdapter.totalAmountValidate;
                    Toast.makeText(context,String.valueOf(CustomMenuAdapter.totalAmountValidate),Toast.LENGTH_LONG).show();

                }
                else
                {
                    CustomMenuAdapter.totalAmountValidate=0;
                    Toast.makeText(context,String.valueOf(CustomMenuAdapter.totalAmountValidate),Toast.LENGTH_LONG).show();

                }
                editor.commit();
                context.startActivity(io);
            }

        });
    }
    public int getCount() {

        return x.size();
    }

    public class Holder {
        public
        ImageView imgDisplay;
        TextView txtRname, txtRtype, txtVegNonVeg, txtRating, txtDeliveryTime, txtMinimumOrderValue;
        TextView txtMoriningTime, txtEvening;
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
            convertView = mInflater.inflate(R.layout.layout_list_resturant, null);

        }
        h1.imgDisplay = (ImageView) convertView.findViewById(R.id.imgResturantDisplay);
        h1.txtRname = (TextView) convertView.findViewById(R.id.txtResturantName);
        h1.txtRtype = (TextView) convertView.findViewById(R.id.txtResturantType);
        h1.txtVegNonVeg = (TextView) convertView.findViewById(R.id.txtExpenseCategory);
        h1.txtRating = (TextView) convertView.findViewById(R.id.txtRating);
        h1.txtDeliveryTime = (TextView) convertView.findViewById(R.id.txtDeliveryTime);
        h1.txtMinimumOrderValue = (TextView) convertView.findViewById(R.id.txtSetMinimumPrice);
        h1.txtMoriningTime = (TextView) convertView.findViewById(R.id.txtMorningTime);
        h1.txtEvening = (TextView) convertView.findViewById(R.id.txtEveningTime);
        resturant = x.get(position);
        resTime = resTimings.get(position);
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
        h1.txtEvening.setText("Evening Time:" + resTimings.get(position).getO2() + "PM" + "  TO  " + resTimings.get(position).getC2() + "PM");
        h1.txtMoriningTime.setText("Morning Time:" + resTimings.get(position).getO1() + "AM" + "  TO  " + resTimings.get(position).getC1() + "AM");
        // GetRestaurantTimings(position);
        return convertView;
    }
    //Code to implement Search Functionality for the search of Restaurants in that particular area
    public void filter(String charText) {
        charText = charText.toString().toLowerCase(Locale.getDefault());
        x.clear();
        if (charText.length() == 0) {
            x.addAll(arrayRestaurantModel);
        } else {
            for (restaurantModel res : arrayRestaurantModel)
            {
                if (res.getRestname().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    x.add(res);
                }
            }

        }
        notifyDataSetChanged();
    }
    /*private Calendar setTimeToCalendar(String dateFormat, String date, boolean addADay) throws ParseException {
        Date time = new SimpleDateFormat(dateFormat).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        if (addADay) {
            cal.add(Calendar.DATE, 1);
        }
        return cal;
    }*/
}
//Android code to call web api to get the timings of the restaurants and set it in appropriate view.
/*
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
*/

