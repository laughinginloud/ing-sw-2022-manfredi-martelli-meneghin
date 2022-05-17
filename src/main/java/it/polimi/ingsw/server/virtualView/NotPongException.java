package it.polimi.ingsw.server.virtualView;

import it.polimi.ingsw.server.controller.command.GameCommand;

/**
 * Exception thrown when a message different from "pong" is received
 * @author Mattia Martelli
 */
class NotPongException extends Exception {
    private final GameCommand command;

    NotPongException(GameCommand command) {
        this.command = command;
    }

    GameCommand getCommand() {
        return command;
    }
}
