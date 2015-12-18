package com.example.smartlocking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.util.Log;

public class BatteryReceiver extends BroadcastReceiver{
	boolean bcheck = true;
	int function = 2;
	@Override
	public void onReceive(Context context, Intent intent) {
		
		String action = intent.getAction();
		SharedPreferences sharedPref = context.getSharedPreferences("state",Context.MODE_PRIVATE);
        int FlareNum = sharedPref.getInt("FlareNum", 0);
        
		if(action.equals(Intent.ACTION_BATTERY_CHANGED)){
			int remain = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			
			if(remain == FlareNum & bcheck == true){
				Log.i("배터리", "배터리가 10% 입니다.");
				bcheck = false;
				Intent i = new Intent(context, Cam_GPS_Activity.class);
				i.putExtra("function", function);
	            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            context.startActivity(i); 
			}
			else if(remain != FlareNum){
				Log.i("배터리", "배터리 체크 결과 :" + remain);
				bcheck = true;
			}
		}
	}
}