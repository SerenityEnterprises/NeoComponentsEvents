# NeoComponentsEvents

A simplistic, lightweight event system written in Kotlin.

## Usage:

```java
EventBus bus = new EventBus();
bus.register(Foo.class, foo -> System.out.println(foo.i));
bus.fire(new Foo(2));
```
