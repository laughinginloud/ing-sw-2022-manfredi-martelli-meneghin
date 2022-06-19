package it.polimi.ingsw.common.model;

/**
 * Enum representing the possible colors of the player's towers
 * @author Mattia Martelli
 */
public enum TowerColor {
    BLACK, WHITE, GREY;

    public static TowerColor fromInt(int num) {
        return values()[num];
    }
}
