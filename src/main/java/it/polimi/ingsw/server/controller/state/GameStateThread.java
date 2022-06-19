package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.model.Player;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.save.GameSave;

import java.io.IOException;

import static it.polimi.ingsw.common.utils.Methods.ifNotNullOrElse;

public class GameStateThread extends Thread {
    private GameState state;

    public GameStateThread() {
        this(new GameStateModelInitialization());
    }

    public GameStateThread(GameState state) {
        this.state = state;
    }

    public synchronized void run() {
        do {
            state.executeState();
            state = state.nextState();

            ifNotNullOrElse(state,
                () -> save(state),
                GameStateThread::deleteSave);
        } while (state != null && !isInterrupted());

        GameController.signalEndGame();
    }

    public synchronized GameState saveGameState() {
        interrupt();
        return state;
    }

    private static void save(GameState state) {
        try {
            GameSave.saveGame(fileName(), state);
        }

        //Should never happen as permissions have already been checked
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteSave() {
        GameSave.deleteGame(fileName());
    }

    private static String fileName() {
        Player[]      players  = ControllerData.getInstance().getGameModel().getPlayer();
        StringBuilder fileName = new StringBuilder(players[0].getUsername());
        for (int i = 1; i < players.length; i++)
            fileName.append("-").append(players[i].getUsername());
        return fileName.toString();
    }
}
