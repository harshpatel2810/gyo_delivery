package com.goyo.grocery_goyo;
import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.goyo.grocery.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.goyo.grocery_goyo.Global.global;
import com.vstechlab.easyfonts.EasyFonts;
import de.halfbit.pinnedsection.PinnedSectionListView;
public class CustomBillAdapter extends BaseExpandableListAdapter {
    Context context;
    HoldGroup hg = null;
    HoldChild hc=null;
    List<String> resturantNames;
    HashMap<String,List<String>>  itemNames;
    HashMap<String,List<Integer>> totalQuantity;
    HashMap<String,List<Double>>  totalRates;
    List<Integer> quantityTotal;
   public CustomBillAdapter(Context context,List<String> resturantNames,HashMap<String,List<String>> itemNames,HashMap<String,List<Integer>> totalQuantity,HashMap<String,List<Double>> totalRates) {
        this.context = context;
        this.itemNames = itemNames;
        this.resturantNames = resturantNames;
        this.totalQuantity=totalQuantity;
        this.totalRates=totalRates;

    }
    @Override
    public int getGroupCount() {
        return resturantNames.size();
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.itemNames.get(this.resturantNames.get(groupPosition)).size();
    }
    @Override
    public Object getGroup(int groupPosition)
    {
        return this.resturantNames.get(groupPosition);
    }
    public Object getGroupQuantity(int groupPosition)
    {
        return this.quantityTotal.get(groupPosition);
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.itemNames.get(this.resturantNames.get(groupPosition)).get(childPosition);
    }
    public Object getChildQuantity(int groupPosition, int childPosition)
    {
        return this.totalQuantity.get(this.resturantNames.get(groupPosition)).get(childPosition);
    }
    public Object getChildRates(int groupPosition, int childPosition)
    {
        return this.totalRates.get(this.resturantNames.get(groupPosition)).get(childPosition);
    }
    public int getChildrenCountQuantity(int groupPosition)
    {
        return this.totalQuantity.get(this.resturantNames.get(groupPosition)).size();
    }
    public int getChildrenCountRates(int groupPosition)
    {
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
        } else
        {
            hg = (HoldGroup) convertView.getTag();
        }
        if(isExpanded)
        {
            convertView.setPadding(0,0,0,0);
        }
        else
        {
            convertView.setPadding(0,0,0,30);
        }

        final String res = resturantNames.get(groupPosition);
        hg.txtResturantName.setText(res);
        return convertView;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String)getChild(groupPosition, childPosition);
        final String quantity=String.valueOf(getChildQuantity(groupPosition,childPosition));
        final String rates=String.valueOf(getChildRates(groupPosition,childPosition));
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.layout_bill_child,null);
           hc=new HoldChild(convertView);
            convertView.setTag(hc);
        }
        else
        {
            hc=(HoldChild)convertView.getTag();
        }
        hc.txtBillItemName.setText("Items:"+childText);
        hc.txtBillItemQuantity.setText(quantity);
        hc.txtBillItemRate.setText("₹"+rates);
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
}


