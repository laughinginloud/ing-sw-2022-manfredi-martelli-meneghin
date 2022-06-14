package it.polimi.ingsw.common.model;

/**
 * Class representing the 'EmptyBagException' i.e. when the bag is empty
 * @author Mattia Martelli & Sebastiano Meneghin
 */
public final class EmptyBagException extends Exception {

    /**
     * Constructor of the class 'EmptyBagException'
     */
    public EmptyBagException() {
        super("The bag has been emptied");
    }
}
