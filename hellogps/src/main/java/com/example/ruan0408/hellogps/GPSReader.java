package com.example.ruan0408.hellogps;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

	
public class GPSReader extends Service implements LocationListener {

	private Context context;
	private LocationManager locationManager;
	private Location lastKnownLocation;
	
	public GPSReader(Context c) {
		context = c;
		locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		lastKnownLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
	}
	
	public LocationManager getLocationManager() {
		return locationManager;
	}
	
	public Location getLastKnownLocation() {
		return lastKnownLocation;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		lastKnownLocation = location;

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
