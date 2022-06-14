package it.polimi.ingsw.common.model;

/**
 * Class representing the school's dining room
 * To be used as part of SchoolBoard
 * @author Mattia Martelli
 */
public final class DiningRoom {
    private final int[] studentCounters = new int[Color.values().length];

    /**
     * Constructor of the class 'Dining Room'
     */
    public DiningRoom() {
        setStudentCounters(0);
    }

    /**
     * Gets the counter for a specific color
     * @param color The color of the counter (not null)
     * @return The value of the counter (positive integer)
     */
    public int getStudentCounters(Color color) {
        return studentCounters[color.ordinal()];
    }

    /**
     * Sets all the counters to a value
     * @param n Value to be set for all counters (positive integer)
     */
    public void setStudentCounters(int n) {
        assert n >= 0 && n <= 10: "The counter must be an element of [0, 10]";

        for (Color color : Color.values())
            setStudentCounters(color, n);
    }

    /**
     * Sets a specific counter to a value
     * @param color Color of the counter (not null)
     * @param n Value to be set (positive integer)
     */
    public void setStudentCounters(Color color, int n) {
        studentCounters[color.ordinal()] = n;
    }
}
