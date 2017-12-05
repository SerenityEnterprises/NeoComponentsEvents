package host.serenity.neo.components.events.test;

import host.serenity.neo.components.events.Event;
import host.serenity.neo.components.events.EventBus;
import host.serenity.neo.components.events.Priority;
import org.junit.Assert;
import org.junit.Test;

public class PriorityTest {
    @Test
    public void testPriority() {
        EventBus bus = new EventBus();

        PriorityTestEvent globalEvent = new PriorityTestEvent();
        globalEvent.one = 1;
        globalEvent.two = 2;

        bus.register(PriorityTestEvent.class, event -> { if (globalEvent.one == 1) globalEvent.one = 0; }, Priority.BEFORE);
        bus.register(PriorityTestEvent.class, event -> { if (globalEvent.one == 0) globalEvent.two = 1; }, Priority.NORMAL);
        bus.register(PriorityTestEvent.class, event -> { if (globalEvent.two == 1) globalEvent.two = 0; }, Priority.AFTER);

        bus.post(globalEvent);

        Assert.assertEquals(0, globalEvent.one);
        Assert.assertEquals(0, globalEvent.two);
    }

    class PriorityTestEvent implements Event {
        int one, two;
    }
}
