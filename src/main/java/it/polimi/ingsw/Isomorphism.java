package it.polimi.ingsw;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Class representing an isomorphism between two classes
 * @param <Left> The "left side" of the isomorphism, i.e. the domain
 * @param <Right> The "right side" of the isomorphism, i.e. the codomain
 */
public class Isomorphism<Left, Right> {
    private final Map<Left, Right> leftRightMap;
    private final Map<Right, Left> rightLeftMap;

    public Isomorphism() {
        leftRightMap = new HashMap<>();
        rightLeftMap = new HashMap<>();
    }

    public Isomorphism(int initialCapacity) {
        leftRightMap = new HashMap<>(initialCapacity);
        rightLeftMap = new HashMap<>(initialCapacity);
    }

    /**
     * Adds the specified values to the isomorphism, if eiether values are not already present
     * @param left The value of the domain
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
     * Returns the value of the domain corresponding the specified codomain value
     * @param right The value of the codomain
     * @return The value of the domain
     */
    public Left getLeft(Right right) {
        return rightLeftMap.get(right);
    }

    /**
     * Returns the value of the codomain corresponding the specified domain value
     * @param left The value of the domain
     * @return The value of the codomain
     */
    public Right getRight(Left left) {
        return leftRightMap.get(left);
    }

    /**
     * Returns wheter or not the specified domain value exists in the isomorphism
     * @param left The value to search
     * @return A boolean representing the result of the search
     */
    public boolean containsLeft(Left left) {
        return leftRightMap.containsKey(left);
    }

    /**
     * Returns wheter or not the specified codomain value exists in the isomorphism
     * @param right The value to search
     * @return A boolean representing the result of the search
     */
    public boolean containsRight(Right right) {
        return rightLeftMap.containsKey(right);
    }

    /**
     * Clears all the data from the isomorphism
     */
    public void clear() {
        leftRightMap.clear();
        rightLeftMap.clear();
    }

    /**
     * Traverse the isomorphism, discarding the intermediate results
     * @param action The action to apply to each element of the isomorphism
     */
    public void forEach(BiConsumer<? super Left, ? super Right> action) {
        leftRightMap.forEach(action);
    }

    /**
     * Creates a Set containing the entries the isomorphism
     * @return A Set containing a copy of the entries
     */
    public Set<Map.Entry<Left, Right>> entrySet() {
        return new HashSet<>(leftRightMap.entrySet());
    }
}
