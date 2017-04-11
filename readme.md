# javaEventSystem

This is a mini event system. Event systems like this are a common way of maintaining a loose coupling between components in a larger system. If a component needs to perform some action as a consequence of an action of some other component, it registers to receive relevant event types. Components then use the Event Manager to re specific events and all registered subscribers wishing to receive events of that type (dependent components) are notified.

The coupling is loose because it is indirect, mediated by the publish-and-subscribe facility of the Event Manager. This is the central idea behind Message-Oriented Middleware (MOM).

The benefits of loose coupling are that expansion and reconfiguration of the system results in minimal knock-on effects to components that re or receive events. New listeners and event types should be able to be added with minimal disruption to existing code.

Key classes include the EventManager interface and its main implementation DefaultEventManager. EventListener implementations register with EventManager to receive events. When the EventManager is told to re an event, all listeners are consulted to see if the event is one that they
should handle. The methods on the EventListener interface are used by the EventManager to determine if the listener is interested in a specific event and if so, to "handle" or receive that event.