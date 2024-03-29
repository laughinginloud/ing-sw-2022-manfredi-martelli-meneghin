package it.polimi.ingsw.common.utils;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

import static java.lang.Thread.currentThread;

/**
 * Static class containing miscellaneous utility methods
 * @author Mattia Martelli
 */
public /*static*/ final class Methods {

    private Methods() {}

    // region Array copy

    /**
     * Deep copy an array
     * @param array The array to copy
     * @return      A deep copy of the array
     * @param <T>   The type of the array
     */
    public static <T> T[] copyOf(T[] array) {
        if (array == null)
            return null;

        return Arrays.copyOf(array, array.length);
    }

    /**
     * Deep copy an array
     * @param array The array to copy
     * @return      A deep copy of the array
     */
    public static int[] copyOf(int[] array) {
        if (array == null)
            return null;

        return Arrays.copyOf(array, array.length);
    }

    /**
     * Deep copy an array
     * @param array The array to copy
     * @return      A deep copy of the array
     */
    public static boolean[] copyOf(boolean[] array) {
        if (array == null)
            return null;

        return Arrays.copyOf(array, array.length);
    }

    // endregion

    // region Null check

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
        ifNotNull(obj, () -> action.accept(obj));
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
     * Execute an action based on the state of the object
     * @param obj       The object to null-check
     * @param ifNotNull The action to perform if the object is not null
     * @param ifNull    The action to perform it the object is null
     */
    public static <T> void ifNotNullOrElse(T obj, Consumer<T> ifNotNull, Runnable ifNull) {
        ifNotNullOrElse(obj, () -> ifNotNull.accept(obj), ifNull);
    }

    // endregion

    // region Thread

    /**
     * Check whether the current thread has been interrupted
     * @return <code>true</code> if the thread has been interrupted, <code>false</code> otherwise
     */
    public static boolean interrupted() {
        return currentThread().isInterrupted();
    }

    /**
     * Perform an action asynchronously
     * @param action The action to perform
     */
    public static void async(Runnable action) {
        (new Thread(action)).start();
    }

    /**
     * Perform an action asynchronously
     * @param action The action to perform
     * @param param  The parameter of the action
     * @param <T>    The type of the parameter
     */
    public static <T> void async(Consumer<T> action, T param) {
        async(() -> action.accept(param));
    }

    // endregion

    // region Miscellaneous

    /**
     * Capitalize the first letter of the string, whilst putting in lower case the rest
     * @param str The string to capitalize
     * @return    The capitalized string
     */
    public static String capitalize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    /**
     * Find the index of an array's element
     * @param elem  The element to search for
     * @param array The array to search in
     * @return Optional containing the index, if it exists
     * @param <T> The type of the array
     */
    public static <T> Optional<Integer> elemindexOpt(T elem, T[] array) {
        for (int i = 0; i < array.length; ++i)
            if (elem.equals(array[i]))
                return Optional.of(i);

        return Optional.empty();
    }

    /**
     * Find the index of an array's element
     * @param elem  The element to search for
     * @param array The array to search in
     * @return The index of the element
     * @param <T> The type of the array
     * @throws NoSuchElementException If the element is not in the array
     */
    public static <T> int elemIndex(T elem, T[] array) throws NoSuchElementException {
        return elemindexOpt(elem, array).orElseThrow();
    }

    /**
     * Check whether assertions have been enabled
     * @return <code>true</code> if assertions are enabled, <code>false</code> otherwise
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean assertionsEnabled() {
        // Start by assuming that the assertions are disabled
        boolean assertions = false;

        // Try to assert false, which is guaranteed to fail if checked
        try {
            assert false;
        }

        // If it failed, it means that assertions are enabled
        catch (AssertionError ignored) {
            assertions = true;
        }

        return assertions;
    }

    // endregion

}
