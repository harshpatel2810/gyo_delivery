package com.goyo.grocery_goyo.LocalDB;
import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Admin on 6/20/2017.
 */
public class UserDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="GoYoFoodOrder.db";
    private static final int DATABASE_VERSIONS=1;
    private static final String CREATE_QUERY=
            "CREATE TABLE "+ UserContract.AddressDetails.TABLE_NAME+"("+ UserContract.AddressDetails.USER_ID+" TEXT,"+
                    UserContract.AddressDetails.USER_ADDRESS+" TEXT,"+ UserContract.AddressDetails.ADDRESS_TYPE+" TEXT);";
    public UserDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSIONS);
        Log.e("Database operations","database created / opened");
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //tables will be created in OnCreate method only
        db.execSQL(CREATE_QUERY);
        Log.e("Database operations","Table created....");
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
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }
}
