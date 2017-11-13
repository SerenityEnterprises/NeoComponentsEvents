package host.serenity.neo.components.events

abstract class CancellableEvent : Event {
    var isCancelled = false
}
