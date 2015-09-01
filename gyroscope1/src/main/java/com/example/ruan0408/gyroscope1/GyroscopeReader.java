package com.example.ruan0408.gyroscope1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.TextView;

/**
 * Created by ruan0408 on 31/08/15.
 */
public class GyroscopeReader extends Service implements SensorEventListener{

    private static final float NS2S = 1.0f / 1000000000.0f;

    private Context context;
    private TextView view1;
    private TextView view2;
    private SensorManager manager;
    private double currentRotation;
    private long lastTimestamp = 0;

    public GyroscopeReader(Context c, TextView view1, TextView view2) {
        context = c;
        this.view1 = view1;
        this.view2 = view2;
        manager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String info = 	"X: "+ event.values[0]+"\n" +
                        "Y: "+ event.values[1]+"\n" +
                        "Z: "+ event.values[2]+"\n";
        view1.setText(info);

        long currentTimestamp = event.timestamp;
        if (lastTimestamp != 0) {
            currentRotation += (currentTimestamp - lastTimestamp) * NS2S * event.values[2] * 180/Math.PI -0.05;
            currentRotation %= 360;
            view2.setText("Rotation on Z axis: "+ -currentRotation);
        }
        lastTimestamp = currentTimestamp;
    }

    public void resetRotation() {
        currentRotation = 0;
        lastTimestamp = 0;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
