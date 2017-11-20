package host.serenity.neo.components.events

import host.serenity.neo.components.events.performance.ListenerTracker
import host.serenity.neo.components.events.performance.ListenerWrapper
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.CopyOnWriteArraySet

class EventBus {
    private val listeners: ConcurrentMap<Class<*>, MutableSet<ListenerWrapper<*>>> = ConcurrentHashMap()
    private val tracker = ListenerTracker()

    fun <T : Event> register(type: Class<T>, listener: Listener<T>): Int {
        listeners.putIfAbsent(type, CopyOnWriteArraySet())

        val wrapper = ListenerWrapper(type, listener)
        val set: MutableSet<ListenerWrapper<*>> = listeners[type]!!
        set += wrapper

        return tracker.add(wrapper, set)
    }

    fun <T : Event> unregister(listener: Listener<T>) {
        val id: Int? = tracker.getID(listener)
        if (id != null) {
            unregister(id)
        }
    }

    fun unregister(id: Int) {
        tracker.getContainingSet(id)?.remove(tracker.getListenerWrapper(id))
        tracker.removeReference(id)
    }

    fun <T : Event> post(event: T) : T {
        val type: Class<in Event> = event.javaClass

        listeners[type]?.asSequence()
                ?.map {
                    @Suppress("UNCHECKED_CAST")
                    it as Listener<T>
                }
                ?.forEach { it.fire(event) }

        return event
    }
}
