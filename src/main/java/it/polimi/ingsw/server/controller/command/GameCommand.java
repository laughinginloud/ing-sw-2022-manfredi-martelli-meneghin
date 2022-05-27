package it.polimi.ingsw.server.controller.command;

import java.io.Serializable;

/**
 * Interface representing a generic command used by the controller
 * @author Mattia Martelli
 */
public interface GameCommand extends Serializable {
    Object executeCommand();
}
