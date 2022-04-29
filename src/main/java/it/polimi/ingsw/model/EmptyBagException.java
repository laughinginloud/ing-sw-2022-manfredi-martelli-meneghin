package it.polimi.ingsw.model;

public class EmptyBagException extends Exception {
    public EmptyBagException() {
        super("The bag has been emptied");
    }
}
