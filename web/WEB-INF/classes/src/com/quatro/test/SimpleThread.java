package com.quatro.test;

import java.util.Calendar;

class SimpleThread extends Thread {
	public SimpleThread(String name) {
		super(name);
	}

	public void run() {
		long start = Calendar.getInstance().getTimeInMillis();
		//System.out.println(getName() + " - Start Time:" + start);
				
		try {
			sleep((int) (Math.random() * 1000));
		} catch (InterruptedException e) {
		}
	
		long end = Calendar.getInstance().getTimeInMillis();
		//System.out.println(getName() + " - End Time:" + end);
		System.out.println(getName() + " - Cost:" + (end - start));
	}
	
}
