package com.goyo.grocery_goyo;
import android.content.Context;
import android.os.Handler;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
public class GeoCodingLocation
{
    private static final String TAG = "GeocodingLocation";
    public static void getAddressFromLocation(final String locationAddress, final Context context, final Handler handler) {
       Thread thread=new Thread()
       {
           public void run()
           {
               Geocoder geocoder=new Geocoder(context,Locale.getDefault());
               String result=null;
               double latitude=0.0;
               String area=null,addressLine=null;
               double longtitude=0.0;
               try
               {
                 List addresslist=geocoder.getFromLocationName(locationAddress,1);
                   if(addresslist!=null && addresslist.size()>0)
                   {
                       Address address = (Address)addresslist.get(0);
                       StringBuilder sb=new StringBuilder();
                       sb.append(address.getLatitude()).append("\n");
                       sb.append(address.getLongitude()).append("\n");
                       result = sb.toString();
                       latitude=address.getLatitude();
                       longtitude=address.getLongitude();
                       area=address.getFeatureName();
                       addressLine=address.getAddressLine(1);
                   }
               }
               catch (IOException i)
               {
                   Log.i(TAG, "Unable to connect to Geocoder", i);
               }
               finally {
                   {
                       Message message=Message.obtain();
                       message.setTarget(handler);
                       if (result != null) {
                           message.what = 1;
                           Bundle bundle = new Bundle();
                           result = "Address: " + locationAddress +
                                   "\n\nLatitude and Longitude :\n" + result;
                           bundle.putString("address", result);
                           bundle.putDouble("lat",latitude);
                           bundle.putDouble("long",longtitude);
                           bundle.putString("area",area);
                           bundle.putString("addressLine",addressLine);
                           message.setData(bundle);
                       } else
                       {
                           message.what = 1;
                           Bundle bundle = new Bundle();
                           result = "Address: " + locationAddress +
                                   "\n Unable to get Latitude and Longitude for this address location.";
                           bundle.putString("address", result);
                           bundle.putDouble("lat",0.0);
                           bundle.putDouble("long",0.0);
                           bundle.putString("area",null);
                           bundle.putString("addressLine",addressLine);
                           message.setData(bundle);
                       }
                       message.sendToTarget();
                   }
               }
           }
       };
       thread.start();
    }
}
