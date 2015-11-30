package com.example.smartlocking;

import android.app.Service;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

public class ScreenService extends Service {
	
	private DeviceAdminReceiver mReceiver = null;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mReceiver = new AdminReceiver();
		
		IntentFilter filter1 = new IntentFilter(android.app.admin.DeviceAdminReceiver.ACTION_PASSWORD_FAILED);
		registerReceiver(mReceiver, filter1);
	}
 
	/*
	public int onStartCommand(Intent intent, int flags, int startId){
		super.onStartCommand(intent, flags, startId);

		if(intent != null){
			if(intent.getAction()==null){
				if(mReceiver==null){	
					mReceiver = new AdminReceiver();	
					IntentFilter filter = new IntentFilter(android.app.admin.DeviceAdminReceiver.ACTION_PASSWORD_FAILED);
					registerReceiver(mReceiver, filter);
				}
				if(bReceiver==null){
					bReceiver = new BatteryReceiver();
					IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
					registerReceiver(bReceiver, filter);
				}
			}
		}
		return START_REDELIVER_INTENT;
	}*/
	
	@Override
	public void onDestroy(){		 	
		super.onDestroy();
		if(mReceiver != null){
			unregisterReceiver(mReceiver);
		}
	}
}