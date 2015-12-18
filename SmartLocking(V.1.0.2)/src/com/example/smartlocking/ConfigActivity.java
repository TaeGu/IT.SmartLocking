package com.example.smartlocking;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ConfigActivity extends Fragment implements OnCheckedChangeListener, View.OnClickListener{
	
	public static DevicePolicyManager devicePolicyManager;
	public static ComponentName adminComponent;
	
	public Switch OnOff; 
	public TextView OnOffText;
	public Switch Battery;
	public Switch Usim;
	
	public RelativeLayout LockNum;
	public RelativeLayout Email;
	
	public TextView txtv2;
	public TextView txem;
	public View emailView;
	
	public EditText emailText;
	
	OnCheckedChangeListener OnOffListener;
	OnCheckedChangeListener BatteryListener;
	OnCheckedChangeListener UsimListener;
	
	boolean lock;
	boolean flare;
	boolean usim;
	
	int lockN;
	
	String mails;
	String accou;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_main_1, container, false);  
    
    	adminComponent = new ComponentName(getActivity(), AdminReceiver.class);
	    devicePolicyManager = (DevicePolicyManager) getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
    	
		OnOff   = (Switch) view.findViewById(R.id.switch1);
		OnOffText =  (TextView) view.findViewById(R.id.text3);
		Battery = (Switch) view.findViewById(R.id.switch2);
		Usim    = (Switch) view.findViewById(R.id.switch3);
		
		txtv2 = (TextView) view.findViewById(R.id.text2);
		LockNum = (RelativeLayout) view.findViewById(R.id.relativeLayout4);
		
		Email = (RelativeLayout) view.findViewById(R.id.relativeLayout5);
		txem = (TextView) view.findViewById(R.id.text5);
		
		Account[] accounts =  AccountManager.get(getActivity()).getAccounts();
	    Account account = null;
	                 
	    for(int i=0;i<accounts.length;i++) {
	          account = accounts[i];
	 
	          if(account.type.equals("com.google")){     //�̷��� ���� ���� ���� ����
	        	  accou = account.name;
	          }
	    }
		
		SharedPreferences sharedPref = getActivity().getSharedPreferences("state",Context.MODE_PRIVATE);
		lock = sharedPref.getBoolean("Lock", false);
		flare = sharedPref.getBoolean("Flare", false);
		usim = sharedPref.getBoolean("Usim", false);
		lockN = sharedPref.getInt("LockNum", 3);
		mails = sharedPref.getString("EMail", accou);
		
		LockNum.setOnClickListener(this);
		Email.setOnClickListener(this);
		
		OnOff.setChecked(lock);
	    Battery.setChecked(flare);
	    Usim.setChecked(usim);
	    txtv2.setText(lockN+"��° ���� �õ��� ���� ������");
	    txem.setText(mails);
	    
		OnOff.setOnCheckedChangeListener(this);
		Battery.setOnCheckedChangeListener(this);
		Usim.setOnCheckedChangeListener(this);
		
		return view;
    }
    
    @Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    	SharedPreferences sharedPref = getActivity().getSharedPreferences("state",Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedPref.edit();
    	switch(buttonView.getId()){
        	case R.id.switch1:
        		editor.putBoolean("Lock", isChecked);
    			editor.commit();
        		if (OnOff.isChecked()) {
        			if (!devicePolicyManager.isAdminActive(adminComponent)){
        				Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        				intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
        				intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
        	                 "���� �����۵��� ���ؼ��� ���� ������ �ʿ��մϴ�.");
        				startActivityForResult(intent, 1);
        			}
        			Intent service = new Intent(getActivity().getApplicationContext(), ScreenService.class);
        			getActivity().startService(service);
        		} 
        		else{
        			if (devicePolicyManager.isAdminActive(adminComponent)){
        				devicePolicyManager.removeActiveAdmin(adminComponent);
        			}
        			Toast.makeText(getActivity(), "���񽺰� ����˴ϴ�.", Toast.LENGTH_SHORT).show();
        			Intent service = new Intent(getActivity().getApplicationContext(), ScreenService.class);
        			getActivity().stopService(service);
        		}
        		break;
        			
        	case R.id.switch2:
        		editor.putBoolean("Flare", isChecked);
        		editor.commit();
        		if (Battery.isChecked()) {
        			final String[] flareNumber = new String[] {"5%","15%","20%"};
    				AlertDialog.Builder flaredlg = new AlertDialog.Builder(getActivity());
    				flaredlg.setTitle("���͸� ���� ����");
    				
    				flaredlg.setItems(flareNumber,
    						new DialogInterface.OnClickListener() {
    							@Override
    							public void onClick(DialogInterface dialog, int which) {
    								SharedPreferences sharedPref = getActivity().getSharedPreferences("state",Context.MODE_PRIVATE);
    						    	SharedPreferences.Editor editor = sharedPref.edit();
    						    	OnOffText.setText(flareNumber[which]);
    						    	if(flareNumber[which]=="5%"){
    					        		editor.putInt("FlareNum", 5);
    								}
    								else if(flareNumber[which]=="15%"){
    									editor.putInt("FlareNum", 15);
    								}
    								else if(flareNumber[which]=="20%"){
    									editor.putInt("FlareNum", 20);
    								}
    								editor.commit();
    							}
    						});
    				flaredlg.setPositiveButton("���", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Battery.setChecked(false);
						}
					});
    				flaredlg.show();
        			Toast.makeText(getActivity(), "��ȣź�� ���۵˴ϴ�.", Toast.LENGTH_SHORT).show();
        			Log.i("��ȣź", "��ȣź ����.");
    				Intent battery = new Intent(getActivity().getApplicationContext(), BatteryService.class);
    				getActivity().startService(battery);
    			} 
    			else {
    				Toast.makeText(getActivity(), "��ȣź�� ����˴ϴ�.", Toast.LENGTH_SHORT).show();
    				Log.i("��ȣź", "��ȣź ����.");
    				Intent Usim = new Intent(getActivity().getApplicationContext(), BatteryService.class);
    				getActivity().stopService(Usim);
    				OnOffText.setText("����");
    			}
        		break;
    
        	case R.id.switch3:
        		editor.putBoolean("Usim", isChecked);
        		editor.commit();
        		if (Usim.isChecked()) {
        			Toast.makeText(getActivity(), "USIMī�� ������ ���۵˴ϴ�.", Toast.LENGTH_SHORT).show();
        			Log.i("USIM", "USIMī�� ���� ����.");
    				Intent Usim = new Intent(getActivity().getApplicationContext(), UsimService.class);
    				getActivity().startService(Usim);
    			} 
    		
    			else{
    				Toast.makeText(getActivity(), "USIMī�� ������ ����˴ϴ�.", Toast.LENGTH_SHORT).show();
    				Log.i("USIM", "USIMī�� ���� ����");
    				Intent Usim = new Intent(getActivity().getApplicationContext(), UsimService.class);
    				getActivity().stopService(Usim);
    			}
        		break;
    	}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode == 0){
			OnOff.setChecked(false);
			Toast.makeText(getActivity(), "���񽺰� ����˴ϴ�.", Toast.LENGTH_SHORT).show();
		}
		else {		
			Toast.makeText(getActivity(), "���񽺰� ���۵˴ϴ�.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.relativeLayout4 :
				final String[] lockNumber = new String[] {"2��","3��","5��"};
				AlertDialog.Builder lockdlg = new AlertDialog.Builder(getActivity());
				lockdlg.setTitle("��� ���� �õ� Ƚ��");
				
				lockdlg.setItems(lockNumber,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								SharedPreferences sharedPref = getActivity().
										getSharedPreferences("state",Context.MODE_PRIVATE);
						    	SharedPreferences.Editor editor = sharedPref.edit();
								txtv2.setText(lockNumber[which]+"° ���� �õ��� ���� ������");
								if(lockNumber[which]=="2��"){
					        		editor.putInt("LockNum", 2);
								}
								else if(lockNumber[which]=="3��"){
									editor.putInt("LockNum", 3);
								}
								else if(lockNumber[which]=="5��"){
									editor.putInt("LockNum", 5);
								}
								editor.commit();
								Toast.makeText(getActivity(), "�õ� Ƚ���� ����Ǿ����ϴ�.", 
										Toast.LENGTH_SHORT).show();
							}
						});
				lockdlg.setPositiveButton("���", null);
				lockdlg.show();
				break;
				
			case R.id.relativeLayout5 :
				SharedPreferences sharedPref = getActivity()
					.getSharedPreferences("state",Context.MODE_PRIVATE);
				emailView = (View) View.inflate(getActivity(), R.layout.mail, null);
				emailText = (EditText) emailView.findViewById(R.id.email);
				mails = sharedPref.getString("EMail", accou);
				emailText.setText(mails);
				AlertDialog.Builder emaildlg = new AlertDialog.Builder(getActivity());
				emaildlg.setTitle("E-mail �����ּ� : ");
				emaildlg.setView(emailView);
				emaildlg.setPositiveButton("Ȯ��", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						SharedPreferences sharedPref = getActivity().getSharedPreferences("state",
								Context.MODE_PRIVATE);
				    	SharedPreferences.Editor editor = sharedPref.edit();
						String email = emailText.getText().toString();
						editor.putString("EMail", email);
						txem.setText(email);
						editor.commit();
						Toast.makeText(getActivity(), "������ ����Ǿ����ϴ�.", Toast.LENGTH_SHORT).show();
					}
				});
				emaildlg.setNegativeButton("���", null);
				emaildlg.show();
				break;
		}
	}
}