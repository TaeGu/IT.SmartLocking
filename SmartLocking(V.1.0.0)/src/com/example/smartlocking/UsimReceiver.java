package com.example.smartlocking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class UsimReceiver extends BroadcastReceiver {
	
	int function = 3;

	@Override
	public void onReceive(Context context, Intent intent) {
		TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

		if(telephony.getSimState() == TelephonyManager.SIM_STATE_ABSENT){
			Intent i = new Intent(context,CameraActivity.class);
			i.putExtra("function", function);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}
	}

}
