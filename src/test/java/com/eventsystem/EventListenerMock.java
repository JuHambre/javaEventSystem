package com.eventsystem;

import com.eventsystem.InterviewEvent;
import com.eventsystem.InterviewEventListener;

class EventListenerMock implements InterviewEventListener
{
    private boolean called;
    Class[] classes;
    public int count;

    public EventListenerMock(Class[] classes)
    {
        this.classes = classes;
    }

    public void handleEvent(InterviewEvent event)
    {
        called = true;
        count++;
    }

    public void resetCalled()
    {
        called = false;
    }

    public boolean isCalled()
    {
        return called;
    }

    public Class[] getHandledEventClasses()
    {
        return classes;
    }
}