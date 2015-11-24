package com.example.smartlocking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class BatteryReceiver extends BroadcastReceiver{
	boolean bcheck = true;
	int function = 2;
	@Override
	public void onReceive(Context context, Intent intent) {
		
		String action = intent.getAction();
		
		if(action.equals(Intent.ACTION_BATTERY_CHANGED)){
			int remain = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			
			if(remain == 86 & bcheck == true){
				Log.i("���͸�", "���͸��� 10% �Դϴ�.");
				bcheck = false;
				Intent i = new Intent(context, CameraActivity.class);
				i.putExtra("function", function);
	            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            context.startActivity(i); 
			}
			else if(remain != 86){
				Log.i("���͸�", "���͸� üũ ��� :" + remain);
				bcheck = true;
			}
		}
	}
}