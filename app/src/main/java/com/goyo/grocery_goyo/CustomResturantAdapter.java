package com.goyo.grocery_goyo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private List<restaurantModel> x;
    restaurantModel resturant;
    //Created Shared Preferences at app level to store resturant_id of particular resturant
    private final String PREF_NAME="Resturant_id";
    SharedPreferences settings;
    public CustomResturantAdapter(HomeActivity activity, List<restaurantModel> xyz) {
        context = activity;
        x = xyz;
        //Created Listener of resturant list because resturant_id can be easily availaible from the webservice
        HomeActivity.resturant_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtResturant=(TextView)view.findViewById(R.id.txtResturantName);
                Intent io=new Intent(context,ResturantProfile.class);
                io.putExtra("resturantName",txtResturant.getText().toString());
                settings = context.getSharedPreferences("PREF_NAME", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("Resturant_id",resturant.restid);
                editor.commit();
                Toast.makeText(context,resturant.restid.toString(),Toast.LENGTH_LONG).show();
                context.startActivity(io);
            }
        });
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
         resturant = x.get(position);
        h1.txtRname.setText(resturant.restname);
        h1.txtRtype.setText(resturant.adr);
        h1.txtRating.setText("0");
        h1.txtDeliveryTime.setText("20");
        return convertView;
    }
}
