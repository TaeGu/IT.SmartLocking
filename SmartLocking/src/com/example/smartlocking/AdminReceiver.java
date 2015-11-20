package com.example.smartlocking;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.util.Log;


public class AdminReceiver extends DeviceAdminReceiver{
    static final String TAG = "DeviceAdminReceiver";
    public static int k = 0;
<<<<<<< HEAD

    @Override
       public void onEnabled(Context context, Intent intent) {
           super.onEnabled(context, intent);
           Log.i("AdminReceiver", "¼­ºñ½º ½ÃÀÛ");
       }

       @Override
       public void onDisabled(Context context, Intent intent) {
           super.onDisabled(context, intent);
           Log.i("AdminReceiver", "¼­ºñ½º Á¾·á");

       }

       @Override
       public void onPasswordChanged(Context context, Intent intent) {
           super.onPasswordChanged(context, intent);
           Log.d(TAG, "onPasswordChanged");
       }

       @Override
       public void onPasswordFailed(Context context, Intent intent) {
           super.onPasswordFailed(context, intent);
           Log.i("AdminReceiver", "onPasswordFailed");
           
           if(k < 2) {
              k++;
           }
           else {
              Intent i = new Intent(context, CameraActivity.class);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK );
              context.startActivity(i); 
           }
       }

       @Override
       public void onPasswordSucceeded(Context context, Intent intent) {
           super.onPasswordSucceeded(context, intent);
           Log.d(TAG, "onPasswordSucceeded");
           k = 0;
       }
}
=======
    //hikkk

    @Override
       public void onEnabled(Context context, Intent intent) {
           super.onEnabled(context, intent);
           Log.i("AdminReceiver", "Â¼Â­ÂºÃ±Â½Âº Â½ÃƒÃ€Ã›");
       }

       @Override
       public void onDisabled(Context context, Intent intent) {
           super.onDisabled(context, intent);
           Log.i("AdminReceiver", "Â¼Â­ÂºÃ±Â½Âº ÃÂ¾Â·Ã¡");

       }

       @Override
       public void onPasswordChanged(Context context, Intent intent) {
           super.onPasswordChanged(context, intent);
           Log.d(TAG, "onPasswordChanged");
       }

       @Override
       public void onPasswordFailed(Context context, Intent intent) {
           super.onPasswordFailed(context, intent);
           Log.i("AdminReceiver", "onPasswordFailed");
           
           if(k < 2) {
              k++;
           }
           else {
              Intent i = new Intent(context, CameraActivity.class);
              i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK );
              context.startActivity(i); 
           }
       }

       @Override
       public void onPasswordSucceeded(Context context, Intent intent) {
           super.onPasswordSucceeded(context, intent);
           Log.d(TAG, "onPasswordSucceeded");
           k = 0;
       }
}
>>>>>>> branch 'master' of https://github.com/TaeGu/IT.SmartLocking.git
