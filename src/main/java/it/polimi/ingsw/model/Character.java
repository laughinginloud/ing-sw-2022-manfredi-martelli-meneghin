package it.polimi.ingsw.model;

/**
 * Enum representing the possible Character Cards
 * @author Giovanni Manfredi
 */
public enum Character {
    MONK,
    FARMER,
    STANDARD_BEARER,
    MAGICIAN,
    HERBALIST,
    CENTAUR,
    JESTER,
    CAVALIER,
    MERCHANT,
    BARD,
    PRINCESS,
    THIEF;

    public static Character fromInt(int num) {
        return values()[num];
    }
}
