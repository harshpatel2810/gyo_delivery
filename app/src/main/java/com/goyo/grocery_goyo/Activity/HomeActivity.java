package com.goyo.grocery_goyo.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.grocery.R;
import com.goyo.grocery_goyo.Adapters.CustomResturantAdapter;
import com.goyo.grocery_goyo.AppLocationService;
import com.goyo.grocery_goyo.Global.global;
import com.goyo.grocery_goyo.LocalDB.UserDbHelper;
import com.goyo.grocery_goyo.SearchLocation;
import com.goyo.grocery_goyo.model.RestaurantsTimings;
import com.goyo.grocery_goyo.model.restaurantModel;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public static ListView resturant_list;
    public static String unique_id;
    private Button search,btn_refresh;
    private ImageView filterOption;
    public TextView txtLocation, txtLocDesc;
    private LinearLayout layout_location;
    private SearchView etSearchRestaurants;
    RestaurantsTimings resTime;
    Context context;
    public ActionBar action;
    String addressLine, newAddress;
    AppLocationService appLocationService;
    SharedPreferences settings;
    CustomResturantAdapter resturantAdapter = null;
    List<restaurantModel> myList;
    List<RestaurantsTimings> resTimings;
    public String c1, c2, o1, o2;
    Intent io;
    UserDbHelper userDbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        unique_id=UUID.randomUUID().toString().replace("-","");
        context = this;
        if(haveNetworkConnection(HomeActivity.this)==false)
        {
           // showInternetAlertDialog(HomeActivity.this).show();
            View view=getLayoutInflater().inflate(R.layout.layout_no_internet_resource_1,null);
            setContentView(view);
            Button btn=(Button)view.findViewById(R.id.btn_refresh);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(haveNetworkConnection(context)==true)
                    {
                        startActivity(new Intent(context,HomeActivity.class));
                    }
                }
            });
         }
        else
        {
            userDbHelper =new UserDbHelper(this);
            db=userDbHelper.getWritableDatabase();
            userDbHelper.DeleteDetails(db);
            io = getIntent();
            newAddress = SearchLocation.address;
            appLocationService = new AppLocationService(this);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            filterOption = (ImageView) findViewById(R.id.imageFilter);
            filterOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), FilterScreen.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            });
            etSearchRestaurants = (SearchView) findViewById(R.id.searchRestaurants);
            global.resturantNames = new ArrayList<>();
            resTimings = new ArrayList<RestaurantsTimings>();
            settings = context.getSharedPreferences("PREF_BILL", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("Total Amount", 0);
            editor.putInt("CurrentCart", 0);
            editor.commit();
            resturant_list = (ListView) findViewById(R.id.list_display_resturants);
            InitAppBar();
            //Helps to set the details of user current location
            if (appLocationService.getIsGPSTrackingEnabled()) {
                if (newAddress == null) {
                    addressLine = String.valueOf(appLocationService.getLocality(this));
                    txtLocation.setText(appLocationService.getAddressLine(this));
                    txtLocDesc.setText(appLocationService.getAddressLine(this) + "," + String.valueOf(addressLine));
                } else {
                    txtLocation.setText(io.getStringExtra("Area"));
                    txtLocDesc.setText(io.getStringExtra("AddressLine"));
                }
            } else {
                appLocationService.showSettingsAlert();
            }
            getRestaurant();
            etSearchRestaurants.setOnQueryTextListener(this);
        }
   }
    private void getRestaurant() {
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
                        if (result != null) {
                            Gson gson = new Gson();
                            myList = gson.fromJson(result.get("data"), new TypeToken<List<restaurantModel>>() {
                            }.getType());
                            for (int i = 0; i < myList.size(); i++) {
                                // Toast.makeText(context, result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("tm").getAsJsonArray().get(0).getAsJsonObject().toString(), Toast.LENGTH_LONG).show();
                                // Toast.makeText(context, result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("tm").getAsJsonArray().toString(), Toast.LENGTH_LONG).show();
                                // Toast.makeText(context, result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("tm").getAsJsonArray().get(0).getAsJsonObject().get("c1").getAsString(), Toast.LENGTH_LONG).show();
                                c1 = result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("tm").getAsJsonArray().get(0).getAsJsonObject().get("c1").getAsString();
                                c2 = result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("tm").getAsJsonArray().get(0).getAsJsonObject().get("c2").getAsString();
                                o1 = result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("tm").getAsJsonArray().get(0).getAsJsonObject().get("o1").getAsString();
                                o2 = result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("tm").getAsJsonArray().get(0).getAsJsonObject().get("o2").getAsString();
                                resTime = new RestaurantsTimings(c1, c2, o1, o2);
                                resTimings.add(resTime);
                            }
                            resturantAdapter = new CustomResturantAdapter(HomeActivity.this, myList,resTimings);
                            resturant_list.setAdapter(resturantAdapter);
                        }
                    }
                });
    }
    //A method to get GPS provider
    public void InitAppBar() {
        ActionBar action = getSupportActionBar();
        action.setDisplayShowCustomEnabled(true);
        action.setCustomView(R.layout.layout_location_select);
        //Initializing both the textView to display current Location
        txtLocation = (TextView) action.getCustomView().findViewById(R.id.txtLocation);
        txtLocDesc = (TextView) action.getCustomView().findViewById(R.id.txtLocDesc);
        layout_location = (LinearLayout) action.getCustomView().findViewById(R.id.layout_location_select);
        //Creating Listener of Layout of action bar to go in next fragment for searching location
        layout_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchLocation.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }
    //Request to set permission runtime because of 6.0 and SecurityException Fatal Error
   /* public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
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
*/
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
            resturantAdapter.filter(text);
        return false;
    }
    //getRestaurantMaster
    //flag = 'all'
    private  boolean haveNetworkConnection(Context context)
    {
        ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo=cm.getActiveNetworkInfo();
        if(netinfo!=null && netinfo.isConnectedOrConnecting())
        {
            android.net.NetworkInfo wifi=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if((mobile!=null && mobile.isConnectedOrConnecting()) || (wifi!=null && wifi.isConnectedOrConnecting()))
            {
              return true;
            }
            else
            {
                return false;
            }
        }
        else
        return false;
    }
    private AlertDialog.Builder showInternetAlertDialog(Context c)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Application Requires Internet Connection....")
                .setCancelable(false)
                .setPositiveButton("Connect to Wifi or Mobile Internet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });
              /*  .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(1);
                    }
                });
              */return builder;
    }
}

