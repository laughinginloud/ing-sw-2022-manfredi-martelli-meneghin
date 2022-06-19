package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.save.GameSave;

import java.io.IOException;
import java.net.SocketException;

import static it.polimi.ingsw.common.utils.Methods.ifNotNullOrElse;

/**
 * Main DFA thread
 * @author Mattia Martelli
 */
public final class GameStateThread extends Thread {
    private final String    fileName;
    private       GameState state;

    private       boolean   stopped;

    public GameStateThread(GameState state, String fileName) {
        this.state    = state;
        this.fileName = fileName;
             stopped  = false;
    }

    public void run() {
        try {
            do {
                state.executeState();
                state = state.nextState();
                autosave(state);
            } while (state != null && !stopped);

            GameController.signalEndGame();
        }

        // If a socket exception is thrown, it means that a connection has been interrupted
        // In that case, just end gracefully
        catch (SocketException ignored) {}
    }

    /**
     * Stop the DFA
     */
    public void end() {
        stopped = true;
    }

    /**
     * Autosave the game after every state
     * @param state The state to possibly save
     */
    private synchronized void autosave(GameState state) {
        ifNotNullOrElse(state,
            this::save,
            this::deleteSave);
    }

    /**
     * Save the current state of the model and DFA
     * @param state The state to save
     */
    private void save(GameState state) {
        if (stopped)
            return;

        // Avoid saving setup or endgame states, as it would make little sense to load these
        if (state instanceof GameStateSetup ||
            state instanceof GameStateEndGame)
            return;

        // If the game has been terminated on a completely automatic state,
        // simply execute it, go to the next and try to save that
        if (state instanceof GameStateComputeIsland ||
            state instanceof GameStateEndCheckPhase ||
            state instanceof GameStateFillClouds    ||
            state instanceof GameStateSelectTurnOrder) {
            try {
                state.executeState();
                save(state.nextState());
            }

            // NB: this catch is here to please the type system, since the possible states
            // do not throw it, but GameState.executeState()'s signature has it because
            // most states do, since they send and receive requests
            catch (SocketException ignored) {}
        }

        try {
            GameSave.saveGame(fileName, state);
        }

        //Should never happen as permissions have already been checked
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * If the DFA has ended, delete the current save
     */
    private void deleteSave() {
        GameSave.deleteGame(fileName);
    }
}
