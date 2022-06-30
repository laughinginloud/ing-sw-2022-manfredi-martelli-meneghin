package it.polimi.ingsw.common.message;

public enum MessageType {

    // region Request and response

    /**
     * Request a value
     */
    REQUESTVALUE,

    /**
     * Send an infomap
     */
    SENDINFO,

    /**
     * Request an action
     */
    REQUESTACTION,

    /**
     * Response to a RequestAction: ChosenCharacterCardFields, MoveMotherNature, PlayAssistantCard, PlayCharacterCard...
     */
    RESPONSEACTION,

    // endregion

    // region End game results

    /**
     * Signal a single winner
     */
    GAMEWINNER,

    /**
     * Signal the winning team of a 4 players game
     */
    GAMEWINNERTEAM,

    /**
     * Signal the draw of multiple players
     */
    GAMEDRAW,

    // endregion

    // region Ping messages

    /**
     * Ping message
     */
    PING,

    /**
     * Ping response message
     */
    PONG,

    // endregion

    // region Game flow messages

    /**
     * Signal that the game is starting
     */
    GAMESTART,

    /**
     * Signal that another game is already in progress
     */
    GAMEPROGRESS,

    /**
     * Signal the game interruption because a player disconnected
     */
    GAMEINTERRUPT,

    /**
     * Signal the player that his turn has now ended
     */
    CUR_PLAYER_END_TURN,

    /**
     * Signal the player that another's turn has now started
     */
    OTHER_PLAYER_START_TURN

    // endregion

}
