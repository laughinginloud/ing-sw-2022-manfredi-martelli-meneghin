package it.polimi.ingsw.common.model;

import java.util.Random;

/**
 * Class representing the bag
 * @author Sebastiano Meneghin
 */
public final class Bag {

    // region Fields

    private final int[]  studentCounters;
    private       int    numOfStudents;

    private final Random random;

    // endregion

    // region Constructor and setters

    /**
     * Constructor of the class 'Bag'
     */
    public Bag(){
        studentCounters = new int[Color.values().length];
        setStudentCounters(0);
        numOfStudents = 0;
        random = new Random();
    }

    /**
     * Sets a studentCounter of a specific color to a specific value
     * @param color Color of the studentCounter to set (not null)
     * @param n Int to set studentCounter to (positive integer between 0 and 16 included)
     */
    public void setStudentCounters(Color color, int n) {
        assert n >= 0 && n <= 26: "Bag.studentCounters accepted value is between 0 and 26";

        numOfStudents = numOfStudents - studentCounters[color.ordinal()] + n;

        this.studentCounters[color.ordinal()] = n;
    }

    /**
     * Sets all studentCounter of the bag to a specific value
     * @param n Int to set all studentCounter to (positive integer between 0 and 16 included)
     */
    public void setStudentCounters(int n){
        for (Color color : Color.values())
            setStudentCounters(color, n);
    }

    // endregion

    // region Getters

    /**
     * Gets the current value of a specific studentCounter
     * @param color Color of the studentCounter to return (not null)
     * @return An int representing the current value of the specified color
     *         (positive integer between 0 and 16 included)
     */
    public int getStudentCounters(Color color){
        return studentCounters[color.ordinal()];
    }

    /**
     * Draws a random student from the Bag
     * @author Mattia Martelli & Sebastiano Meneghin
     * @return Color of the student selected (not null)
     */
    private Color drawStudents() {
        int randomColorIndex = random.nextInt(5);

        while (studentCounters[randomColorIndex] == 0)
            randomColorIndex = (randomColorIndex + 1) % 5;

        studentCounters[randomColorIndex]--;

        return Color.values()[randomColorIndex];
    }

    /**
     * Draws a specific number of random students from the bag
     * @author Mattia Martelli & Sebastiano Meneghin
     * @param n Int representing the number of students to be drawn (a positive integer)
     * @return A record containing the array of students extracted and a flag that indicates whether
     *         the bag is now empty. Please note that the function is "best effort", so if there are not
     *         enough students available, the array will be shorter than requested
     * @throws EmptyBagException Launched when trying to draw a student from an empty bag
     */
    public BagResult drawStudents(int n) throws EmptyBagException {
        if (numOfStudents == 0)
            throw new EmptyBagException();

        int length = Math.min(n, numOfStudents);
        Color[] students = new Color[length];
        for (int i = 0; i < length; i++)
            students[i] = drawStudents();

        return new BagResult(students, numOfStudents == 0);
    }

    // endregion

}
