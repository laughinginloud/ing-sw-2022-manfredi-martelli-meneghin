package it.polimi.ingsw.model;

/**
 * Class representing the bag
 */
public class Bag {
    private int[] studentCounters;
    private static Bag instance;

    private Bag(){
        studentCounters = new int[5];
    }

    /**
     * Return the instance of the bag. If the instance doesn't exist it creates a new Bag.
     * @return The unique bag
     */
    public static Bag getInstance(){
        if(instance == null){
            instance = new Bag();
        }
        return instance;
    }

    /**
     * Return the current value of a specific studentCounter
     * @param color Color of the studentCounter to return
     * @return An int representing the current value
     */
    public int getStudentCounter (Color color){
        return studentCounters[color.ordinal()];
    }

    /**
     * Set a studentCounter of a specific color to a specific value
     * @param color Color of the studentCounter to set
     * @param n Int to set studentCounter to
     */
    public void setStudentCounter (Color color, int n){
        this.studentCounters[color.ordinal()] = n;
    }

    /**
     * Set all studentCounter of the bag to a specific value
     * @param n Int to set all studentCounter to
     */
    public void SetStudentCounter (int n){
        for (int i = 0; i < studentCounters.length; i++){
            this.studentCounters[i] = n;
        }
    }
}
