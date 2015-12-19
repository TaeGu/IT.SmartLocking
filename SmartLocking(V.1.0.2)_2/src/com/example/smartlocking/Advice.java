package com.example.smartlocking;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Advice extends Fragment implements OnClickListener{
	
	public Button Lock, Flare, USIM;
	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_2, container, false);
        
        Lock  = (Button) view.findViewById(R.id.btn1);
        Flare = (Button) view.findViewById(R.id.btn2);
        USIM  = (Button) view.findViewById(R.id.btn3);
        Lock.setOnClickListener(this);
        Flare.setOnClickListener(this);
        USIM.setOnClickListener(this);
        
        return view;
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
    		case R.id.btn1:
    			Intent intent = new Intent(getActivity().getApplicationContext(), Advice_Lock.class);
    			startActivity(intent);
    			break;
    		case R.id.btn2:
    			Intent intent2 = new Intent(getActivity().getApplicationContext(), Advice_Flare.class);
    			startActivity(intent2);
    			break;
    		case R.id.btn3:
    			Intent intent3 = new Intent(getActivity().getApplicationContext(), Advice_USIM.class);
    			startActivity(intent3);
    			break;
    	}
	}
}