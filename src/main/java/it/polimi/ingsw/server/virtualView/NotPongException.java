package it.polimi.ingsw.server.virtualView;

import it.polimi.ingsw.common.message.Message;

/**
 * Exception thrown when a message different from "pong" is received
 * @author Mattia Martelli
 */
class NotPongException extends Exception {
    private final Message message;

    NotPongException(Message message) {
        this.message = message;
    }

    Message getMsg() {
        return message;
    }
}
