package com.example.ruan0408.hellowifi;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

public class WifiConnectionListener extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {

		ConnectivityManager cm =
		        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
		                      activeNetwork.isConnectedOrConnecting();

		if(isConnected && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
			WifiManager manager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = manager.getConnectionInfo();
			int ip = wifiInfo.getIpAddress();
			String ipAddress = Formatter.formatIpAddress(ip);
			
			AlertDialog alert = new AlertDialog.Builder(context).create();
			alert.setTitle("You are connected to "+wifiInfo.getSSID()+ "and your IP address is "+ ipAddress);
			alert.show();
		}
	}

}
