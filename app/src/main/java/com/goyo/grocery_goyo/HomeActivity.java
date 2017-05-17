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

import com.goyo.grocery.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ListView resturant_list;
    private Button search;
    private ArrayList<ResturantDetails> r1=new ArrayList<>();
    ResturantDetails resturant;
    ResturantDetails resturant1;
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
        resturant=new ResturantDetails("Punjab Resturant","North Mughlai",4.3,"31 mins");
        resturant1=new ResturantDetails("Kathiyawad Resturant","Katiyawadi",4.7,"22 mins");
        r1.add(resturant);
        r1.add(resturant1);
        resturant_list=(ListView)findViewById(R.id.list_display_resturants);
        resturant_list.setAdapter(new CustomResturantAdapter(this,r1));
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
    }

     public void InitAppBar()
    {
        ActionBar action=getSupportActionBar();
        action.setDisplayShowCustomEnabled(true);
        action.setCustomView(R.layout.layout_location_select);
        TextView label=(TextView)action.getCustomView().findViewById(R.id.txtLocation);
    }
}
