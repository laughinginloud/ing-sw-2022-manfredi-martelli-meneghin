package it.polimi.ingsw.common.utils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class representing an isomorphism between two classes
 * @param <Left> The "left side" of the isomorphism, i.e. the domain
 * @param <Right> The "right side" of the isomorphism, i.e. the codomain
 */
@SuppressWarnings("unused")
public final class Isomorphism<Left, Right> {

    // region Fields

    /**
     * Map Left -> Right
     */
    private final Map<Left, Right> leftRightMap;

    /**
     * Map Right -> Left
     */
    private final Map<Right, Left> rightLeftMap;

    // endregion

    // region Constructors

    /**
     * Create an empty isomorphism
     */
    public Isomorphism() {
        leftRightMap = new HashMap<>();
        rightLeftMap = new HashMap<>();
    }

    /**
     * Create an empty isomorphism
     * @param initialCapacity The initial capacity of the morphism
     */
    public Isomorphism(int initialCapacity) {
        leftRightMap = new HashMap<>(initialCapacity);
        rightLeftMap = new HashMap<>(initialCapacity);
    }

    // endregion

    // region Getters

    /**
     * Returns the value of the domain corresponding the specified codomain value
     * @param right The value of the codomain
     * @return The value of the domain, or null if none exists
     */
    public Left getLeft(Right right) {
        return rightLeftMap.get(right);
    }

    /**
     * Returns the value of the codomain corresponding the specified domain value
     * @param left The value of the domain
     * @return The value of the codomain, or null if none exists
     */
    public Right getRight(Left left) {
        return leftRightMap.get(left);
    }

    /**
     * Returns whether the specified domain value exists in the isomorphism
     * @param left The value to search
     * @return A boolean representing the result of the search
     */
    public boolean containsLeft(Left left) {
        return leftRightMap.containsKey(left);
    }

    /**
     * Returns whether the specified codomain value exists in the isomorphism
     * @param right The value to search
     * @return A boolean representing the result of the search
     */
    public boolean containsRight(Right right) {
        return rightLeftMap.containsKey(right);
    }

    // endregion

    // region Setters

    /**
     * Adds the specified values to the isomorphism, if either values are not already present
     * @param left  The value of the domain
     * @param right The value of the codomain
     * @return A boolean representing whether the insertion was successful
     */
    public boolean put(Left left, Right right) {
        if (leftRightMap.containsKey(left) || rightLeftMap.containsKey(right)
            || left == null || right == null)
            return false;

        leftRightMap.put(left, right);
        rightLeftMap.put(right, left);

        return true;
    }

    /**
     * Clears all the data from the isomorphism
     */
    public void clear() {
        leftRightMap.clear();
        rightLeftMap.clear();
    }

    /**
     * Remove a tuple from the isomorphism
     * @param left The domain value of the tuple
     * @return The codomain value of the tuple that was removed
     */
    public Right removeLeft(Left left) {
        rightLeftMap.remove(leftRightMap.get(left));
        return leftRightMap.remove(left);
    }

    /**
     * Remove a tuple from the isomorphism
     * @param right The codomain value of the tuple
     * @return The domain value of the tuple that was removed
     */
    public Left removeRight(Right right) {
        leftRightMap.remove(rightLeftMap.get(right));
        return rightLeftMap.remove(right);
    }

    // endregion

    // region Functional interface

    /**
     * Traverse the isomorphism, discarding the intermediate results
     * @param action The action to apply to each element of the isomorphism
     */
    public void forEach(BiConsumer<? super Left, ? super Right> action) {
        leftRightMap.forEach(action);
    }

    /**
     * Traverse the isomorphism, appending the intermediate results to a list
     * @param action The action to apply to each element of the isomorphism
     * @return A list containing the intermediate results
     * @param <T> The type of the result
     */
    public <T> List<T> traverse(BiFunction<? super Left, ? super Right, T> action) {
        List<T> result = new ArrayList<>();
        entrySet().forEach(e -> result.add(action.apply(e.getKey(), e.getValue())));
        return result;
    }

    /**
     * Map the left side of the isomorphism to a new domain
     * @param map The mapper function
     * @return The new isomorphism
     * @param <NewLeft> The type of the new domain
     */
    public <NewLeft> Isomorphism<NewLeft, Right> mapLeft(Function<Left, NewLeft> map) {
        Isomorphism<NewLeft, Right> result = new Isomorphism<>();
        entrySet().forEach(e -> result.put(map.apply(e.getKey()), e.getValue()));
        return result;
    }

    /**
     * Map the right side of the isomorphism to a new domain
     * @param map The mapper function
     * @return The new isomorphism
     * @param <NewRight> The type of the new domain
     */
    public <NewRight> Isomorphism<Left, NewRight> mapRight(Function<Right, NewRight> map) {
        Isomorphism<Left, NewRight> result = new Isomorphism<>();
        entrySet().forEach(e -> result.put(e.getKey(), map.apply(e.getValue())));
        return result;
    }

    /**
     * Map the isomorphism to a new domain
     * @param leftMap  The mapper function for the left side
     * @param rightMap The mapper function for the right side
     * @return The new isomorphism
     * @param <NewLeft>  The type of the new left domain
     * @param <NewRight> The type of the new right domain
     */
    public <NewLeft, NewRight> Isomorphism<NewLeft, NewRight> map(Function<Left, NewLeft> leftMap, Function<Right, NewRight> rightMap) {
        return mapLeft(leftMap).mapRight(rightMap);
    }

    /**
     * Swap the domain with the codomain
     * @return The new isomorphism with the domains swapped
     */
    public Isomorphism<Right, Left> swap() {
        Isomorphism<Right, Left> result = new Isomorphism<>();
        entrySet().forEach(e -> result.put(e.getValue(), e.getKey()));
        return result;
    }

    /**
     * Creates a Set containing the entries the isomorphism
     * @return A Set containing a copy of the entries
     */
    public Set<Map.Entry<Left, Right>> entrySet() {
        return new HashSet<>(leftRightMap.entrySet());
    }

    /**
     * Return a set of tuples representing the isomorphism
     * @return An unordered Set(Tuple(Left, Right))
     */
    public Set<Tuple<Left, Right>> tupleSet() {
        return entrySet().stream().map(e -> new Tuple<>(e.getKey(), e.getValue())).collect(Collectors.toSet());
    }

    /**
     * Map the isomorphism to a new domain
     * @param map The mapper function
     * @return The new isomorphism
     * @param <NewLeft>  The type of the new left domain
     * @param <NewRight> The type of the new right domain
     */
    public <NewLeft, NewRight> Isomorphism<NewLeft, NewRight> map(Function<Isomorphism<Left, Right>, Isomorphism<NewLeft, NewRight>> map) {
        return map.apply(this);
    }

    // endregion

}
