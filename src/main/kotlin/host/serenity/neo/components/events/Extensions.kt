package host.serenity.neo.components.events

import host.serenity.neo.components.events.util.GenericInfo
import kotlin.reflect.KClass

fun <T : Event> EventBus.register(listener: Listener<T>) {
    val eventClass = GenericInfo.getGenericType(listener.javaClass)

    @Suppress("UNCHECKED_CAST")
    register(eventClass as Class<T>, listener)
}

fun <T : Event> EventBus.register(type: KClass<T>, listener: (T) -> Unit) {
    register(type.java, object: Listener<T> {
        override fun fire(event: T) {
            listener(event)
        }
    })
}
