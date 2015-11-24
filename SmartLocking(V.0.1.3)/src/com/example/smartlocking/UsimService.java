package com.example.smartlocking;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class UsimService extends Service {
	
	private UsimReceiver uReceiver = null;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void onCreate() {
		super.onCreate();
		
		uReceiver = new UsimReceiver();
		IntentFilter filter = new IntentFilter("android.intent.action.SIM_STATE_CHANGED");
		registerReceiver(uReceiver, filter);
	}

	@Override
	public void onDestroy(){		 	
		super.onDestroy();
		if(uReceiver != null){
			unregisterReceiver(uReceiver);
		}
	}
}
