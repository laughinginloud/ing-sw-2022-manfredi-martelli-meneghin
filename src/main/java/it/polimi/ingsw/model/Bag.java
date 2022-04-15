package it.polimi.ingsw.model;

/**
 * Class representing the bag
 * @author Sebastiano Meneghin
 */
public class Bag {
    private int[] studentCounters;

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
    public void setStudentCounters(Color color, int n){
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
}
