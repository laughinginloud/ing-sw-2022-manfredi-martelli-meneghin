package it.polimi.ingsw.common.message;

/**
 * Record representing the network messages
 * @param type The type of the message
 * @param value The payload
 * @author Mattia Martelli
 */
public record Message (MessageType type, Object value) {}
