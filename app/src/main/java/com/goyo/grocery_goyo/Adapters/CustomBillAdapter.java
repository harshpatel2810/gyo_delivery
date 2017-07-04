package com.goyo.grocery_goyo.Adapters;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.goyo.grocery.R;

import java.util.HashMap;
import java.util.List;

import com.goyo.grocery_goyo.Activity.CustomerBill;
import com.goyo.grocery_goyo.LocalDB.UserContract;
import com.goyo.grocery_goyo.LocalDB.UserDbHelper;
import com.vstechlab.easyfonts.EasyFonts;
public class CustomBillAdapter extends BaseExpandableListAdapter{
    Context context;
    HoldGroup hg = null;
    HoldChild hc = null;
    List<String> resturantNames;
    HashMap<String, List<String>> itemNames;
    HashMap<String, List<Integer>> totalQuantity;
    HashMap<String, List<Double>> totalRates;
    List<Integer> quantityTotal;
    List<Double> sumAmount;
    UserDbHelper userDbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor result;
    GroupDetails groupDetails;
    public CustomBillAdapter(Context context, List<String> resturantNames, HashMap<String, List<String>> itemNames, HashMap<String, List<Integer>> totalQuantity, HashMap<String, List<Double>> totalRates) {
        this.context = context;
        this.itemNames = itemNames;
        this.resturantNames = resturantNames;
        this.totalQuantity = totalQuantity;
        this.totalRates = totalRates;
        quantityTotal = CustomerBill.sumQuantity;
        sumAmount=CustomerBill.sumAmount;
        userDbHelper=new UserDbHelper(context);
        sqLiteDatabase=userDbHelper.getWritableDatabase();
        result=userDbHelper.GetRestuarantDetails(sqLiteDatabase);
    }
    @Override
    public int getGroupCount() {
        //return resturantNames.size();
        return result.getCount();
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.itemNames.get(this.resturantNames.get(groupPosition)).size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        //return this.resturantNames.get(groupPosition);
        result.moveToPosition(groupPosition);
        //String res=result.getString(0);
        //return res;
        GroupDetails groupDetails=new GroupDetails(result.getString(0),result.getString(1),result.getString(2));
        return groupDetails;
    }
    public Object getGroupQuantity(int groupPosition) {
        return this.quantityTotal.get(groupPosition);
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
      return this.itemNames.get(this.resturantNames.get(groupPosition)).get(childPosition);
    }
    public Object getChildQuantity(int groupPosition, int childPosition) {
        return this.totalQuantity.get(this.resturantNames.get(groupPosition)).get(childPosition);
    }

    public Object getChildRates(int groupPosition, int childPosition) {
        return this.totalRates.get(this.resturantNames.get(groupPosition)).get(childPosition);
    }
    public int getChildrenCountQuantity(int groupPosition) {
        return this.totalQuantity.get(this.resturantNames.get(groupPosition)).size();
    }

    public int getChildrenCountRates(int groupPosition) {
        return this.totalRates.get(this.resturantNames.get(groupPosition)).size();
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = null;
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_bill_group, null);
            hg = new HoldGroup(convertView);
            convertView.setTag(hg);
        } else {
            hg = (HoldGroup) convertView.getTag();
        }
        //Lines which are in commment are the coding lines getting data from arraylist and while
        //rest of them are the coding lines which brings the data from the database
        final GroupDetails res = (GroupDetails) getGroup(groupPosition);
        hg.txtResturantName.setText(res.getRes_name());
        //hg.txtItemCount.setText("Items:"+String.valueOf(quantityTotal.get(groupPosition)));
        hg.txtItemCount.setText("Items:"+res.getRes_qty());
        hg.txtAmount.setText("Amount"+" "+"₹"+String.valueOf(sumAmount.get(groupPosition)));
        userDbHelper.close();
        return convertView;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        final String quantity = String.valueOf(getChildQuantity(groupPosition, childPosition));
        final String rates = String.valueOf(getChildRates(groupPosition, childPosition));
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_bill_child, null);
            hc = new HoldChild(convertView);
            convertView.setTag(hc);
        } else {
            hc = (HoldChild) convertView.getTag();
        }
        hc.txtBillItemName.setText("Items:" + childText);
        hc.txtBillItemQuantity.setText(quantity);
        hc.txtBillItemRate.setText("₹" + rates);
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class HoldGroup {
        public HoldGroup(View item) {

            txtResturantName = (TextView) item.findViewById(R.id.txtBillResturantName);
            txtItemCount = (TextView) item.findViewById(R.id.txtItemCount);
            txtAmount=(TextView)item.findViewById(R.id.txtBillAmount);
            txtResturantName.setTypeface(EasyFonts.caviarDreamsBold(context));
        }

        private TextView txtResturantName, txtItemCount, txtAmount;
    }

    public class HoldChild {
        public HoldChild(View item) {
            txtBillItemName = (TextView) item.findViewById(R.id.txtBillItemName);
            txtBillItemQuantity = (TextView) item.findViewById(R.id.txtBillQuantity);
            txtBillItemRate = (TextView) item.findViewById(R.id.txtBillAmount);
        }

        private TextView txtBillItemName, txtBillItemQuantity, txtBillItemRate;
    }
    public class GroupDetails
    {
        private String res_name;
        private String res_qty;
        private String res_amount;
        public GroupDetails(String res_name, String res_qty,String res_amount) {
            this.res_name = res_name;
            this.res_qty = res_qty;
            this.res_amount=res_amount;
        }
        public String getRes_name() {
            return res_name;
        }
        public String getRes_qty() {
            return res_qty;
        }
    }
}


