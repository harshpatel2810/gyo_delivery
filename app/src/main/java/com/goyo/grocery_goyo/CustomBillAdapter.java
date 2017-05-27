package com.goyo.grocery_goyo;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.goyo.grocery.R;
import java.util.ArrayList;
public class CustomBillAdapter extends BaseAdapter {
    Context context;
    Hold h1 = null;
    ArrayList<CustomerBillDetails> customerDetails;
    public CustomBillAdapter(Context context, ArrayList<CustomerBillDetails> customerDetails) {
        this.context = context;
        this.customerDetails = customerDetails;
    }
    @Override
    public int getCount() {
        return customerDetails.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = null;
        if (convertView == null) {
            mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_bill, null);
            h1 = new Hold(convertView);
            convertView.setTag(h1);
        } else {
            h1 = (Hold) convertView.getTag();
        }
        final CustomerBillDetails customerBillDetails = customerDetails.get(position);
        h1.txtResturantName.setText(customerBillDetails.getResturant_name());
        h1.txtItemName.setText(customerBillDetails.getItem_name());
        h1.txtQty.setText(String.valueOf(customerBillDetails.getQuantity()));
        h1.txtRate.setText("₹" + String.valueOf(customerBillDetails.getRate()));
        h1.txtAmount.setText(String.valueOf(customerBillDetails.getRate() * customerBillDetails.getQuantity()));
        return convertView;
    }
    public class Hold {
        private TextView txtResturantName, txtItemName, txtRate, txtQty, txtAmount;
        public Hold(View item) {
            txtResturantName = (TextView) item.findViewById(R.id.txtResturantBillName);
            txtItemName = (TextView) item.findViewById(R.id.txtItemName);
            txtRate = (TextView) item.findViewById(R.id.txtRate);
            txtQty = (TextView) item.findViewById(R.id.txtItemQuantity);
            txtAmount = (TextView) item.findViewById(R.id.txtBillAmount);
        }
    }
}

