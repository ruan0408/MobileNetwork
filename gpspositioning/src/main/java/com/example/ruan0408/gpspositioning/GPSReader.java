package com.example.ruan0408.gpspositioning;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

public class GPSReader extends Service implements LocationListener {
	
    private static final long MIN_DISTANCE = 0; // 10 meters
    private static final long MIN_TIME = 0; // 10 seconds
    
	private Context context;
	private LocationManager locationManager;
	private TextView locationText;
	
	public GPSReader(Context c, TextView view) {
		context = c;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_DISTANCE, MIN_TIME, this);
		locationText = view;
	}

	@Override
	public void onLocationChanged(Location location) {

		Date date = new Date(location.getTime());
		String datetime = SimpleDateFormat.getDateTimeInstance().format(date);
		
		String info = 	"Date/Time: "+datetime+"\n" +
						"Provider: "+ location.getProvider()+"\n" + 
						"Accuracy: "+ location.getAccuracy()+"\n"+
						"Latitude: "+ location.getLatitude()+"\n"+
						"Longitude: "+ location.getLongitude()+"\n"+
						"Speed:" + location.getSpeed()+"\n";
		locationText.setText(info);
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
