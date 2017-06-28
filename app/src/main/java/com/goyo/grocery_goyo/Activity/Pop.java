package com.goyo.grocery_goyo.Activity;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.EditText;

import com.goyo.grocery.R;
import com.goyo.grocery_goyo.AppLocationService;

import butterknife.BindView;
public class Pop extends AppCompatActivity {
   private EditText ettxtAddressStreet;
    AppLocationService appLocationService;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.layout_delivery_address);
         ettxtAddressStreet=(EditText)findViewById(R.id.txtAddressStreet);
         appLocationService=new AppLocationService(this);
         DisplayMetrics dm=new DisplayMetrics();
         getWindowManager().getDefaultDisplay().getMetrics(dm);
         int width=dm.widthPixels;
         int height=dm.heightPixels;
         getWindow().setLayout((int)(width*.8),(int)(height*.6));
         getSupportActionBar().hide();
         ettxtAddressStreet.setText(appLocationService.getAddressLine(this));

    }
}
