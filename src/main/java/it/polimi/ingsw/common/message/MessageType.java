package it.polimi.ingsw.common.message;

public enum MessageType {
    REQUESTVALUE,

    SENDINFO,

    REQUESTACTION,

    // Response to a RequestAction: ChosenCharacterCardFields, MoveMotherNature, PlayAssistantCard, PlayCharacterCard...
    RESPONSEACTION,

    // End game results
    GAMEWINNER,
    GAMEWINNERTEAM,
    GAMEDRAW,

    // Ping messages
    PING,
    PONG,

    // Messages to signal the start of a game and the fact that a game is already in progress
    GAMESTART,
    GAMEPROGRESS,

    // Message sent by the server to inform the client about a network error
    GAMEINTERRUPT,

    // Message sent by the server to inform the current player about the end of his turn
    CUR_PLAYER_END_TURN,

    // Message sent by the server to inform the not current players about the
    // beginning of the the turn of another player
    OTHER_PLAYER_START_TURN;
}
