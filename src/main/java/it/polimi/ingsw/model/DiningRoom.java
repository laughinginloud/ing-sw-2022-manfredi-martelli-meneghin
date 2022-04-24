package it.polimi.ingsw.model;

/**
 * Class representing the school's dining room
 * To be used as part of SchoolBoard
 * @author Mattia Martelli
 */
public class DiningRoom {
    private final int[] studentCounters = new int[Color.values().length];

    public DiningRoom() {
        setStudentCounters(0);
    }

    /**
     * Get the counter for a specific color
     * @param color The color of the counter
     * @return The value of the counter
     */
    public int getStudentCounters(Color color) {
        return studentCounters[color.ordinal()];
    }

    /**
     * Set all the counters to a value
     * @param n Value to be set for all counters
     */
    public void setStudentCounters(int n) throws IllegalArgumentException {
        if (n < 0 || n > 10)
            throw new IllegalArgumentException("The counter must be an element of [0, 10]");

        for (Color color : Color.values()) {
            setStudentCounters(color, n);
        }
    }

    /**
     * Set a specific counter to a value
     * @param color Color of the counter
     * @param n Value to be set
     */
    public void setStudentCounters(Color color, int n) throws IllegalArgumentException {
        studentCounters[color.ordinal()] = n;
    }
}
