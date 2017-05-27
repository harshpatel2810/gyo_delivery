package com.goyo.grocery_goyo;
import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.goyo.grocery.R;
import com.goyo.grocery_goyo.Global.global;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        ArrayList<CustomerBillDetails> cc= new ArrayList<>();

        for (Map.Entry<Integer, CustomerBillDetails> entry : global.myCart.entrySet())
        {

            cc.add(entry.getValue());
            //System.out.println(entry.getKey() + "/" + entry.getValue());
        }

        mListView.setAdapter(new CustomBillAdapter(CustomerBill.this, cc));
        actionBar=getSupportActionBar();
        actionBar.hide();
    }
    private void setDataListItems(){

    }
}
