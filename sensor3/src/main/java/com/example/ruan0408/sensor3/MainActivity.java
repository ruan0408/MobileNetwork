package com.example.ruan0408.sensor3;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements SensorEventListener {

	private SensorManager sensorManager;
	private Sensor accelerometerGrav;
	private Sensor accelerometerNoGrav;
	private TextView view1;
	private TextView view2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		view1 = (TextView) findViewById(R.id.textView1);
		view2 = (TextView) findViewById(R.id.textView2);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometerGrav = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		accelerometerNoGrav = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		sensorManager.registerListener(this, accelerometerGrav, SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(this, accelerometerNoGrav, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		float x = sensorEvent.values[0];
		float y = sensorEvent.values[1];
		float z = sensorEvent.values[2];
		String s;
		if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			s = "Acceleration force including gravity\n" +
					"X: "+x+"\n"+ "Y: "+y+"\n"+ "Z: "+z+"\n";
			view1.setText(s);
		} else {
			s = "Acceleration force without gravity\n" +
					"X: " + x + "\n" + "Y: " + y + "\n" + "Z: " + z + "\n";
			view2.setText(s);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int i) {

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
