package com.goyo.grocery_goyo;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.goyo.grocery.R;
import com.goyo.grocery_goyo.Activity.HomeActivity;

import java.net.HttpURLConnection;
import java.net.URL;

public class SearchLocation extends AppCompatActivity {
    private URL url;
    public static String address=null;
    private HttpURLConnection hc;
    private Integer TRESHOLD = 2;
    private DelayAutoCompleteTextView geo_autocomplete;
    private ImageView geo_autocomplete_clear;
    double new_latitude;
    double new_longtitude;
    Intent io;
    GeoSearchResult georesult;
    //All are the libraries of Google Play Services where dependency have also been added to gradle file
    //to implement the services
    //Implementing GoogleApiClient Listeners Connec
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //INITIALIZING VIEWS FROM THE LAYOUT FILE OF ACTIVITY
        setContentView(R.layout.activity_search_location);
        //Code to add Strict Thread Policy because NetworkMainThread Runtime Exception
          StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
        geo_autocomplete_clear = (ImageView) findViewById(R.id.geo_autocomplete_text_clear);
        geo_autocomplete = (DelayAutoCompleteTextView) findViewById(R.id.txtSearchBar);
        geo_autocomplete.setThreshold(TRESHOLD);
        geo_autocomplete.setAdapter(new GeoAutoCompleteAdapter(this));
        geo_autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //Listener of auto complete textview onItemClick
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                georesult = (GeoSearchResult) adapterView.getItemAtPosition(position);
                geo_autocomplete.setText(georesult.getAddress());
                geo_autocomplete_clear.setVisibility(View.GONE);
                // Intent io=new Intent(getApplicationContext(),HomeActivity.class);
                //io.putExtra("SearchAddress",georesult.getAddress());
                //startActivity(io);
               /* try {
                    GetNewLatLong();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                 address = georesult.getAddress();
                GeoCodingLocation locationAddress = new GeoCodingLocation();
                locationAddress.getAddressFromLocation(address, getApplicationContext(), new GeocoderHandler());

            }
        });
        //Text Change Listener to AutoComplete TextView
        geo_autocomplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            //Some functionality provided in the method
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    //if there is text in autocomplete textview than the image view will be visible
                    geo_autocomplete_clear.setVisibility(View.VISIBLE);
                } else {
                    //if auto complete text view is clear than the image view will be invisible
                    geo_autocomplete_clear.setVisibility(View.GONE);
                }
            }
        });
        //listener of image view to clear the text
        geo_autocomplete_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geo_autocomplete.setText("");
            }
        });
    }

    //Approach To get the Latitude and Longtitude of the location  selected by the user by it is only application when name
    //is single if there is detailed address than it is not applicable
    /*private void GetNewLatLong() throws JSONException {
        String response="";
        try {
             url=new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+georesult.getAddress()+"&key=AIzaSyAXJbhK_apLxdfAqe3kvcW0LpVppuEehXQ");
             hc=(HttpURLConnection) url.openConnection();
            hc.setConnectTimeout(15000);
            hc.setReadTimeout(15000);
            hc.setRequestMethod("GET");
            hc.setDoInput(true);
            hc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            hc.setDoOutput(true);
            int res_code=hc.getResponseCode();
            if (res_code == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
                br.close();
            }
            else
            {
                response = "";
            }
            JSONObject jsonObject1 = new JSONObject(response.toString());
            double lng = ((JSONArray)jsonObject1.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");
            Toast.makeText(this,String.valueOf(lng),Toast.LENGTH_LONG).show();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }*/
    //Approach to get Latitude and Longtitude of each and every place selected in the search bar
    public class GeocoderHandler extends Handler {
         Intent io;
        public void handleMessage(Message message) {
            String locationAddress,area=null,addressLine=null;


            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    new_latitude = bundle.getDouble("lat");
                    new_longtitude = bundle.getDouble("long");
                    area=bundle.getString("area");
                    addressLine=bundle.getString("addressLine");
                    io=new Intent(getApplicationContext(),HomeActivity.class);
                    io.putExtra("Area",area);
                    io.putExtra("AddressLine",addressLine);
                    startActivity(io);
                    break;
                default:
                    locationAddress = null;
                    new_latitude = 0.0;
                    new_longtitude = 0.0;
                    area=null;
                    addressLine=null;
            }
            Toast.makeText(getApplicationContext(),"Lat--->"+String.valueOf(new_latitude)+""+"Long--->"+String.valueOf(new_longtitude),Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),addressLine,Toast.LENGTH_LONG).show();
         }
    }
}
