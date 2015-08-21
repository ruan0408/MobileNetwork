package com.example.ruan0408.sensor1;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setListAdapter();
		registerButtonCallback();
	}
	
	private void setListAdapter() {
		ListView list = (ListView) findViewById(R.id.sensorList);
		list.setAdapter(new ArrayAdapter<String>(this, R.layout.item));
	}
	
	private void registerButtonCallback() {
		Button btn = (Button) findViewById(R.id.listButton);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
				List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ALL);
				String[] sensorsArray = sensorListToArray(sensors);
				ListView list = (ListView) MainActivity.this.findViewById(R.id.sensorList);
				((ArrayAdapter<String>)list.getAdapter()).addAll(sensorsArray);
			}
		});
	}
	
	private String[] sensorListToArray(List<Sensor> sensors) {
		String[] array = new String[sensors.size()];
		int j = 0;
		for(Sensor s : sensors) {
			String info = 	"Name: " +s.getName()+"\n"+
							"Vendor: "+s.getVendor()+"\n"+
							"Version: "+s.getVersion()+"\n"+
							"Maximum Range: "+s.getMaximumRange()+"\n"+
							"Min Delay: "+s.getMinDelay()+"\n";
			array[j++] = info;
		}
		return array;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
