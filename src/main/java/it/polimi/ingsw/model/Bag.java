package it.polimi.ingsw.model;

/**
 * Class representing the bag
 * @author Sebastiano Meneghin
 */
public class Bag {
    private final int[] studentCounters;
    private       int   numOfStudents;

    public Bag(){
        studentCounters = new int[Color.values().length];
        setStudentCounters(0);
        numOfStudents = 0;
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

        numOfStudents = numOfStudents - studentCounters[color.ordinal()] + n;

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
     */
    private Color drawStudents() {
        int randomColorIndex;
        do {
            randomColorIndex = ((int) (Math.random() * 4));
        } while (studentCounters[randomColorIndex] == 0);

        studentCounters[randomColorIndex]--;

        return Color.values()[randomColorIndex];
    }

    /**
     * Draw a specific number of random students from the bag
     * @param n Int representing the number of students to be drawn
     * @return A variable length array of Color containing the students drawn (if there are fewer students than required, its size is <n)
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
}
