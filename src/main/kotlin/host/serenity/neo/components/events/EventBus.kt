package host.serenity.neo.components.events

class EventBus {
    private val listeners: MutableList<ListenerWrapper<*>> = mutableListOf()

    fun <T : Event> register(type: Class<T>, listener: Listener<T>) {
        listeners += ListenerWrapper(type, listener)
    }

    fun <T : Event> unregister(type: Class<T>, listener: Listener<T>) {
        listeners.removeIf { it.type == type && it.listener == listener }
    }

    fun <T : Event> post(event: T) : T {
        val type: Class<in Event> = event.javaClass

        listeners.asSequence()
                .filter { it.type.isAssignableFrom(type) }
                .map {
                    @Suppress("UNCHECKED_CAST")
                    it as Listener<T>
                }
                .forEach { it.fire(event) }

        return event
    }

    private data class ListenerWrapper<T : Event>(val type: Class<T>, val listener: Listener<T>) : Listener<T> {
        override fun fire(event: T) {
            listener.fire(event)
        }
    }
}
