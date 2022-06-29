package it.polimi.ingsw.server.virtualView;

import it.polimi.ingsw.common.message.Message;

/**
 * Exception thrown when a message different from "pong" is received
 * @author Mattia Martelli
 */
class NotPongException extends Exception {
    /**
     * The message received
     */
    private final Message message;

    /**
     * Create a not pong exception
     * @param message The message received
     */
    NotPongException(Message message) {
        this.message = message;
    }

    /**
     * Get the message received
     * @return The message received
     */
    Message getMsg() {
        return message;
    }
}
