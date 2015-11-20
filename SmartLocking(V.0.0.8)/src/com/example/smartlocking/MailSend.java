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
       try
			{ 
				Mail email = new Mail("IT.SmartLocking@gmail.com",
						"!1q2w3e4r");
				email.sendMailWithFile("누군가가 기기를 열어보려고합니다!", "http://m.map.daum.net/look?p=" + a + "," + b,
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
