package host.serenity.neo.components.events.test;

import host.serenity.neo.components.events.Event;
import host.serenity.neo.components.events.EventBus;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class EventBusTest {
    private final EventBus bus = new EventBus();
    private int value = 0;

    @Test
    public void testBus() {
        value = 0;

        int id = bus.register(TestEvent.class, event -> {
            value += event.increment;
        });

        assertEquals("Sanity check: initial value is 0", value, 0);

        bus.post(new TestEvent(1));
        assertEquals("Event fired; mutated state.", value, 1);

        final int OFFSET = 10;
        for (int i = 0; i < OFFSET; i++) {
            bus.post(new TestEvent(2));
        }

        assertEquals("Multiple events fired", value, 1 + OFFSET * 2);

        bus.unregister(id);
    }

    class TestEvent implements Event {
        int increment;

        TestEvent(int increment) {
            this.increment = increment;
        }
    }
}
