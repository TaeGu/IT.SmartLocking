package com.example.onetofifty;



import java.util.*;
import android.os.*;
import android.app.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;

public class OneToFifty extends Activity implements View.OnClickListener{
	private Button      m_btn_start = null;            /* start 버튼             */	
	private Button      m_btns[]    = new Button[25];  /* 버튼1 ~ 25 */
	private TextView    m_tv_top    = null;
	private TextView    m_tv_time   = null;
	private Dialog      m_input_dlg = null;            /* Popup Dialog */
	private StopWatch   m_sw        = null;   
	private StopWatch   m_sw2        = null;   
	private StopWatch   m_sw3        = null;
	private int m_ArrNum[] = new int[25];      
	private int m_iStep = 1;                   /* 1 ~ 50현재진행 숫자     */	
	private int m_iNext = 1;  
	private boolean m_bStart = false;          /* 시작인지 여부                                 */	
	private boolean m_bBlink = false; 
	private boolean m_bBlink2 = false; 
	private int m_btnNum[] = new int[51];
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        SetBinddingButtons();        

        m_iStep = 1;	   
 	    m_bStart = false;   
        m_tv_time = (TextView)findViewById(R.id.tv_time);
        m_tv_time.setText("00:00:00");
        m_tv_time.setTextColor(Color.RED);
        
        m_sw = new StopWatch();
        m_sw2 = new StopWatch();
        m_sw3 = new StopWatch();
    }

    @SuppressWarnings("deprecation")
	private void SetBinddingButtons() {
		// TODO Auto-generated method stub
       	m_btn_start = (Button)findViewById(R.id.btn_start);
    	m_btn_start.setOnClickListener(this);
    	Resources res = getResources();
    	Drawable shape = res.getDrawable(R.drawable.round);

    	for(int i=0; i< 25; i++){
    	//	m_btns[i] = (Button)findViewById(m_btn_ids[i]);
    		m_btns[i] = (Button)findViewById(R.id.btn_1 + i);
    		m_btns[i].setOnClickListener(this);   
    		m_btns[i].setVisibility(View.INVISIBLE);
    		m_btns[i].setTextSize(20);    
    		m_btns[i].setBackgroundColor(Color.parseColor("#4C787E"));
    		m_btns[i].setTextColor(Color.parseColor("#B90000"));
    		m_btns[i].setPadding(1,1,1,1);
    		m_btns[i].setBackgroundDrawable(shape);
    	}   		
	}

    public void InitValue(){
 	   m_iStep = 1;	   
 	   m_bStart = false;
 	   m_btn_start.setText("Start");	
 	   m_tv_time.setText("00:00:00");
     }

 	public void onClick(View v) {
        if(v == m_btn_start){    	       	   
     	   if(!m_bStart){
     		   InitValue();
     		   m_sw.refresh();
     		   m_sw2.refresh();
     		   m_sw3.refresh();
     	   }
     	   
     	   m_bStart = !m_bStart;
     	   
     	   if(m_bStart) {
     		   
     		   /* 1 ~ 25버튼 생성*/
     		   initNumberArr(1);
     		   shakeNumber();
     		   int bWidth = m_btns[1].getWidth();
     		   int bHeight = m_btns[1].getHeight();
 	    	   for(int i=0; i< 5; i++){
 		       		for(int j=0; j< 5; j++){	       		
 		       		   m_btns[i*5 + j].setVisibility(View.VISIBLE);
 		       		   m_btns[i*5 + j].layout(i*bWidth+6, j*bHeight, i*bWidth+6+bWidth, j*bHeight+bHeight);
 		       	       m_btns[i*5 + j].setText("" + m_ArrNum[i*5 + j]);
 		       	       
 		       	       m_btnNum[ m_ArrNum[i*5 + j]] = i*5 + j;
 		       	       m_btns[i*5 + j].setGravity(Gravity.CENTER_HORIZONTAL |Gravity.CENTER_VERTICAL);
 		       		}
 	       	   }
        	   
 	    	   startThread();
 	    	   m_sw.start();
 	    	   m_sw2.start();	    
 	    	   m_sw3.start();
 	    	   /* 26 ~ 50버튼 생성*/

 	    	   initNumberArr(26);
 	    	   shakeNumber();

     	   }else{
    		   stopThread();
    		   stopThread2();
    	   }
     	   
     	   m_btn_start.setText(m_bStart?"Stop":"Start");
   
        }else{
  	
        	int x = Integer.parseInt(((Button)v).getText().toString());
     	   if(x == m_iStep){  
    		   stopThread2();
    		   m_sw2.refresh();
    		   m_sw2.start();
    		   ((Button)v).setTextColor(Color.parseColor("#B90000"));
     		   if(m_iStep >= 26)
     		   {
     			   ((Button)v).setVisibility(View.INVISIBLE);
     		   }else{    			   
     			   ((Button)v).setText(""+ m_ArrNum[25 - m_iStep]); 
     			   m_btnNum[m_ArrNum[25 - m_iStep]]=m_btnNum[m_iStep];
    			   
     		   }
     		   m_iStep++;
     	   }
     	   
     	   if(m_iStep == 51)
     	   {
     		   m_bStart = false;
     		   m_sw.stop();
     		   
     	   }
        }		
 	}
 	
 	public void initNumberArr(int nStartNum){
 		for(int i=0; i< 25; i++){			
 			m_ArrNum[i] = i+ nStartNum;
 			
 		}
 	}   
 	
 	public void shakeNumber(){
 		
 		int x   = 0;
 		int y   = 0;
 		int tmp = 0;
 		
 		Random _ran = new Random();
 		
 		for(int i=0; i< 100; i++){
 		    x = _ran.nextInt(25);
 		    y = _ran.nextInt(25);
 		    
 		    if(x == y) continue;
 		    
 		    tmp = m_ArrNum[x];
 		    m_ArrNum[x] = m_ArrNum[y];
 		    m_ArrNum[y] = tmp;

 		}		
 	}
 	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	private volatile Thread theThread1;

	public synchronized void startThread() {
		if (theThread1 == null) {
			theThread1 = new Thread(null, backgroundTread1,
					"startThread");
			theThread1.start();
		}
	}	

	public synchronized void stopThread() {
		if (theThread1 != null) {
			Thread tmpThread = theThread1;
			theThread1 = null;
			tmpThread.interrupt();
		}
	}

	private Runnable backgroundTread1 = new Runnable() {
		public void run() {
			if (Thread.currentThread() == theThread1) {
				while(m_bStart)
				{
					try {
						theHandle.sendMessage(theHandle
								.obtainMessage());
						Thread.sleep(100);

					} catch (final InterruptedException e) {
						return;
					} catch (final Exception e) {
						return;
					}
				}	
			}
		}	
		Handler theHandle = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				double ell =   m_sw.getFormatF();
				String strTime = String.format("%02d:%02d:%02d", (int)(ell / 60), 
						(int)(ell% 60), (int)((ell *100) % 100));	
				 m_tv_time.setText(strTime);
				 if(m_sw2.getElapsedTimeMilli()>=3000) startThread2();
	   		     super.handleMessage(msg); 
			}
		};
	};		

	private volatile Thread theThread2;

	public synchronized void startThread2() {
		if (theThread2 == null) {
			theThread2 = new Thread(null, backgroundTread2,
					"startThread");
			m_bBlink = true;
			theThread2.start();
		}
	}	

	public synchronized void stopThread2() {
		if (theThread2 != null) {
			Thread tmpThread2 = theThread2;
			theThread2 = null;
			m_bBlink=false;
			tmpThread2.interrupt();
		}
	}

	private Runnable backgroundTread2 = new Runnable() {
		public void run() {
			if (Thread.currentThread() == theThread2) {
				while(m_bBlink)
				{
					try {
						if(m_iStep>25) m_iNext = m_iStep-25;
						else  m_iNext = m_iStep;
						theHandle2.sendMessage(theHandle2
								.obtainMessage());
						Thread.sleep(500);

					} catch (final InterruptedException e) {
						return;
					} catch (final Exception e) {
						return;
					}
				}	
			}
		}	
		Handler theHandle2 = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(m_bBlink2 == true){
						m_btns[m_btnNum[m_iStep]].setTextColor(Color.parseColor("#FFFF00"));
						m_bBlink2 = false;
				}else{
						m_btns[m_btnNum[m_iStep]].setTextColor(Color.parseColor("#B90000"));
						m_bBlink2 = true;
				}	
				super.handleMessage(msg); 
			}
		};
	};	
}
