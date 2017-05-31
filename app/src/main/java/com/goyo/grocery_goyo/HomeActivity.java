package com.goyo.grocery_goyo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
import com.goyo.grocery_goyo.Global.global;
import com.goyo.grocery_goyo.model.restaurantModel;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;
public class HomeActivity extends AppCompatActivity  {
    public static ListView resturant_list;
    private Button search;
    private TextView txtLocation,txtLocDesc;
    Context context;
    public ActionBar action;
    AppLocationService appLocationService;
    SharedPreferences settings;
    Intent io;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         io=getIntent();
        appLocationService=new AppLocationService(this);
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
        context=this;
        search=(Button)findViewById(R.id.btnResturantSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent io=new Intent(context,SearchResturant.class);
                startActivity(io);
            }
        });
        global.resturantNames=new ArrayList<>();
        settings = context.getSharedPreferences("PREF_BILL", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("Total Amount",0);
        editor.putInt("CurrentCart",0);
        editor.commit();

        resturant_list=(ListView)findViewById(R.id.list_display_resturants);
        InitAppBar();
        //Helps to set the details of user current location
        if(appLocationService.getIsGPSTrackingEnabled())
        {
            String addressLine=String.valueOf(appLocationService.getLocality(this));
            txtLocation.setText(appLocationService.getAddressLine(this));
            txtLocDesc.setText(appLocationService.getAddressLine(this)+","+String.valueOf(addressLine));
        }
        else
        {
            appLocationService.showSettingsAlert();
        }
        getRestaurant();
    }
    private void getRestaurant(){
        JsonObject json = new JsonObject();
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
                        List<restaurantModel> myList = gson.fromJson(result.get("data"), new TypeToken<List<restaurantModel>>(){}.getType());
                        resturant_list.setAdapter(new CustomResturantAdapter(HomeActivity.this,myList));

                        //Toast.makeText(HomeActivity.this,result.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
    //A method to get GPS provider
     public void InitAppBar()
    {
        ActionBar action=getSupportActionBar();
        action.setDisplayShowCustomEnabled(true);
        action.setCustomView(R.layout.layout_location_select);
        //Initializing both the textView to display current Location
        txtLocation=(TextView)action.getCustomView().findViewById(R.id.txtLocation);
        txtLocDesc=(TextView)action.getCustomView().findViewById(R.id.txtLocDesc);
        //Creating Listener on main Title(txtLocation) of action bar to go in next fragment for searching location
        txtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SearchLocation.class));
                overridePendingTransition(R.anim.enter,R.anim.exit);
            }
        });
    }
    //Request to set permission runtime because of 6.0 and SecurityException Fatal Error
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    //getRestaurantMaster
    //flag = 'all'
}
