package com.example.smartlocking;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class BatteryService extends Service {
	
	private BatteryReceiver bReceiver = null;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void onCreate() {
		super.onCreate();
		bReceiver = new BatteryReceiver();
		
		IntentFilter filter2 = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(bReceiver, filter2);
	}

	@Override
	public void onDestroy(){		 	
		super.onDestroy();
		if(bReceiver != null){
			unregisterReceiver(bReceiver);
		}
	}
}
