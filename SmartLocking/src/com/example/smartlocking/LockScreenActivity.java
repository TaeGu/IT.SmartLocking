package com.example.smartlocking;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

public class LockScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Toast.makeText(LockScreenActivity.this, "ºñ¹øÆ²·È½º", Toast.LENGTH_SHORT).show();
		
		finish();
	}
}
