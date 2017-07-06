package com.goyo.grocery_goyo.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.goyo.grocery.R;
import com.goyo.grocery_goyo.AppLocationService;
import com.goyo.grocery_goyo.LocalDB.UserDbHelper;
import com.vstechlab.easyfonts.EasyFonts;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by Admin on 7/5/2017.
 */
public class PaymentScreen extends AppCompatActivity {
    @BindView(R.id.label_delivery_address) TextView label_delivery_address;
    @BindView(R.id.label_bill_summary) TextView label_bill_summary;
    @BindView(R.id.label_payment_mode) TextView label_payment_mode;
    @BindView(R.id.label_deals) TextView label_deals;
    @BindView(R.id.tvDisplayDeliveryAddress) TextView tvDisplayDelivery;
    UserDbHelper userDbHelper;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent io=getIntent();
        userDbHelper=new UserDbHelper(this);
        sqLiteDatabase=userDbHelper.getReadableDatabase();
        InitLabelFonts();
        DisplayDeliveryAddress();
    }
    private  void InitLabelFonts()
    {
        label_delivery_address.setTypeface(EasyFonts.robotoLight(this));
        label_bill_summary.setTypeface(EasyFonts.robotoLight(this));
        label_payment_mode.setTypeface(EasyFonts.robotoLight(this));
        label_deals.setTypeface(EasyFonts.robotoLight(this));
    }
    public void DisplayDeliveryAddress()
    {
       Cursor result=userDbHelper.GetAddressDetails(sqLiteDatabase);
        while (result.moveToNext()) {
          if(result.getString(2).equals("Home"))
          {
              tvDisplayDelivery.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_home_icon, 0, 0, 0);
          }
          else
          {
              tvDisplayDelivery.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_office_icon, 0, 0, 0);
          }
          tvDisplayDelivery.setText(result.getString(1));
      }
    }
}
