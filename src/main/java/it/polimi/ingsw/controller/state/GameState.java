package it.polimi.ingsw.controller.state;

/**
 * Interface representing a state of the game's DFA
 * @author Mattia Martelli
 */
public interface GameState {
    GameState nextState();

    void sendNetworkMessage();

    void executeState();
}
