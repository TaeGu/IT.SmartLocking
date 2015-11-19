package com.example.onetofifty;

public class StopWatch {          
	private long startTime = 0;      
	private long stopTime = 0;      
	private long elapsed = 0;      
	private boolean running = false;   
	
	public void start() {          
		this.startTime = System.nanoTime();          
		this.running = true;      
	}
	
	public void stop() {
		this.stopTime = System.nanoTime();
		this.running = false;
	}
	
	public void reset() {
		this.startTime = 0;          
		this.stopTime = 0;          
		this.running = false;      
	}   
	
	public void refresh(){
		stop();
		reset();
	}
	
	//elaspsed time in microseconds      
	public long getElapsedTimeMicro() {         
        if (running) {              
        	elapsed = ((System.nanoTime() - startTime) / 1000);         
        }else{ 
        	elapsed = ((stopTime - startTime) / 1000);          
        }          
        
        return elapsed;      
    }   
	
	//elaspsed time in milliseconds      
	public long getElapsedTimeMilli() {          
		if (running) {              
			elapsed = ((System.nanoTime() - startTime) / 1000000);          
	    }else{
	    	elapsed = ((stopTime - startTime) / 1000000);          
	    }          
		
		return elapsed;      
	}
	
	public double getFormatF(){
		 double ftime = 0.0;
		 
		 long ltime = getElapsedTimeMilli();
		 
		 /*
		 int sec =(int)(ltime/1000);
		 int msec =(int)(ltime%1000);
		 
		 if(msec > 100)
			 msec = (int)(msec/100);
			 */
		 double nTime = ltime / 1000.0;
		 
         ftime = Double.parseDouble(String.format("%02f", nTime));
                  
         return ftime;
	}
}