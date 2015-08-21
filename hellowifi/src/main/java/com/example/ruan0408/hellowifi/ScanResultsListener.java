package com.example.ruan0408.hellowifi;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.widget.ArrayAdapter;

public class ScanResultsListener extends BroadcastReceiver {
	
	private ArrayAdapter<String> arrayAdapter;
	private WifiManager manager;
	
	public ScanResultsListener(ArrayAdapter<String> a, WifiManager m) {
		arrayAdapter = a;
		manager = m;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Set<String> hash = new HashSet<String>();
		List<ScanResult> scanResults = manager.getScanResults();
		arrayAdapter.clear();
		Collections.sort(scanResults, new ScanResultLevelComparator());

		int count = 0;
		for(ScanResult r : scanResults) {
			if(count >= 4) break;
			if(!hash.contains(r.SSID)) {
				arrayAdapter.add(r.SSID+" "+r.BSSID+" "+r.level+" "+r.capabilities);
				hash.add(r.SSID);
				count++;
			}
		}
	}
}
