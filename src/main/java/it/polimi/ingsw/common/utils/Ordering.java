package it.polimi.ingsw.common.utils;

/**
 * Enum to ease reading comparisons' results, which wraps Comparable's <code>compareTo</code>
 * @author Mattia Martelli
 */
public enum Ordering {
    LT, GT, EQ;

    /**
     * Function that compares two comparable objects, returning a result relative to the first (e.g. LT <=> a < b)
     * @param a The first object
     * @param b The second object
     * @return The result of the compare
     */
    public static <T> Ordering compare(Comparable<T> a, T b) {
        int compRes = a.compareTo(b);

        if (compRes < 0)
            return LT;

        if (compRes > 0)
            return GT;

        return EQ;
    }
}
