package it.polimi.ingsw.virtualView;

enum MessageType {
    // TODO: [ResponseMessage] Add response messages, twins of RequestValue and RequestAction, to decide whether to add them here or in the client code
    REQUESTVALUE, SENDINFO, ILLEGALMESSAGE, ILLEGALVALUE, REQUESTACTION,

    // Response to a RequestAction: ChosenCharacterCardFields, MoveMotherNature, PlayAssistantCard, PlayCharacterCard...
    // Still to implements in the MessageBuilder, if it will be needed
    RESPONSEACTION,

    // End game results
    GAMEWINNER, GAMEDRAW,

    // Ping messages
    PING, PONG,
}
