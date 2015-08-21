package com.example.ruan0408.hellogps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends ActionBarActivity {

	final private Context context = this;
	
	private Button statusButton;
	private Button locationButton;
	private GPSReader gpsReader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gpsReader = new GPSReader(context);
		buildStatusButton();
		buildLocationButton();
	}
	
	private void buildLocationButton() {
		locationButton = (Button) findViewById(R.id.locationButton);
		locationButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Location location = gpsReader.getLastKnownLocation();
				TextView locationTxt = (TextView) findViewById(R.id.locationText);
				 
				Date date = new Date(location.getTime());
				String datetime = SimpleDateFormat.getDateTimeInstance().format(date);
				
				String info = 	"Date/Time: "+datetime+"\n" + 
								"Provider: "+ location.getProvider()+"\n" + 
								"Accuracy: "+ location.getAccuracy()+"\n"+
								"Latitude: "+ location.getLatitude()+"\n"+
								"Longitude: "+ location.getLongitude()+"\n"+
								"Speed:" + location.getSpeed()+"\n";
				locationTxt.setText(info);
			}
			
		});
	}

	private void buildStatusButton() {
		statusButton = (Button) findViewById(R.id.gpsStatusButton);
		statusButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				TextView statusTxt = (TextView) findViewById(R.id.gpsStatusText);
				LocationManager locationManager = gpsReader.getLocationManager();
				if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					statusTxt.setText("GPS is not active");
					final AlertDialog.Builder builder =
				            new AlertDialog.Builder(context);
						builder.setTitle("Setting the GPS");
				        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
				        final String message = "GPS is not enabled. Do you want to go to settings menu?";
				        
				        builder.setMessage(message)
				            .setPositiveButton("Settings",
				                new DialogInterface.OnClickListener() {
				                    public void onClick(DialogInterface d, int id) {
				                        startActivity(new Intent(action));
				                        d.dismiss();
				                    }
				            })
				            .setNegativeButton("Cancel",
				                new DialogInterface.OnClickListener() {
				                    public void onClick(DialogInterface d, int id) {
				                        d.cancel();
				                    }
				            });
				        builder.create().show();
				}
				else
					statusTxt.setText("GPS is active");
			}
			
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
