package host.serenity.neo.components.events

import host.serenity.neo.components.events.util.GenericInfo

fun <T : Event> EventBus.register(listener: Listener<T>) {
    val eventClass = GenericInfo.getGenericType(listener.javaClass)

    @Suppress("UNCHECKED_CAST")
    register(eventClass as Class<T>, listener)
}
