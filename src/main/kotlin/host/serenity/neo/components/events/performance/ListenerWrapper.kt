package host.serenity.neo.components.events.performance

import host.serenity.neo.components.events.Event
import host.serenity.neo.components.events.Listener

data class ListenerWrapper<T : Event>(val type: Class<T>, val listener: Listener<T>) : Listener<T> {
    override fun fire(event: T) {
        listener.fire(event)
    }
}