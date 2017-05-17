package com.goyo.grocery_goyo;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.grocery.R;
import com.goyo.grocery_goyo.model.restaurantModel;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ListView resturant_list;
    private Button search;
    Context context;
    private ActionBar action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context=this;
        search=(Button)findViewById(R.id.btnResturantSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent io=new Intent(context,SearchResturant.class);
                startActivity(io);
            }
        });
          resturant_list=(ListView)findViewById(R.id.list_display_resturants);
        resturant_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // When clicked, show a toast with the TextView text
               TextView txtResturant=(TextView)view.findViewById(R.id.txtResturantName);
                Intent io=new Intent(context,ResturantProfile.class);
                io.putExtra("resturantName",txtResturant.getText().toString());
                startActivity(io);
            }
        });
        InitAppBar();
        getRestaurant();

    }

     public void InitAppBar()
    {
        ActionBar action=getSupportActionBar();
        action.setDisplayShowCustomEnabled(true);
        action.setCustomView(R.layout.layout_location_select);
        TextView label=(TextView)action.getCustomView().findViewById(R.id.txtLocation);
    }

    //getRestaurantMaster
    //flag = 'all'
    private void getRestaurant(){
        JsonObject json = new JsonObject();
        json.addProperty("flag", "all");
        Ion.with(context)
                .load("http://192.168.1.101:3005/getRestaurantMaster")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        Gson gson = new Gson();
                        List<restaurantModel> myList = gson.fromJson(result.get("data"), new TypeToken<List<restaurantModel>>(){}.getType());
                        resturant_list.setAdapter(new CustomResturantAdapter(HomeActivity.this,myList));

                        //Toast.makeText(HomeActivity.this,result.toString(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
