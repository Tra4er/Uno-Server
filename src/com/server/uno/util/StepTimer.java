package com.server.uno.util;

import java.util.Timer;
import java.util.TimerTask;

public class StepTimer {
	
	private final int STEP_TIME;
	
	private volatile int time;
	private Timer timer = new Timer();
	
	public StepTimer(int time) {
		STEP_TIME = time;
		this.time = time;
	}
	
	public void start() {
		time = STEP_TIME;
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				if(time >= 0) 
					time--;
			}
		}, 0, 1000);
	}
	
	public int getTime() {
		return time;
	}
}
