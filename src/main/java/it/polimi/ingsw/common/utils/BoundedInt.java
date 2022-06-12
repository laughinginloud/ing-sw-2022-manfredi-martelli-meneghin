package it.polimi.ingsw.common.utils;

/**
 * Wrapper for a natural in modulo n
 * @author Mattia Martelli
 */
public final class BoundedInt {
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
    public BoundedInt(int mod) {
        assert mod >= 1: "Modulo must be in N+";

        this.mod   = mod;
             value = 0;
    }

    /**
     * Create a new bounded natural
     * @param mod The modulo that will bound the value
     * @param initialValue The initial value of the number
     */
    public BoundedInt(int mod, int initialValue) {
        assert mod >= 1: "Modulo must be in N+";

        this.mod   = mod;

        while (initialValue < 0)
            initialValue += mod;

        value = initialValue % mod;
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
        while (value < 0)
            value += mod;

        this.value = value;
    }

    /**
     * Add a number to the current value
     * @param n The value to add
     */
    public void add(int n) {
        while (n < 0)
            n += mod;

        value = (value + n) % mod;
    }

    /**
     * Subtract a number to the current value
     * @param n The value to subtract
     */
    public void sub(int n) {
        while (n < 0)
            n += mod;

        while (n >= mod)
            n -= mod;

        add(mod - n);
    }
}
