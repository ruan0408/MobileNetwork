package com.example.ruan0408.hellowifi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private final Context context = this;
	private WifiManager manager;
	private AlertDialog.Builder popup;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		manager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		final Button btn = (Button) findViewById(R.id.scanButton);
		listView = (ListView) findViewById(R.id.APList);
		
		buildConnectionCompleteDialog();

		if(!manager.isWifiEnabled() || manager.getWifiState() != WifiManager.WIFI_STATE_ENABLING)
			manager.setWifiEnabled(true);

		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				manager.startScan();
			}
		});

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, 
				android.R.layout.simple_list_item_1, new ArrayList<String>());
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(new WifiItemClickListener(context, manager));

		IntentFilter filter = new IntentFilter();
		filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		//filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(new ScanResultsListener(arrayAdapter, manager), filter);
		
//		IntentFilter filter2 =  new IntentFilter();
//		filter2.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//		registerReceiver(new WifiConnectionListener(), filter2);
	}
	
	private void buildConnectionCompleteDialog() {
		popup = new AlertDialog.Builder(context);
		popup.setCancelable(false)
			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {}
				  });
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