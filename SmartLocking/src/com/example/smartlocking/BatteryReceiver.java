package com.example.smartlocking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class BatteryReceiver extends BroadcastReceiver{
	boolean bcheck = true;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		String action = intent.getAction();
		
		
		if(action.equals(Intent.ACTION_BATTERY_CHANGED)){
			int remain = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			
			if(remain == 10 & bcheck == true){
				Log.e("배터리", "배터리가 10% 입니다.");
				bcheck = false;
				Intent i = new Intent(context, CameraActivity.class);
	            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            context.startActivity(i); 
			}
			else if(remain != 10){
				bcheck = true;
			}
		}
	}
}
