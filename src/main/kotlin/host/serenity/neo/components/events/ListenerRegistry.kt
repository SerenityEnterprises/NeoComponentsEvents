package host.serenity.neo.components.events

import host.serenity.neo.components.events.performance.ListenerWrapper
import java.util.concurrent.CopyOnWriteArraySet

class ListenerRegistry {
    val listeners: Array<MutableSet<ListenerWrapper<*>>> = Array(Priority.values().size, { CopyOnWriteArraySet<ListenerWrapper<*>>() })

    operator fun get(priority: Priority): MutableSet<ListenerWrapper<*>> {
        return listeners[priority.ordinal]
    }
}