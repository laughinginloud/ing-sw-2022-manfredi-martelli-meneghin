package it.polimi.ingsw.model;

/**
 * Class representing the 'EmptyBagException' i.e. when the bag is empty
 * @author Mattia Martelli & Sebastiano Meneghin
 */
public class EmptyBagException extends Exception {

    /**
     * Constructor of the class 'EmptyBagException'
     */
    public EmptyBagException() {
        super("The bag has been emptied");
    }
}
