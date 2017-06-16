package com.goyo.grocery_goyo.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.goyo.grocery.R;
import com.goyo.grocery_goyo.Adapters.CustomRestaurantCategoryAdapter;
import com.goyo.grocery_goyo.model.RestaurantCategory;

import java.util.ArrayList;
public class FilterScreen extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Context context;
    private ImageButton ratingOnOff;
    private Button ApplyFilter;
    private ImageButton deliveryOnOff;
    CustomRestaurantCategoryAdapter adapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_screen);
        Intent io = getIntent();
        //Code for setting title to Action Bar and enabling setDisplayHomeAsUp Enabled
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Filter");
        actionBar.setDisplayHomeAsUpEnabled(true);
        ApplyFilter = (Button) findViewById(R.id.btnApplyFilters);
        //Creating Listener for both Rating and Delivery
        AddRatingListener();
        AddDeliveryListener();
        DisplayCategoryListView();
    }
    private void DisplayCategoryListView() {
        ArrayList<RestaurantCategory> restaurantCategories = new ArrayList<RestaurantCategory>();
        RestaurantCategory restaurantCategory = new RestaurantCategory(1, "CHINESE", false);
        restaurantCategories.add(restaurantCategory);
        restaurantCategory = new RestaurantCategory(2, "THAI", false);
        restaurantCategories.add(restaurantCategory);
        restaurantCategory = new RestaurantCategory(3, "PUNJABI", false);
        restaurantCategories.add(restaurantCategory);
        restaurantCategory = new RestaurantCategory(4, "CONTINENTAL", false);
        restaurantCategories.add(restaurantCategory);
        restaurantCategory = new RestaurantCategory(5, "MEXICAN", false);
        restaurantCategories.add(restaurantCategory);
        restaurantCategory = new RestaurantCategory(6, "ITALIAN", false);
        restaurantCategories.add(restaurantCategory);
        adapter=new CustomRestaurantCategoryAdapter(this,restaurantCategories);
        ListView listView=(ListView)findViewById(R.id.listDispCategories);
        listView.setAdapter(adapter);
    }
    private void AddDeliveryListener() {
        deliveryOnOff = (ImageButton) findViewById(R.id.ImageDisplayDelivery);
        deliveryOnOff.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_delivery_clock_off));
        deliveryOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code to change the state of the rating button by changing the icon accordingly and also to change the
                //state of button Apply Filters
                deliveryOnOff.setSelected(!deliveryOnOff.isSelected());
                if (deliveryOnOff.isSelected())
                {
                    deliveryOnOff.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_clock_delivery));
                    ratingOnOff.setEnabled(false);
                    ApplyFilter.setEnabled(true);
                }
                else
                {
                    deliveryOnOff.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_delivery_clock_off));
                    ratingOnOff.setEnabled(true);
                    ApplyFilter.setEnabled(false);
                }
            }
        });
    }
    //Code to navigate to Home Screen on Click of home button in android studio
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
        return (super.onOptionsItemSelected(menuItem));
    }
    private void AddRatingListener() {
        ratingOnOff = (ImageButton) findViewById(R.id.ImageRatingFilter);
        ratingOnOff.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_rate_off));
        ratingOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code to change the state of the rating button by changing the icon accordingly and also to change the
                //state of button Apply Filters
                ratingOnOff.setSelected(!ratingOnOff.isSelected());
                if (ratingOnOff.isSelected())
                {
                    ratingOnOff.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_rate_action));
                    deliveryOnOff.setEnabled(false);
                    ApplyFilter.setEnabled(true);
                }
                else
                {
                    ratingOnOff.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_rate_off));
                    deliveryOnOff.setEnabled(true);
                    ApplyFilter.setEnabled(false);
                }
            }
        });
    }
}
