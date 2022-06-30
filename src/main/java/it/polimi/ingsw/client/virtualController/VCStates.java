package it.polimi.ingsw.client.virtualController;

/**
 * The current state of the virtual controller
 * @author Mattia Martelli
 */
public enum VCStates {
    REQ_USER_AGE,
    REQ_RULES,
    REQ_WIZARD,
    REQ_LOAD,
    REQ_PLAY_ASS,
    REQ_MOVE_STUD_FIRST,
    REQ_MOVE_STUD_SECOND,
    REQ_MOVE_MN,
    REQ_CHOOSE_CLOUD,
    REQ_MOVE_STUD_OR_PLAY,
    REQ_MOVE_MN_OR_PLAY,
    REQ_CHOOSE_CLOUD_OR_PLAY,
    REQ_END_TURN_ASK,
    REQ_END_TURN_PLAY,

    // The following VCStates are related to the CharacterCardEffect
    REQ_MONK_FIRST__STUDENT,
    REQ_MONK_FIRST__ISLAND,
    REQ_BEARER_FIRST_OR_HERB_FIRST,
    REQ_JEST_FIRST,
    REQ_JEST_SECOND__CARD,
    REQ_JEST_SECOND__ENTRANCE,
    REQ_MERCH_FIRST,
    REQ_BARD_FIRST,
    REQ_BARD_SECOND_STUDENT,
    REQ_BARD_SECOND_DINING,
    REQ_PRINCESS_FIRST,
    REQ_THIEF_FIRST,
}
