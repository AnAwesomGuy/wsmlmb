package net.anawesomguy.wsmlmb.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Very simple class containing utilities for working with {@link List}s and Arrays.
 */
public final class ListArrayUtil {
    public static final Object[] EMPTY_ARRAY = {};

    public static <T> T last(T[] array) {
        return array.length < 1 ? null : array[array.length - 1];
    }

    public static <T> T lastOrThrow(T[] array) {
        return array[array.length - 1];
    }

    public static <T> T last(List<T> list) {
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }

    public static <T> T lastOrThrow(List<T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> T[] removeLast(T[] array) {
        return array.length < 1 ? array : Arrays.copyOf(array, array.length - 1);
    }

    public static <T> T removeLast(List<T> list) {
        if (list.isEmpty())
            return null;
        T result = list.remove(list.size() - 1);
        if (list instanceof ArrayList<T>)
            ((ArrayList<T>)list).trimToSize();
        return result;
    }

    public static <T> T[] removeFirst(T[] array) {
        return array.length < 1 ? array : Arrays.copyOfRange(array, 1, array.length);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] emptyArray() {
        return (T[])EMPTY_ARRAY;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Collection<T> coll) {
        return coll.toArray((T[])EMPTY_ARRAY);
    }

    @SafeVarargs
    public static <T> ArrayList<T> list(T... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }

    private ListArrayUtil() {
        throw new AssertionError("Cannot instantiate ListArrayUtil!");
    }
}
