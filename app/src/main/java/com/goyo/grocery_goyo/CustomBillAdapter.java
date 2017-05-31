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
import com.goyo.grocery_goyo.Global.global;
import com.vstechlab.easyfonts.EasyFonts;
import de.halfbit.pinnedsection.PinnedSectionListView;
public class CustomBillAdapter extends BaseExpandableListAdapter {
    Context context;
    HoldGroup hg = null;
    HoldChild hc=null;
    List<String> resturantNames;
    HashMap<List<String>,List<CustomerBillDetails>> customerDetails;
    public CustomBillAdapter(Context context,List<String> resturantNames,HashMap<List<String>,List<CustomerBillDetails>> customerDetails) {
        this.context = context;
        this.customerDetails = customerDetails;
        this.resturantNames = resturantNames;
    }
    @Override
    public int getGroupCount() {
        return resturantNames.size();
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.customerDetails.get(this.resturantNames.get(groupPosition)).size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return this.resturantNames.get(groupPosition);
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.customerDetails.get(this.resturantNames.get(groupPosition)).get(childPosition);
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
        if(isExpanded)
        {
            convertView.setPadding(0,0,0,0);
        }
        else
        {
            convertView.setPadding(0,0,0,30);
        }
        final String res = resturantNames.get(groupPosition);
        hg.txtResturantName.setText(res.toString());
        return convertView;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.layout_bill_child,null);
        }
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    public class HoldGroup {
        public HoldGroup(View item) {

            txtResturantName = (TextView) item.findViewById(R.id.txtBillResturantName);
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


