package com.example.smartlocking;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.TelephonyManager;

public class UsimService extends Service {
	
	private UsimReceiver uReceiver = null;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void onCreate() {
		super.onCreate();
		
		uReceiver = new UsimReceiver();
		IntentFilter filter = new IntentFilter("TelephonyManager.SIM_STATE_ABSENT");
		registerReceiver(uReceiver, filter);
	}

	@Override
	public void onDestroy(){		 	
		super.onDestroy();
	}
}
