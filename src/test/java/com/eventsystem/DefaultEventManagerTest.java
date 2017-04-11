package com.eventsystem;

import com.eventsystem.events.SimpleEvent;
import com.eventsystem.events.SubEvent;
import com.eventsystem.impl.DefaultEventManager;
import com.eventsystem.impl.SpecialEventListener;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;

public class DefaultEventManagerTest
{
    private DefaultEventManager eventManager = new DefaultEventManager();;
    private EventListenerMock eventListenerMockBefore;
    private SpecialEventListener specialEventListenerNull;
    private SpecialEventListener specialEventListenerNotNull;

    //Before all tests
    @Before
    public void before()
    {
    	eventListenerMockBefore = new EventListenerMock(new Class[]{SimpleEvent.class});
    	specialEventListenerNull = new SpecialEventListener();
    	specialEventListenerNotNull = new SpecialEventListener(new Class[]{SimpleEvent.class});
    }
    
    @Ignore
    @Test
    public void testListenerSimpleEventListenerAndPublishSubEvent()
    {
    	eventManager.registerListener("Key1", eventListenerMockBefore);
    	eventManager.publishEvent(new SubEvent(this));
    	assertTrue(eventListenerMockBefore.isCalled());
    }
    
    @Test
    public void testSpecialListenerNull()
    {
    	Class [] result = specialEventListenerNull.getHandledEventClasses();
    	assertEquals(2, result.length);
    }
    
    @Test
    public void testSpecialListenerNotNull()
    {
    	Class [] result = specialEventListenerNotNull.getHandledEventClasses();
    	assertEquals(1, result.length);
    }
    
    @Test
    public void testPublishNullEvent()
    {
        eventManager.publishEvent(null);
    }

    @Test
    public void testRegisterListenerAndPublishEvent()
    {
        EventListenerMock eventListenerMock = new EventListenerMock(new Class[]{SimpleEvent.class});
        eventManager.registerListener("some.key", eventListenerMock);
        eventManager.publishEvent(new SimpleEvent(this));
        assertTrue(eventListenerMock.isCalled());
    }

    @Test
    public void testListenerWithoutMatchingEventClass()
    {
        EventListenerMock eventListenerMock = new EventListenerMock(new Class[]{SubEvent.class});
        eventManager.registerListener("some.key", eventListenerMock);
        eventManager.publishEvent(new SimpleEvent(this));
        assertFalse(eventListenerMock.isCalled());
    }

    @Test
    public void testUnregisterListener()
    {
        EventListenerMock eventListenerMock = new EventListenerMock(new Class[]{SimpleEvent.class});
        EventListenerMock eventListenerMock2 = new EventListenerMock(new Class[]{SimpleEvent.class});

        eventManager.registerListener("some.key", eventListenerMock);
        eventManager.registerListener("another.key", eventListenerMock2);
        eventManager.unregisterListener("some.key");

        eventManager.publishEvent(new SimpleEvent(this));
        assertFalse(eventListenerMock.isCalled());
        assertTrue(eventListenerMock2.isCalled());
    }


    /**
     * Check that registering and unregistering listeners behaves properly.
     */
    @Test
    public void testRemoveNonexistentListener()
    {
        DefaultEventManager dem = (DefaultEventManager)eventManager;
        assertEquals(0, dem.getListeners().size());
        eventManager.registerListener("some.key", new EventListenerMock(new Class[]{SimpleEvent.class}));
        assertEquals(1, dem.getListeners().size());
        eventManager.unregisterListener("this.key.is.not.registered");
        assertEquals(1, dem.getListeners().size());
        eventManager.unregisterListener("some.key");
        assertEquals(0, dem.getListeners().size());
    }

    /**
     * Registering duplicate keys on different listeners should only fire the most recently added.
     */
    @Test
    public void testDuplicateKeysForListeners()
    {
        EventListenerMock eventListenerMock = new EventListenerMock(new Class[]{SimpleEvent.class});
        EventListenerMock eventListenerMock2 = new EventListenerMock(new Class[]{SimpleEvent.class});

        eventManager.registerListener("some.key", eventListenerMock);
        eventManager.registerListener("some.key", eventListenerMock2);

        eventManager.publishEvent(new SimpleEvent(this));

        assertTrue(eventListenerMock2.isCalled());
        assertFalse(eventListenerMock.isCalled());

        eventListenerMock.resetCalled();
        eventListenerMock2.resetCalled();

        eventManager.unregisterListener("some.key");
        eventManager.publishEvent(new SimpleEvent(this));

        assertFalse(eventListenerMock2.isCalled());
        assertFalse(eventListenerMock.isCalled());
    }

    /**
     * Attempting to register a null with a valid key should result in an illegal argument exception
     */
    @Test
    public void testAddValidKeyWithNullListener()
    {
        try
        {
            eventManager.registerListener("bogus.key", null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException ex)
        {
        }
    }
}
