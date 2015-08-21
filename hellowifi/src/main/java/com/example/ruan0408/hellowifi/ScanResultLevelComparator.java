package com.example.ruan0408.hellowifi;

import java.util.Comparator;

import android.net.wifi.ScanResult;

public class ScanResultLevelComparator implements Comparator<ScanResult> {

	@Override
	public int compare(ScanResult lhs, ScanResult rhs) {
		return lhs.level - rhs.level;
	}

}
