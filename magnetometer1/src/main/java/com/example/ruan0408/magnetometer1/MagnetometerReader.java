package com.example.ruan0408.magnetometer1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.TextView;

/**
 * Created by ruan0408 on 31/08/15.
 */
public class MagnetometerReader extends Service implements SensorEventListener {

    private Context context;
    private TextView view1;
    private TextView view2;
    private TextView view3;
    private SensorManager manager;
    private GPSReader gps;

    public MagnetometerReader(Context c, TextView v1, TextView v2, TextView v3) {
        context = c;
        view1 = v1;
        view2 = v2;
        view3 = v3;
        gps = new GPSReader(context);
        manager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        view1.setText("X: "+ x+"\n" + "Y: "+ y +"\n" + "Z: "+ z+"\n");
        float direction = 0;
        if (x > 0)      direction += 270 + Math.atan2(y,x)*180/Math.PI;
        else if (x < 0) direction += 90 + Math.atan2(y,x)*180/Math.PI;
        else if (y > 0) direction += 0;
        else if (y < 0) direction += 180;
        view2.setText("Exact heading: "+direction);

        GeomagneticField geoField;
        geoField = new GeomagneticField(gps.getLatitude(), gps.getLongitude(),
                                        gps.getAltitude(), System.currentTimeMillis());

        view3.setText("True heading: "+ direction+geoField.getDeclination());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}