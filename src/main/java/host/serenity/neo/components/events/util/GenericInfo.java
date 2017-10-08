package host.serenity.neo.components.events.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericInfo {
    // Ugly hack, avert your eyes.
    public static Class<?> getGenericType(Class<?> owner) {
        Type generic = owner.getGenericSuperclass();
        if (generic instanceof ParameterizedType) {
            for (Type type : ((ParameterizedType) generic).getActualTypeArguments()) {
                if (type instanceof Class) {
                    //noinspection unchecked
                    return (Class<?>) type;
                }
            }
        }

        return null;
    }
}
