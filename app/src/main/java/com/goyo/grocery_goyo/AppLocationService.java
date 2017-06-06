package com.goyo.grocery_goyo;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.location.Geocoder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.goyo.grocery.R;

import java.io.IOException;
import java.security.SecurityPermission;
import java.util.List;
import java.util.Locale;

public class AppLocationService extends Service implements LocationListener {
    //Basically it is a class which helps to get the current location of the user
    //even we can also able to get current Latitude and Longtitude of the user
    private static String TAG = AppLocationService.class.getName();
    private final Context mContext;
    //It will check wether GPS is enabled
    boolean isGpsEnabled = false;
    //It will check for Network Availibility
    boolean isNetworkEnabled = false;
    //check for GPSTrackingEnabled
    boolean isGPSTrackingEnabled;
    Location location;
    double latitude;
    double longtitude;
    int geocoderMaxResults = 1;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;
    private String provider_info;
    public AppLocationService(Context context) {
        this.mContext = context;
        getLocation();
    }
    //Helps to get the Location by checking if GPS and Network is Enabled
    public void getLocation() throws SecurityException {
        try {
            //To get Service for Location
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            //Getting GPS Status wether GPS is enabled or not
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            //Getting Network Status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGpsEnabled) {
                isGPSTrackingEnabled = true;
                Log.d(TAG, "Application Uses GPS Service");
                provider_info = LocationManager.GPS_PROVIDER;
            } else if (isNetworkEnabled) {
                // Try to get location if you Network Service is enabled
                this.isGPSTrackingEnabled = true;
                Log.d(TAG, "Application use Network State to get GPS coordinates");
                provider_info = LocationManager.NETWORK_PROVIDER;
            }
            if (!provider_info.isEmpty()) {
                locationManager.requestLocationUpdates(provider_info, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            }
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(provider_info);
                updateCoordinates();
            }
        } catch (Exception e) {
            Log.e(TAG, "Impossible to connect to LocationManager", e);
        }
    }

    public void updateCoordinates() {
        if (location != null) {
            latitude = location.getLatitude();
            longtitude = location.getLongitude();
        }
    }

    //Helps to get the Latitude
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longtitude = location.getLongitude();
        }
        return longtitude;
    }

    //Helps to stop using GPS
    public void StopUsingGps() {
        if (locationManager != null) {
            locationManager.removeUpdates(AppLocationService.this);
        }
    }

    public boolean getIsGPSTrackingEnabled() {

        return this.isGPSTrackingEnabled;
    }

    //Help to get the Adress of the Current Location
    public List<Address> getGeoDecoderAdress(Context cxt) {
        if (location != null) {
            Geocoder geoCoder = new Geocoder(cxt, Locale.ENGLISH);
            try {
                List<Address> addresses = geoCoder.getFromLocation(latitude, longtitude, this.geocoderMaxResults);
                return addresses;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //Method of Alert Dialog box if the GPS is not enabled
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        //It indicates title of the Alert Dialog Box
        alertDialog.setTitle(R.string.GPSTitleAlert);

        //Show the message of the Alert Dialog Box
        alertDialog.setMessage(R.string.GPSMessage);

        //On Pressing Setting button
        alertDialog.setPositiveButton(R.string.action_settings, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        //On pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public String SubAdminArea(Context context) {
        List<Address> addresses = getGeoDecoderAdress(context);
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String addressLine = address.getSubAdminArea();
            return addressLine;
        } else {
            return null;
        }
    }

    //Helps to get the adress line of the location
    public String getAddressLine(Context context) {
        List<Address> addresses = getGeoDecoderAdress(context);
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String addressLine = address.getAddressLine(0);
            return addressLine;
        } else {
            return null;
        }
    }

    //Helps to get the Locality of the Location
    public String getLocality(Context context) {
        List<Address> addresses = getGeoDecoderAdress(context);
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String locality = address.getLocality();
            return locality;
        } else {
            return null;
        }
    }

    //All kinds of listeners related to GPS Services
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
