package com.goyo.grocery_goyo.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.goyo.grocery.R;
import com.goyo.grocery_goyo.LocalDB.UserDbHelper;

/**
 * Created by Admin on 7/10/2017.
 */
public class CustomPaymentRestauranAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    Cursor result;
    UserDbHelper userDbHelper;
    SQLiteDatabase sqLiteDatabase;
    public  CustomPaymentRestauranAdapter(Context context)
    {
        this.context=context;
        userDbHelper=new UserDbHelper(context);
        sqLiteDatabase=userDbHelper.getWritableDatabase();
        result=userDbHelper.GetRestuarantDetails(sqLiteDatabase);

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.payment_restaurant_layout,parent,false);
        Holder holder=new Holder(row);
        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        result.moveToPosition(position);
        ((Holder)holder).txtRestaurantDisplay.setText(result.getString(0));
        ((Holder)holder).txtRestaurantAmount.setText("₹"+result.getString(2));

    }
    @Override
    public int getItemCount() {
        return result.getCount();
    }
    public class Holder extends RecyclerView.ViewHolder {
        TextView txtRestaurantDisplay,txtRestaurantAmount;
        public Holder(View itemView) {
            super(itemView);
            txtRestaurantDisplay=(TextView)itemView.findViewById(R.id.txtRestauarantNamePayment);
            txtRestaurantAmount=(TextView)itemView.findViewById(R.id.txtRestaurantAmountPayment);
        }
    }
}
