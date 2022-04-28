package it.polimi.ingsw.model;

import java.util.Random;

/**
 * Class representing the bag
 * @author Sebastiano Meneghin
 */
public class Bag {
    private final int[] studentCounters;

    public Bag(){
        studentCounters = new int[Color.values().length];
        setStudentCounters(0);
    }

    /**
     * Return the current value of a specific studentCounter
     * @param color Color of the studentCounter to return
     * @return An int representing the current value
     */
    public int getStudentCounters(Color color){
        return studentCounters[color.ordinal()];
    }

    /**
     * Set a studentCounter of a specific color to a specific value
     * @param color Color of the studentCounter to set
     * @param n Int to set studentCounter to
     */
    public void setStudentCounters(Color color, int n) throws IllegalArgumentException {
        if (n < 0 || n > 26)
            throw new IllegalArgumentException("Bag.studentCounters accepted value is between 0 and 26");

        this.studentCounters[color.ordinal()] = n;
    }

    /**
     * Set all studentCounter of the bag to a specific value
     * @param n Int to set all studentCounter to
     */
    public void setStudentCounters(int n){
        for (Color color : Color.values()) {
            setStudentCounters(color, n);
        }
    }

    /**
     * Draw a random student from the Bag
     * @return Color of the student selected
     * @throws EmptyBagException Launched when trying to draw a student from an empty bag
     */
    public Color drawStudents() throws EmptyBagException {
        int numOfStudents = 0;

        for (Color color : Color.values())
            numOfStudents = numOfStudents + getStudentCounters(color);

        if (numOfStudents == 0)
            throw new EmptyBagException("The bag has been emptied");

        int seed = ((int) (Math.random() * 4));
        while (getStudentCounters((Color.values())[seed]) == 0)
            seed = ((int) (Math.random() * 4));

        int counterValue = getStudentCounters(Color.values()[seed]);
        setStudentCounters(Color.values()[seed], counterValue - 1);

        return Color.values()[seed];
    }

    /**
     * Draw a specific number of random students from the bag
     * @param n Int representing the number of students to be drawn
     * @return An array of Color containing the students drawn
     * @throws EmptyBagException Launched when trying to draw a student from an empty bag
     */
    public Color[] drawStudents(int n) throws EmptyBagException {
        Color[] students = new Color[n];
        for (int i = 0; i < n; i++)
            students[i] = drawStudents();

        return students;
    }
}
