package it.polimi.ingsw.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Useful constants for both client and server
 * @author Mattia Martelli
 */
public /*static*/ final class Constants {

    // Hid the constructor to simulate the class being static
    private Constants() {}

    /**
     * JSON transformer
     */
    public static final Gson jsonBuilder = new GsonBuilder().setPrettyPrinting().create(); //TODO: spostare in constants
}
