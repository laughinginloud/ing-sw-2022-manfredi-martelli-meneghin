package it.polimi.ingsw.common.utils;

import java.util.function.Function;

/**
 * A tuple of two elements
 * @param left The first element
 * @param right The second element
 * @param <Left> The type of the first element
 * @param <Right> The type of the second element
 * @author Mattia Martelli
 */
public record Tuple<Left, Right>(Left left, Right right) {
    /**
     * Swap the elements of the tuple
     * @return A new tuple with the elements swapped
     */
    public Tuple<Right, Left> swap() {
        return new Tuple<>(right, left);
    }

    /**
     * Map the first element of the tuple to a new domain
     * @param map The mapper function
     * @return The new tuple
     * @param <NewLeft> The type of the new domain
     */
    public <NewLeft> Tuple<NewLeft, Right> mapLeft(Function<Left, NewLeft> map) {
        return new Tuple<>(map.apply(left), right);
    }

    /**
     * Map the second element of the tuple to a new domain
     * @param map The mapper function
     * @return The new tuple
     * @param <NewRight> The type of the new domain
     */
    public <NewRight> Tuple<Left, NewRight> mapRight(Function<Right, NewRight> map) {
        return new Tuple<>(left, map.apply(right));
    }

    /**
     * Map the tuple to a new domain
     * @param map The mapper function
     * @return The new tuple
     * @param <NewLeft> The type of the new domain
     * @param <NewRight> The type of the new codomain
     */
    public <NewLeft, NewRight> Tuple<NewLeft, NewRight> map(Function<Tuple<Left, Right>, Tuple<NewLeft, NewRight>> map) {
        return map.apply(this);
    }

    /**
     * Map the tuple to a new domain
     * @param leftMap The mapper function for the left side
     * @param rightMap The mapper function for the right side
     * @return The new tuple
     * @param <NewLeft> The type of the new domain
     * @param <NewRight> The type of the new codomain
     */
    public <NewLeft, NewRight> Tuple<NewLeft, NewRight> map(Function<Left, NewLeft> leftMap, Function<Right, NewRight> rightMap) {
        return mapLeft(leftMap).mapRight(rightMap);
    }
}
