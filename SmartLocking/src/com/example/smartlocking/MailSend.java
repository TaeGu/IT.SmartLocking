package com.example.smartlocking;

import java.io.File;

import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

public class MailSend {
	String timestamp = null;
	public MailSend(String timestamp, double a, double b){ 
		//메일 전송
        File filepath = Environment.getExternalStorageDirectory();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        
        Mail sender = new Mail("IT.SmartLocking@gmail.com","!1q2w3e4r"); // SUBSTITUTE HERE                    
        
        String body = "";
        
        if(a==0.0)
        {
        	body = "GPS 기능이 꺼져있습니다.";
        }else{
        	body = "http://m.map.daum.net/look?p=" + a + "," + b;
        }
        
        try
			{ 
				Mail email = new Mail("IT.SmartLocking@gmail.com",
						"!1q2w3e4r");
				email.sendMailWithFile("SmartLocking : 누군가가 기기를 잠금해제하려고 합니다!", body,
						"IT.SmartLocking@gmail.com", "IT.SmartLocking@gmail.com",
						filepath.getAbsolutePath() + "/DCIM/SmartLocking/IMG_" + timestamp + ".jpg", timestamp + ".jpg");
				Log.i("Mail", "메일보내기 완료");
			} catch (Exception e)
			{
				Log.d("lastiverse", e.toString());
				Log.d("lastiverse", e.getMessage());
			} // try-catch
        return ;
	}
}

//http://m.map.daum.net/look?p=" + a + "," + b
