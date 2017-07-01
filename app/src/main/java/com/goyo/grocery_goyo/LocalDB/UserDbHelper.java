package com.goyo.grocery_goyo.LocalDB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.goyo.grocery_goyo.Activity.HomeActivity;
/**
 * Created by Admin on 6/20/2017.
 */
public class UserDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="GoYoFoodOrder.db";
    private static final int DATABASE_VERSIONS=1;
    private static final String CREATE_ADDRESS_QUERY=
            "CREATE TABLE "+ UserContract.AddressDetails.TABLE_NAME+"("+ UserContract.AddressDetails.USER_ID+" TEXT,"+
                    UserContract.AddressDetails.USER_ADDRESS+" TEXT,"+ UserContract.AddressDetails.ADDRESS_TYPE+" TEXT);";

    private static final String CREATE_CART=
            "CREATE TABLE "+ UserContract.CartDetails.TABLE_NAME+"("+ UserContract.CartDetails.USER_ID+" TEXT,"+
                    UserContract.CartDetails.RES_ID+" INTEGER,"+
                    UserContract.CartDetails.RES_NAME+" TEXT,"+
                    UserContract.CartDetails.ITEM_ID+" INTEGER,"+
                    UserContract.CartDetails.ITEM_NAME+" TEXT,"
                    +UserContract.CartDetails.ITEM_QTY+" INTEGER,"
                    + UserContract.CartDetails.ITEM_PRICE+" INTEGER);";

    public UserDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSIONS);
        Log.e("Database operations","database created / opened");

    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //tables will be created in OnCreate method only
        db.execSQL(CREATE_ADDRESS_QUERY);
        db.execSQL(CREATE_CART);
        Log.e("Database operations","Table created....");
    }
    public  void DeleteDetails(SQLiteDatabase db)
    {
        db.execSQL("DELETE FROM "+UserContract.AddressDetails.TABLE_NAME);
    }
    public void addAddressDetails(String user_id,String address,String address_type,SQLiteDatabase db)
    {
        //Helps to put the value in key-value format so for that Content Value Class is to be created
        ContentValues cn=new ContentValues();
        cn.put(UserContract.AddressDetails.USER_ID,user_id);
        cn.put(UserContract.AddressDetails.USER_ADDRESS,address);
        cn.put(UserContract.AddressDetails.ADDRESS_TYPE,address_type);
        db.insert(UserContract.AddressDetails.TABLE_NAME,null,cn);
        Log.e("Database operations","One Row is inserted");
    }
    public void addCartItems(String user_id,int res_id,String res_name,int item_id,String item_name,int qty,int price,SQLiteDatabase db)
    {
        ContentValues cn=new ContentValues();
        cn.put(UserContract.CartDetails.USER_ID,user_id);
        cn.put(UserContract.CartDetails.RES_ID,res_id);
        cn.put(UserContract.CartDetails.RES_NAME,res_name);
        cn.put(UserContract.CartDetails.ITEM_ID,item_id);
        cn.put(UserContract.CartDetails.ITEM_NAME,item_name);
        cn.put(UserContract.CartDetails.ITEM_PRICE,price);
        db.insert(UserContract.CartDetails.TABLE_NAME,null,cn);
    }
    public Cursor GetAddressDetails(SQLiteDatabase db)
    {
        String query="SELECT * FROM "+UserContract.AddressDetails.TABLE_NAME+" WHERE "+UserContract.AddressDetails.USER_ID+" ='"+HomeActivity.unique_id+"'";
        db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        return cursor;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.AddressDetails.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.CartDetails.TABLE_NAME);
        onCreate(db);
    }
}
