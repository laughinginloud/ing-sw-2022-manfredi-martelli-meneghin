package it.polimi.ingsw.common.utils;


import java.util.Arrays;
import java.util.function.Consumer;

import static java.lang.Thread.currentThread;

/**
 * Static class containing miscellaneous utility methods
 * @author Mattia Martelli
 */
public /*static*/ final class Methods {
    private Methods() {}

    /**
     * Execute the action if the object is not null
     * @param obj    The object to null-check
     * @param action The action to perform
     */
    public static void ifNotNull(Object obj, Runnable action) {
        if (obj != null)
            action.run();
    }

    /**
     * Consume the object if it is not null
     * @param obj    The object to null-check
     * @param action The consumer to activate
     * @param <T>    The type of the object and the consumer
     */
    public static <T> void ifNotNull(T obj, Consumer<T> action) {
        if (obj != null)
            action.accept(obj);
    }

    /**
     * Execute an action based on the state of the object
     * @param obj       The object to null-check
     * @param ifNotNull The action to perform if the object is not null
     * @param ifNull    The action to perform it the object is null
     */
    public static void ifNotNullOrElse(Object obj, Runnable ifNotNull, Runnable ifNull) {
        if (obj != null)
            ifNotNull.run();

        else
            ifNull.run();
    }

    /**
     * Capitalize the first letter of the string, whilst putting in lower case the rest
     * @param str The string to capitalize
     * @return    The capitalized string
     */
    public static String capitalize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    /**
     * Deep copy an array
     * @param array The array to copy
     * @return      A deep copy of the array
     * @param <T>   The type of the array
     */
    public static <T> T[] copyOf(T[] array) {
        return Arrays.copyOf(array, array.length);
    }

    /**
     * Deep copy an array
     * @param array The array to copy
     * @return      A deep copy of the array
     */
    public static int[] copyOf(int[] array) {
        return Arrays.copyOf(array, array.length);
    }

    /**
     * Deep copy an array
     * @param array The array to copy
     * @return      A deep copy of the array
     */
    public static boolean[] copyOf(boolean[] array) {
        return Arrays.copyOf(array, array.length);
    }

    /**
     * Check whether the current thread has been interrupted
     * @return <code>true</code> if the thread has been interrupted, <code>false</code> otherwise
     */
    public static boolean interrupted() {
        return currentThread().isInterrupted();
    }
}
