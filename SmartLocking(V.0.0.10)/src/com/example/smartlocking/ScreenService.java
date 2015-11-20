package com.example.smartlocking;

import android.app.Service;
import android.app.admin.DeviceAdminReceiver;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class ScreenService extends Service {
	
	private DeviceAdminReceiver mReceiver = null;
	private BatteryReceiver bReceiver = null;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mReceiver = new AdminReceiver();
		bReceiver = new BatteryReceiver();
		
		IntentFilter filter1 = new IntentFilter(android.app.admin.DeviceAdminReceiver.ACTION_PASSWORD_FAILED);
		registerReceiver(mReceiver, filter1);
		
		IntentFilter filter2 = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(bReceiver, filter2);
	}
 
	/*@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		super.onStartCommand(intent, flags, startId);

		if(intent != null){
			if(intent.getAction()==null){
				if(mReceiver==null){	
					mReceiver = new AdminReceiver();	
					IntentFilter filter = new IntentFilter(android.app.admin.DeviceAdminReceiver.ACTION_PASSWORD_FAILED);
					registerReceiver(mReceiver, filter);
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
