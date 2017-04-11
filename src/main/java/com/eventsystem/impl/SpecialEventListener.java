package com.eventsystem.impl;

import com.eventsystem.InterviewEvent;
import com.eventsystem.InterviewEventListener;
import com.eventsystem.events.SimpleEvent;
import com.eventsystem.events.SubEvent;

public class SpecialEventListener implements InterviewEventListener{

	private Class[] classes;
	private final Class[] allClasses = new Class[]{SimpleEvent.class, SubEvent.class};
	
	public SpecialEventListener (Class[] c)
	{
		classes = c;
	}
	
	public SpecialEventListener()
	{
		
	}
	
	public void handleEvent(InterviewEvent event) {
		// TODO
	}

	public Class[] getHandledEventClasses() {
		if(classes == null)
		{
			return allClasses;
		}
		else
		{
			return classes;
		}
	}

}
