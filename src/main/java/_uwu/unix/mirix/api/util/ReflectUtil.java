package _uwu.unix.mirix.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * @author Unix on 06.10.2019.
 */
public final class ReflectUtil {

    private ReflectUtil() {
    }

    public static int hashCode(Object... objects) {
        return Arrays.deepHashCode(objects);
    }

    @NotNull
    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }

        try {
            final StringBuilder stringBuilder = new StringBuilder(object.getClass().getSimpleName()).append('(');
            final List<Field> allDeclaredFields = getAllDeclaredFields(object.getClass());
            IntStream.range(0, allDeclaredFields.size()).forEachOrdered(i -> {
                try {
                    if (i > 0) {
                        stringBuilder.append(", ");
                    }

                    final Field field = allDeclaredFields.get(i);
                    field.setAccessible(true);
                    stringBuilder.append(field.getName()).append('=').append(memberToString(field.get(object)));
                } catch (IllegalAccessException ex) {
                    Logger.getLogger("ReflectUtil").log(Level.SEVERE, "Cannot access field", ex);
                }
            });

            return stringBuilder.append(')').toString();
        } catch (Throwable throwable) {
            return object.getClass().getSimpleName() + '@' + Integer.toHexString(object.hashCode()) + '(' + throwable.toString() + ')';
        }
    }

    @Contract("null -> !null")
    private static String memberToString(final Object object) {
        if (object == null) {
            return "null";
        }

        if (object.getClass().isArray()) {
            final int length = Array.getLength(object);
            if (length > 20) {
                return object.getClass().getSimpleName() + "(length=" + length + ')';
            }

            final StringBuilder stringBuilder = new StringBuilder("[");
            IntStream.range(0, length).forEachOrdered(index -> {
                if (index > 0) {
                    stringBuilder.append(", ");
                }

                stringBuilder.append(memberToString(Array.get(object, index)));
            });

            return stringBuilder.append(']').toString();
        }

        return object.toString();
    }

    @NotNull
    private static List<Field> getAllDeclaredFields(Class<?> clazz) {
        final List<Field> fields = new ArrayList<>();
        while (Objects.nonNull(clazz)) {
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .forEach(fields::add);

            clazz = clazz.getSuperclass();
        }

        return fields;
    }
}