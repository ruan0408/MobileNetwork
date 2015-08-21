package com.example.ruan0408.hellowifi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class WifiItemClickListener implements OnItemClickListener {
	
	private Context context;
	private WifiManager manager;
	
	public WifiItemClickListener(Context c, WifiManager m) {
		context = c;
		manager = m;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	
		final WifiConfiguration config = new WifiConfiguration();
		final String row = ((TextView)arg1).getText().toString();
		final String ssid = row.split(" ")[0];
		config.SSID = "\"" + ssid + "\"";
		config.status = WifiConfiguration.Status.ENABLED;
		
		if(row.contains("WPA") || row.contains("WPA2") || row.contains("WEP"))
			wifiSigninDialogCreator(config, row).show();
		else {
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			int networkId = manager.addNetwork(config);
			manager.disconnect();
	        manager.enableNetwork(networkId, true);
			manager.reconnect();
			showPopUp();
		}		
	}

	private AlertDialog wifiSigninDialogCreator(final WifiConfiguration config,
			final String row) {
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setView(promptsView);
		
		final EditText usernameInput = (EditText) promptsView.findViewById(R.id.usernameInput);
		final EditText passwordInput = (EditText) promptsView.findViewById(R.id.passwordInput);
		
		if(!row.contains("EAP")) usernameInput.setVisibility(View.GONE);
		
		alertDialogBuilder.setCancelable(true)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					String username = usernameInput.getText().toString();
					String password = passwordInput.getText().toString();
					
					if (row.contains("EAP")){
						WifiEnterpriseConfig enterpriseConfig = new WifiEnterpriseConfig();
						config.allowedKeyManagement.set(KeyMgmt.WPA_EAP);
						config.allowedKeyManagement.set(KeyMgmt.IEEE8021X);
						enterpriseConfig.setIdentity(username);
						enterpriseConfig.setPassword(password);
						enterpriseConfig.setEapMethod(WifiEnterpriseConfig.Eap.PEAP);
						config.enterpriseConfig = enterpriseConfig;
					} else if (row.contains("WPA2") || row.contains("WPA")) {
						config.preSharedKey = "\""+ password +"\"";
					} else if (row.contains("WEP")) {
						config.wepKeys[0] = "\"" + password + "\""; 
						config.wepTxKeyIndex = 0;
						config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
						config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
					}
					
					int networkId = manager.addNetwork(config);
					manager.disconnect();
	                manager.enableNetwork(networkId, true);
					manager.reconnect();
					showPopUp();
				}
			});
		AlertDialog alertDialog = alertDialogBuilder.create();
		return alertDialog;
	}
	
	private void showPopUp() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		WifiInfo wifiInfo = manager.getConnectionInfo();
		int ip = wifiInfo.getIpAddress();
		String ipAddress = Formatter.formatIpAddress(ip);
		String message = "You are connected to "+wifiInfo.getSSID()+ "and your IP address is "+ ipAddress;
		AlertDialog alert = new AlertDialog.Builder(context).setMessage(message).create();
		alert.show();
	}
}
