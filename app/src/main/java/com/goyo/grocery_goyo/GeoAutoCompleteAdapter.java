package com.goyo.grocery_goyo;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.location.Geocoder;

import com.goyo.grocery.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Admin on 5/23/2017.
 */

public class GeoAutoCompleteAdapter extends BaseAdapter implements Filterable {
    //This Class refers as a custom adapter which will help to add restults in auto
    //complete textview
    //it will give total 10 Results at a time
    private static final int Maximum_Results = 15;
    private Context mContext;
    //ArrayList to store the result of the suggestions
    private List resultList = new ArrayList();
    //Constructor to assign the context of the activity
    public GeoAutoCompleteAdapter(Context context) {
        mContext = context;
    }

    @Override
    //Returning list  size of the adress
    public int getCount() {
        return resultList.size();
    }

    @Override
    //returns the object of search result on particular position
    public GeoSearchResult getItem(int position) {
        return (GeoSearchResult) resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    //Method to inflate the view of the layout resource file
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.geo_search_result, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.geo_search_result_text)).setText(getItem(position).getAddress());
        return convertView;
    }

    @Override
    //method to implement the filter on the query given by the user on search of location
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List locations = findLocations(mContext, constraint.toString());
                    filterResults.values = locations;
                    filterResults.count = locations.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (results != null && results.count > 0) {
                    resultList = (List) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    //Method to find the location as per the query suggested by the user
    private List<GeoSearchResult> findLocations(Context context, String query_text) {
        List<GeoSearchResult> geoSearchResults = new ArrayList<GeoSearchResult>();
        Geocoder geocoder = new Geocoder(context, context.getResources().getConfiguration().locale);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(query_text, 15);
            for (int i = 0; i < addresses.size(); i++) {
                Address address = (Address) addresses.get(i);
                if (address.getMaxAddressLineIndex() != -1) {
                    geoSearchResults.add(new GeoSearchResult(address));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return geoSearchResults;
    }
}
