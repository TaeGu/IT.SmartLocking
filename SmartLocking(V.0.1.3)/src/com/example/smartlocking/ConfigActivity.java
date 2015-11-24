package com.example.smartlocking;

import android.app.Fragment;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

public class ConfigActivity extends Fragment implements OnCheckedChangeListener{
	
	public static DevicePolicyManager devicePolicyManager;
	public static ComponentName adminComponent;
	public Switch OnOff; // 스위치 객체
	public Switch Battery;
	public Switch Usim;
	OnCheckedChangeListener OnOffListener;
	OnCheckedChangeListener BatteryListener;
	OnCheckedChangeListener UsimListener;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_main_1, container, false);  
    
    	adminComponent = new ComponentName(getActivity(), AdminReceiver.class);
	    devicePolicyManager = (DevicePolicyManager) getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
    	
		OnOff   = (Switch) view.findViewById(R.id.switch1);
		Battery = (Switch) view.findViewById(R.id.switch2);
		Usim    = (Switch) view.findViewById(R.id.switch3);
		
		OnOff.setOnCheckedChangeListener(this);
		Battery.setOnCheckedChangeListener(this);
		Usim.setOnCheckedChangeListener(this);
		
		return view;
    }
    
    @Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    	switch(buttonView.getId()){
        	case R.id.switch1:
        			if (OnOff.isChecked()) {
        				if (!devicePolicyManager.isAdminActive(adminComponent)){
        					Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        					intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
        					intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
        	                  "앱의 정상작동을 위해서는 다음 권한이 필요합니다.");
        					startActivityForResult(intent, 1);
        				}
        				Intent service = new Intent(getActivity().getApplicationContext(), ScreenService.class);
        				getActivity().startService(service);
        			} 
        			else{
        				if (devicePolicyManager.isAdminActive(adminComponent)){
        					devicePolicyManager.removeActiveAdmin(adminComponent);
        				}
        				Toast.makeText(getActivity(), "서비스가 종료됩니다.", Toast.LENGTH_SHORT).show();
        				Intent service = new Intent(getActivity().getApplicationContext(), ScreenService.class);
        				getActivity().stopService(service);
        			}
        			break;
        			
        	case R.id.switch2:
        		if (Battery.isChecked()) {
        			Toast.makeText(getActivity(), "신호탄이 시작됩니다.", Toast.LENGTH_SHORT).show();
        			Log.i("신호탄", "신호탄 시작.");
    				Intent battery = new Intent(getActivity().getApplicationContext(), BatteryService.class);
    				getActivity().startService(battery);
    			} 
    			else{
    				Toast.makeText(getActivity(), "신호탄이 종료됩니다.", Toast.LENGTH_SHORT).show();
    				Log.i("신호탄", "신호탄 종료.");
    				Intent Usim = new Intent(getActivity().getApplicationContext(), BatteryService.class);
    				getActivity().stopService(Usim);
    			}
        		break;
    
        	case R.id.switch3:
        		if (Usim.isChecked()) {
        			Toast.makeText(getActivity(), "USIM카드 감지가 시작됩니다.", Toast.LENGTH_SHORT).show();
        			Log.i("USIM", "USIM카드 감지 시작.");
    				Intent Usim = new Intent(getActivity().getApplicationContext(), UsimService.class);
    				getActivity().startService(Usim);
    			} 
    		
    			else{
    				Toast.makeText(getActivity(), "USIM카드 감지가 종료됩니다.", Toast.LENGTH_SHORT).show();
    				Log.i("USIM", "USIM카드 감지 종료");
    				Intent Usim = new Intent(getActivity().getApplicationContext(), UsimService.class);
    				getActivity().stopService(Usim);
    			}
        		break;
    	}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {		
				Toast.makeText(getActivity(), "서비스가 시작됩니다.", Toast.LENGTH_SHORT).show();
		}
	}
}