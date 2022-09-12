package it.polimi.ingsw.common.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * A lazy, stable sorted list
 * @param <T> The type of the list
 * @author Mattia Martelli
 */
@SuppressWarnings("unused")
public final class SortedList<T> {
    /**
     * The concrete list
     */
    private final List<T> list;

    /**
     * The comparator used to sort the list
     */
    private final Comparator<T> comparator;

    /**
     * A flag to indicate whether the list is currently sorted
     */
    private       boolean       sorted;

    /**
     * Create an empty, naturally-sorted list
     */
    public SortedList() {
        //noinspection unchecked
        this((Comparator<T>) Comparator.naturalOrder());
    }

    /**
     * Create an empty sorted list
     * @param comparator The sorting criteria to follow
     */
    public SortedList(Comparator<T> comparator) {
        this(10, comparator);
    }

    /**
     * Create an empty sorted list
     * @param initialCapcity The initial capacity of the list
     * @param comparator     The sorting criteria to follow
     */
    public SortedList(int initialCapcity, Comparator<T> comparator) {
             list       = new ArrayList<>(initialCapcity);
        this.comparator = comparator;
             sorted     = false;
    }

    /**
     * Add an element to the list
     * @param elem The element to add
     */
    public void add(T elem) {
        list.add(elem);
        sorted = false;
    }

    /**
     * Sort the list according to the provided criteria
     */
    private void sort() {
        if (sorted)
            return;

        list.sort(comparator);
        sorted = true;
    }

    /**
     * Return the list in stream form
     * @return The requested Stream
     */
    public Stream<T> stream() {
        sort();
        return list.stream();
    }

    /**
     * Return the list in normal list form
     * @return A new list containing the same elements
     */
    public List<T> list() {
        sort();
        return new ArrayList<>(list);
    }

    /**
     * Transform the list in a new collection
     * @param collector The function describing the collection
     * @return A new collection
     * @param <A> The type of the collector's accumulator
     * @param <R> The type of the new collection
     */
    public <A, R> R collect(Collector<? super T, A, R> collector) {
        sort();
        return list.stream().collect(collector);
    }

    /**
     * Map the list to a new type
     * @param mapper     The mapping function
     * @param comparator The comparator of the new <code>SortedList</code>
     * @return A new <code>SortedList</code> with the specified mapping applied
     * @param <R> The type of the new <code>SortedList</code>
     */
    public <R> SortedList<R> map(Function<T, R> mapper, Comparator<R> comparator) {
        SortedList<R> newList = new SortedList<>(list.size(), comparator);
        newList.list.addAll(list.stream().map(mapper).toList());
        newList.sorted = false;
        return newList;
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

    /**
     * Check whether an element is part of the list
     * @param elem The element to search for
     * @return A bool representing the result
     */
    public boolean contains(T elem) {
        return list.contains(elem);
    }

    /**
     * Remove an element from the list
     * @param elem The element to remove
     * @return <code>true</code> if the element was actually removed,
     * <code>false</code> otherwise
     */
    public boolean remove(T elem) {
        return list.remove(elem);
    }
}
