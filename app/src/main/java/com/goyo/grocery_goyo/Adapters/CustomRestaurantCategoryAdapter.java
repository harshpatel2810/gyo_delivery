package com.goyo.grocery_goyo.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.goyo.grocery.R;
import com.goyo.grocery_goyo.model.RestaurantCategory;
import java.util.ArrayList;
/**
 * Created by Admin on 6/7/2017.
 */
public class CustomRestaurantCategoryAdapter extends BaseAdapter {
    Context context;
    ViewHolder h1 = null;
    ArrayList<RestaurantCategory> restaurantCategories;
    public CustomRestaurantCategoryAdapter(Context context, ArrayList<RestaurantCategory> restaurantCategories) {
        this.context = context;
        this.restaurantCategories = restaurantCategories;
    }
    @Override
    public int getCount() {
        return restaurantCategories.size();
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
        LayoutInflater inflater = null;
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_restaurant_category_list, null);
            h1 = new ViewHolder(convertView);
            convertView.setTag(h1);
        } else {
            h1 = (ViewHolder) convertView.getTag();
        }
        h1.category.setText(restaurantCategories.get(position).getCategory_name());
        return convertView;
    }
    private class ViewHolder {
        TextView txtName;
        CheckBox category;
        public ViewHolder(View item) {
            txtName = (TextView) item.findViewById(R.id.chkCategoryDisplay);
            category=(CheckBox)item.findViewById(R.id.chkCategory);
        }
    }
}
