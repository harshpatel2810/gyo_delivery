package com.goyo.grocery_goyo.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.goyo.grocery.R;
import com.goyo.grocery_goyo.AppLocationService;
import com.goyo.grocery_goyo.LocalDB.UserDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
public class Pop extends AppCompatActivity{
     @BindView(R.id.txtAddressStreet)EditText ettxtAddressStreet;
     @BindView(R.id.etFlatBuilding) EditText etFlatName;
     @BindView(R.id.etLandmark) EditText etLandmark;
     @BindView(R.id.btnnEditStreet) ImageButton btnEditStreet;
     @BindView(R.id.btnSaveAddress)  Button btnSaveAddress;
     @BindView(R.id.radioAddressType) RadioGroup radioAddressType;
     DisplayMetrics dm;
     AppLocationService appLocationService;
     Context context=this;
     UserDbHelper userDbHelper;
     SQLiteDatabase sqLiteDatabase;
     private  StringBuffer useraddress;
     private String addressType;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.layout_delivery_address);
         useraddress=new StringBuffer();
         ButterKnife.bind(this);
         appLocationService=new AppLocationService(this);
         dm=new DisplayMetrics();
         getWindowManager().getDefaultDisplay().getMetrics(dm);
         int width=dm.widthPixels;
         int height=dm.heightPixels;
         getWindow().setLayout((int)(width*.8),(int)(height*.6));
         getSupportActionBar().hide();
         ettxtAddressStreet.setText(appLocationService.getAddressLine(this));
         btnEditStreet.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ettxtAddressStreet.getText().clear();
                 ettxtAddressStreet.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
             }
         });
         btnSaveAddress.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               if(etFlatName.getText().toString().isEmpty())
               {
                   etFlatName.setError("Enter Building Details");
               }
               else if(etLandmark.getText().toString().isEmpty())
               {
                   etLandmark.setError("Enter Landmark");
               }
               else if(etLandmark.getText().toString().isEmpty() || etFlatName.getText().toString().isEmpty())
               {
                   etFlatName.setError("Enter Building Details");
                   etLandmark.setError("Enter Landmark");
               }
               else
               {
                   final String addressType = ((RadioButton)findViewById(radioAddressType.getCheckedRadioButtonId())).getText().toString();
                   useraddress.setLength(0);
                   useraddress.append(etFlatName.getText().toString());
                   useraddress.append(" "+ettxtAddressStreet.getText().toString());
                   useraddress.append(" "+etLandmark.getText().toString());
                   useraddress.append(" "+appLocationService.getLocality(getBaseContext()).toString());
                   userDbHelper=new UserDbHelper(context);
                   sqLiteDatabase=userDbHelper.getWritableDatabase();
                   userDbHelper.addAddressDetails(HomeActivity.unique_id,useraddress.toString(),addressType,sqLiteDatabase);
                   Toast.makeText(getBaseContext(),"Data Saved Successfully",Toast.LENGTH_LONG).show();
                   Cursor re=userDbHelper.GetAddressDetails(sqLiteDatabase);
                   if(re.getCount()==0)
                   {
                       return;
                   }
                   StringBuffer stringBuffer=new StringBuffer();
                   while (re.moveToNext())
                   {
                       stringBuffer.setLength(0);
                       stringBuffer.append("Delivery Address"+re.getString(1));
                       Toast.makeText(context,stringBuffer.toString(),Toast.LENGTH_LONG).show();
                   }
                   userDbHelper.close();
               }
             }
         });
    }
}
