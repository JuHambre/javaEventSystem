package com.eventsystem.events;

import com.eventsystem.InterviewEvent;

public class SimpleEvent implements InterviewEvent
{
    private Object source;

    public SimpleEvent(Object source)
    {
        this.source = source;
    }

    public Object getSource()
    {
        return source;
    }
}