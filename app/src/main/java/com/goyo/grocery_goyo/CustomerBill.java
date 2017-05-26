package com.goyo.grocery_goyo;
import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.goyo.grocery.R;

import java.util.ArrayList;
import java.util.List;
public class CustomerBill extends AppCompatActivity  {
    private ListView mListView;
    private android.support.v7.app.ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_bill);
        Intent io=getIntent();
       // customerBillDetailsList= (ArrayList<CustomerBillDetails>)io.getSerializableExtra("bill");
        mListView=(ListView)findViewById(R.id.list_display_bill);
        mListView.setAdapter(new CustomBillAdapter(CustomerBill.this,PlaceOrder.customerDetails));
        actionBar=getSupportActionBar();
        actionBar.hide();
    }
    private void setDataListItems(){

    }
}
