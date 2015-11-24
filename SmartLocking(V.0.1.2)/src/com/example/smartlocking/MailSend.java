package com.example.smartlocking;

import java.io.File;

import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

public class MailSend {
	String timestamp = null;
	
	public MailSend(String timestamp, double a, double b, int function){ 
		//���� ����
        File filepath = Environment.getExternalStorageDirectory();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        
        Mail sender = new Mail("IT.SmartLocking@gmail.com","!1q2w3e4r"); // SUBSTITUTE HERE                    
        
        
        String subject = "";
        String body = "";
        
        if(function == 1)
        {
        	subject = "SmartLocking : �������� ��⸦ ��������Ϸ��� �մϴ�!";
        }
        else if(function == 2)
        {
        	subject = "SmartLocking : ��ȣź�� ����Ǿ����ϴ�!";
        }
        else if(function == 3)
        {
        	subject = "SmartLocking : USIM ī�� ������ ����Ǿ����ϴ�!";
        }
        
        if(a==0.0 && b==0.0)
        {
        	body = "GPS ����� �����ֽ��ϴ�.";
        }else{
        	body = "http://m.map.daum.net/look?p=" + a + "," + b;
        }
        
        try{
        	Mail email = new Mail("IT.SmartLocking@gmail.com", "!1q2w3e4r");
        	email.sendMailWithFile(subject, body, "IT.SmartLocking@gmail.com", "IT.SmartLocking@gmail.com",
        			filepath.getAbsolutePath() + "/DCIM/SmartLocking/IMG_" + timestamp + ".jpg"
        			, timestamp + ".jpg");
        	
				Log.i("Mail", "���Ϻ����� �Ϸ�");
			} catch (Exception e)
			{
				Log.d("lastiverse", e.toString());
				Log.d("lastiverse", e.getMessage());
			}
        
        
        return ;
	}
}