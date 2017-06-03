package com.goyo.grocery_goyo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.goyo.grocery.R;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Admin on 6/3/2017.
 */
public class RecyclerAdapterFilter extends RecyclerView.Adapter<RecyclerAdapterFilter.CustomViewHolder> {
    private Context context;
    public RecyclerAdapterFilter(Context mconttext)
    {
        this.context=mconttext;
    }
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType)
        {
                case 0:
                View viewOne= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_filter_rating_delivery_time,parent,false);
                CustomViewHolder rowOne=new CustomViewHolder(viewOne);

                case 2:
                View viewTwo= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_veg_dishes_filter,parent,false);
                CustomViewHolder rowTwo=new CustomViewHolder(viewTwo);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position)
    {
       final int pos=0;
    }
    @Override
    public int getItemCount()
    {
        return 0;
    }
    public int getItemViewType(int position)
    {
         return 0;
    }
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public CustomViewHolder(View itemView) {
            super(itemView);
        }
    }
}
