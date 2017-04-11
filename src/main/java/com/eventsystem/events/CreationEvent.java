package com.eventsystem.events;

public class CreationEvent extends SimpleEvent implements Creation
{
    public CreationEvent(Object source)
    {
        super(source);
    }
}