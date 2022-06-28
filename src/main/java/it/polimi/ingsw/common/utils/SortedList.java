package it.polimi.ingsw.common.utils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A simple sorted list, accessible via its Stream interface
 * @param <T> The type of the list
 * @author Mattia Martelli
 */
public class SortedList<T> {
    private final List<T>       list;
    private final Comparator<T> comparator;

    /**
     * Create an empty sorted list
     * @param comparator The sorting criteria to follow
     */
    public SortedList(Comparator<T> comparator) {
        this(4, comparator);
    }

    /**
     * Create an empty sorted list
     * @param initialCapcity The initial capacity of the list
     * @param comparator     The sorting criteria to follow
     */
    public SortedList(int initialCapcity, Comparator<T> comparator) {
             list       = new ArrayList<>(initialCapcity);
        this.comparator = comparator;
    }

    /**
     * Add an element to the list
     * @param elem The element to add
     */
    public void add(T elem) {
        list.add(elem);
    }

    /**
     * Return the list in stream form
     * @return The requested Stream
     */
    public Stream<T> stream() {
        return list.stream().sorted(comparator);
    }

    /**
     * Apply an action to each element of the list
     * @param action The action to apply
     */
    public void forEach(Consumer<? super T> action) {
        stream().forEach(action);
    }

    /**
     * Remove all elements from the list
     */
    public void clear() {
        list.clear();
    }

    /**
     * Remove from the list all elements that meet a criteria
     * @param filter The criteria to follow
     */
    public void removeIf(Predicate<? super T> filter) {
        list.removeIf(filter);
    }
}
