package it.polimi.ingsw.client.virtualController;

/**
 * Enumeration representing the internal states of the Virtual Controller, used in the
 * client in order to manage the right asynchronous response from the user
 * @author Sebastiano Meneghin
 */
public enum VCStates {

    /**
     * State where the player have to insert Username and Magic Age
     */
    REQ_USER_AGE,

    /**
     * State where the player have to ask the rules he wants to play with
     */
    REQ_RULES,

    /**
     * State where the player have to insert the wanted Wizard Deck
     */
    REQ_WIZARD,

    /**
     * State where the player is asked to reload a past game or start a new one
     */
    REQ_LOAD,

    /**
     * State where the player is asked to play an Assistant Card
     */
    REQ_PLAY_ASS,

    /**
     * State where the player is asked to select a student from the entrance
     */
    REQ_MOVE_STUD_FIRST,

    /**
     * State where the player is asked to select an island or a Dining Room
     * where to move the selected student
     */
    REQ_MOVE_STUD_SECOND,

    /**
     * State where to player is asked to select an island where to move Mother Nature
     */
    REQ_MOVE_MN,

    /**
     * State where the player is asked to choose a Cloud Tile
     */
    REQ_CHOOSE_CLOUD,

    /**
     * State where the player is asked to move a student or to play
     * a Character Card - only ExpertMode
     */
    REQ_MOVE_STUD_OR_PLAY,

    /**
     * State where the player is asked to move Mother Nature or to play
     * a Character Card - only ExpertMode
     */
    REQ_MOVE_MN_OR_PLAY,

    /**
     * State where the player is asked to choose a Cloud Tile or
     * a Character Card - only ExpertMode
     */
    REQ_CHOOSE_CLOUD_OR_PLAY,

    /**
     * State where the player is asked to end them turn or play
     * a Character Card - only ExpertMode
     */
    REQ_END_TURN_ASK,

    /**
     * State where the player is asked to select a Character Card
     */
    REQ_END_TURN_PLAY,


    /* *************************************************************  /
    /  The following VCStates are related to the CharacterCardEffect /
    / *************************************************************  */

    /**
     * State where the player is asked to select a student from the Character Card
     */
    REQ_MONK_FIRST__STUDENT,

    /**
     * State where the player is asked to select the Island where to move the selectedStudent
     */
    REQ_MONK_FIRST__ISLAND,

    /**
     * State where the player is asked select the Island where to calculate the
     * "influence" or where to put the noEntryTile
     */
    REQ_BEARER_FIRST_OR_HERB_FIRST,

    /**
     * State where the player is asked to choose how many students to move
     * using the effect of the JESTER Character Card
     */
    REQ_JEST_FIRST,

    /**
     * State where the player is asked to select the student to move from
     * the JESTER Character Card
     */
    REQ_JEST_SECOND__CARD,

    /**
     * State where the player is asked to select a DiningRoom in order to play the
     * effect of the CharacterCard JESTER
     */
    REQ_JEST_SECOND__ENTRANCE,

    /**
     * State where the player is asked to select the color to inhibit during the
     * calculus of the influences, during the current turn
     */
    REQ_MERCH_FIRST,

    /**
     * State where the player is asked to choose how many students to move
     * using the effect of the BARD Character Card
     */
    REQ_BARD_FIRST,

    /**
     * State where the player is asked to select the student of the entrance
     * in order to swap it with one of the DiningRoom
     */
    REQ_BARD_SECOND_STUDENT,

    /**
     * State where the player is asked to select a DiningRoom Color in order
     * to play the effect of BARD Character Card
     */
    REQ_BARD_SECOND_DINING,

    /**
     * State where the player is asked to select the student to move
     * from the Character Card PRINCESS
     */
    REQ_PRINCESS_FIRST,

    /**
     * State where the player is asked to select a color in order to reduce
     * the number of student from the correspondent DiningRooms using the
     * effect of the Character Card THIEF
     */
    REQ_THIEF_FIRST,
}
