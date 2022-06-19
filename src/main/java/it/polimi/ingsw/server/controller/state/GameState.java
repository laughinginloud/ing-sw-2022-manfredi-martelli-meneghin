package it.polimi.ingsw.server.controller.state;

/**
 * Interface representing a state of the game's DFA
 * @author Mattia Martelli
 */
public sealed interface GameState
    permits GameStateSetup,
            GameStatePlanPhase,
            GameStateActionPhase,
            GameStateEndCheckPhase,
            GameStateEndGame {
    /**
     * Returns the next state of the abstract DFA
     * @return A pointer to the next state
     */
    GameState nextState();

    /**
     * Execute the current state of the DFA
     */
    void executeState();
}
