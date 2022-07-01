package it.polimi.ingsw.common;

/**
 * A character card action
 * @author Sebastiano Meneghin
 */
public enum PlayCharacterAction {

    /**
     * The first phase of the MONK: requests a student and the island where to move it
     */
    MONKFIRST,

    /**
     * The first phase of the STANDARD BEARER: requests the island where to calculate the "influence"
     */
    STANDARDBEARERFIRST,

    /**
     * The first phase of the HERBALIST: requests the island where to put the noEntryTile
     */
    HERBALISTFIRST,

    /**
     * The first phase of the JESTER: requests how many students to move with the effect of the card
     */
    JESTERFIRST,

    /**
     * The second phase of the JESTER: request a student to move from the Character Card
     */
    JESTERSECOND,

    /**
     * The first phase of the MERCHANT: requests which color will be inhibited during the current turn
     *
     */
    MERCHANTFIRST,

    /**
     * The first phase of the BARD: requests how many students to move with the effect of the card
     */
    BARDFIRST,

    /**
     * The first phase of the BARD: request the student to swap from the entrance
     */
    BARDSECOND,

    /**
     * The second phase of the PRINCESS: request the student to move from the CharacterCard
     */
    PRINCESSFIRST,

    /**
     * The first phase of the THIEF: request the color to remove/reduce from the diningRooms
     */
    THIEFFIRST,
}
