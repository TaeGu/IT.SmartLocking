package com.example.smartlocking;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ConfigActivity extends Activity{
    
   private Button onBtn, offBtn;
   public static DevicePolicyManager devicePolicyManager;
   public static ComponentName adminComponent;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
    
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
    
      adminComponent = new ComponentName(this, AdminReceiver.class);
      devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
      
      onBtn= (Button)findViewById(R.id.btn1); 
      offBtn= (Button)findViewById(R.id.btn2);
      
      onBtn.setOnClickListener(new View.OnClickListener(){
         public void onClick(View v) {      
            if (!devicePolicyManager.isAdminActive(adminComponent)){        
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                  intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
                  intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                        "���� �����۵��� ���ؼ��� ���� ������ �ʿ��մϴ�.");
                  startActivityForResult(intent, 1);
            }
             
            Intent intent = new Intent(getApplicationContext(), ScreenService.class);
            startService(intent);
            Toast.makeText(ConfigActivity.this, "���񽺰� ���۵˴ϴ�.", Toast.LENGTH_SHORT).show();
         }
      });
          
          
      offBtn.setOnClickListener(new View.OnClickListener(){
         public void onClick(View v) {   
            if (devicePolicyManager.isAdminActive(adminComponent)){
               devicePolicyManager.removeActiveAdmin(adminComponent);
            }
            Intent intent = new Intent(getApplicationContext(), ScreenService.class);
            stopService(intent);
            Toast.makeText(ConfigActivity.this, "���񽺰� ����˴ϴ�.", Toast.LENGTH_SHORT).show();
         }
      });
   }
}