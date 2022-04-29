package it.polimi.ingsw.model;

public class EmptyBagException extends Exception {
    public EmptyBagException (String errorMessage) {
        super(errorMessage);
    }
}
