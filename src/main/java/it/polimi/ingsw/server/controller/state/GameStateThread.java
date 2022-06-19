package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.save.GameSave;

import java.io.IOException;

import static it.polimi.ingsw.common.utils.Methods.ifNotNullOrElse;

public final class GameStateThread extends Thread {
    private final String fileName;

    private GameState state;

    public GameStateThread(GameState state, String fileName) {
        this.state    = state;
        this.fileName = fileName;
    }

    public synchronized void run() {
        do {
            state.executeState();
            state = state.nextState();
            autosave(state);
        } while (state != null && !isInterrupted());

        GameController.signalEndGame();
    }

    public synchronized GameState saveGameState() {
        interrupt();
        return state;
    }

    private void autosave(GameState state) {
        ifNotNullOrElse(state,
            this::save,
            this::deleteSave);
    }

    private void save(GameState state) {
        if (isInterrupted())
            return;

        if ((state instanceof GameStateSetup) ||
            (state instanceof GameStateEndGame))
            return;

        try {
            GameSave.saveGame(fileName, state);
        }

        //Should never happen as permissions have already been checked
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteSave() {
        GameSave.deleteGame(fileName);
    }
}
