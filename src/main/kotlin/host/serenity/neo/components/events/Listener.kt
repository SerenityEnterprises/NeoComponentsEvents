package host.serenity.neo.components.events

interface Listener<in T : Event> {
    fun fire(event: T)
}