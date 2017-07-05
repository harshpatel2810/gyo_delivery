package com.goyo.grocery_goyo.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import com.goyo.grocery.R;
import com.goyo.grocery_goyo.AppLocationService;
import com.goyo.grocery_goyo.LocalDB.UserDbHelper;
import butterknife.ButterKnife;
/**
 * Created by Admin on 7/5/2017.
 */
public class PaymentScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent io=getIntent();
    }
}
