package it.polimi.ingsw.model;

/**
 * Enum representing the possible Character Cards
 * @author Giovanni Manfredi
 */
public enum Character {
    // ID = 0  - Cost = 1 - Rules: 4 students on the card. Allows to move one to an island. Refills from Bag
    MONK,

    // ID = 1  - Cost = 2 - Rules: professor moves when more or equal students are on the SchoolBoard
    FARMER,

    // ID = 2  - Cost = 3 - Rules: activates the effect of motherNature on an island
    STANDARD_BEARER,

    // ID = 3  - Cost = 1 - Rules: motherNature can be moved of 2 additional tiles
    MAGICIAN,

    // ID = 4  - Cost = 2 - Rules: 4 noEntryTiles on the card. Allows to move one to an island
    HERBALIST,

    // ID = 5  - Cost = 3 - Rules: towers are not considered when calculating the influence
    CENTAUR,

    // ID = 6  - Cost = 1 - Rules: 6 students on the card. 3 can be exchange with the same number of students in an entrance
    JESTER,

    // ID = 7  - Cost = 2 - Rules: 2 additional points of influence on each island
    CAVALIER,

    // ID = 8  - Cost = 3 - Rules: the chosen Color is not considered when calculating influence on islands
    MERCHANT,

    // ID = 9  - Cost = 1 - Rules: 2 students can be exchanged between DiningRoom and Entrance of the active player
    BARD,

    // ID = 10 - Cost = 2 - Rules: 4 students on the card. Allows to move one to a SchoolBoard. Refills from Bag
    PRINCESS,

    // ID = 11 - Cost = 3 - Rules: 3 students (or all if less) of the chosen color are removed from each SchoolBoard
    THIEF;

    public static Character fromInt(int num) {
        return values()[num];
    }
}
