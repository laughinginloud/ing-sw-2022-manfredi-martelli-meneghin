package it.polimi.ingsw.common.message;

public enum MessageType {
    // TODO: [ResponseMessage] Add response messages, twins of RequestValue and RequestAction, to decide whether to add them here or in the client code
    REQUESTVALUE, SENDINFO, ILLEGALMESSAGE, ILLEGALVALUE, REQUESTACTION,

    // Response to a RequestAction: ChosenCharacterCardFields, MoveMotherNature, PlayAssistantCard, PlayCharacterCard...
    // Still to implements in the MessageBuilder, if it will be needed
    RESPONSEACTION,

    // End game results
    GAMEWINNER, GAMEDRAW,

    // Ping messages
    PING, PONG,

    // Messages to signal the start of a game, the fact that a game is already in progress and the interruption because of a network error
    GAMESTART, GAMEPROGRESS, GAMEINTERRUPT,
}
