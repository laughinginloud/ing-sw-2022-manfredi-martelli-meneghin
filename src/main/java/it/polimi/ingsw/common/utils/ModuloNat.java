package it.polimi.ingsw.common.utils;

import static java.lang.Math.floorMod;

/**
 * Wrapper for a natural number in modulo n
 * @author Mattia Martelli
 */
public final class ModuloNat {
    /**
     * The concrete value of the number
     */
    private       int value;

    /**
     * The modulo bounding the value
     */
    private final int mod;

    /**
     * Create a new bounded natural with the default value of 0
     * @param mod The modulo that will bound the value
     */
    public ModuloNat(int mod) {
        this(mod, 0);
    }

    /**
     * Create a new bounded natural
     * @param mod The modulo that will bound the value
     * @param initialValue The initial value of the number
     */
    public ModuloNat(int mod, int initialValue) {
        assert mod >= 1: "Modulo must be in N+";

        this.mod = mod;
        set(initialValue);
    }

    /**
     * Return the unwrapped number
     * @return The concrete value
     */
    public int value() {
        return value;
    }

    /**
     * Modify the current value into the next number
     */
    public void next() {
        add(1);
    }

    /**
     * Modify the current value into the previous number
     */
    public void prev() {
        sub(1);
    }

    /**
     * Set the current value
     * @param value The value the number will be set to
     */
    public void set(int value) {
        this.value = mod(value);
    }

    /**
     * Add a number to the current value
     * @param n The value to add
     */
    public void add(int n) {
        set(value + n);
    }

    /**
     * Subtract a number to the current value
     * @param n The value to subtract
     */
    public void sub(int n) {
        set(value - n);
    }

    /**
     * Normalize the number passed to a value of the set (0, mod - 1)
     * @param n The number to nurmalize
     * @return The normalized number
     */
    private int mod(int n) {
        return mod(mod, n);
    }

    /**
     * Normalize the number passed to a value of the set (0, mod - 1)
     * @param n The number to normalize
     * @param mod The modulo for which to normalize
     * @return The normalized number
     */
    public static int mod(int mod, int n) {
        return floorMod(n, mod);
    }
}
