package com.goyo.grocery_goyo;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.goyo.grocery.R;
public class SearchLocation extends AppCompatActivity{
    private Integer TRESHOLD=2;
    private DelayAutoCompleteTextView geo_autocomplete;
    private  ImageView geo_autocomplete_clear;
    //All are the libraries of Google Play Services where dependency have also been added to gradle file
    //to implement the services
    //Implementing GoogleApiClient Listeners Connec
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //INITIALIZING VIEWS FROM THE LAYOUT FILE OF ACTIVITY
        setContentView(R.layout.activity_search_location);
        geo_autocomplete_clear=(ImageView)findViewById(R.id.geo_autocomplete_text_clear);
        geo_autocomplete=(DelayAutoCompleteTextView)findViewById(R.id.txtSearchBar);
        geo_autocomplete.setThreshold(TRESHOLD);
        geo_autocomplete.setAdapter(new GeoAutoCompleteAdapter(this));
        geo_autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //Listener of auto complete textview onItemClick
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                GeoSearchResult result = (GeoSearchResult) adapterView.getItemAtPosition(position);
                geo_autocomplete.setText(result.getAddress());
                geo_autocomplete_clear.setVisibility(View.GONE);
                Intent io=new Intent(getApplicationContext(),HomeActivity.class);
                io.putExtra("SearchAddress",result.getAddress());
                startActivity(io);
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
               if(s.length()>0)
               {
                   //if there is text in autocomplete textview than the image view will be visible
                   geo_autocomplete_clear.setVisibility(View.VISIBLE);
               }
               else
               {
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
}
