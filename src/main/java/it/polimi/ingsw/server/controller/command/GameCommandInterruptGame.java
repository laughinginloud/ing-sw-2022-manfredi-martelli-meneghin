package it.polimi.ingsw.server.controller.command;

/**
 * Signal the players that the game must be interrupted because of a connection error
 * @author Mattia Martelli
 */
public class GameCommandInterruptGame implements GameCommand {
    public Object executeCommand() {
        return null;
    }
}
