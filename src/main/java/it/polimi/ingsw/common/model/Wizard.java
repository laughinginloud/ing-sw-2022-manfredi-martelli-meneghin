package it.polimi.ingsw.common.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Enum representing the possible backs of the cards
 * @author Mattia Martelli
 */
public enum Wizard {
    NATURE, DESERT, CLOUD, SNOW;

    /**
     * Return a set containing all the wizards
     * @return The desired set
     */
    public static Set<Wizard> set() {
        return new HashSet<>(Arrays.asList(Wizard.values()));
    }
}
