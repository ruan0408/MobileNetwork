package com.example.ruan0408.magnetometer1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ruan0408 on 1/09/15.
 */
public class GPSReader extends Service implements LocationListener {

    private static final long MIN_DISTANCE = 0; // 10 meters
    private static final long MIN_TIME = 0; // 10 seconds

    private Context context;
    private LocationManager locationManager;
    private Location currentLocation;

    public GPSReader(Context c) {
        context = c;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_DISTANCE, MIN_TIME, this);
        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    public float getLatitude() {
        return Double.valueOf(currentLocation.getLatitude()).floatValue();
    }

    public float getLongitude() {
        return Double.valueOf(currentLocation.getLongitude()).floatValue();
    }

    public float getAltitude() {
        return Double.valueOf(currentLocation.getAltitude()).floatValue();
    }

    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
}
