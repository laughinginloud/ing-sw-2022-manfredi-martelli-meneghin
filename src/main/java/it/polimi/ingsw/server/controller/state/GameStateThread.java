package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.model.Player;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.save.GameSave;

import java.io.IOException;

import static it.polimi.ingsw.common.utils.Methods.ifNotNullOrElse;

public final class GameStateThread extends Thread {
    private static String fileName;

    private GameState state;

    public GameStateThread(GameState state) {
        this.state = state;
    }

    public synchronized void run() {
        fileName = mkFileName();

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
            GameSave.saveGame(fileName, state);
        }

        //Should never happen as permissions have already been checked
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteSave() {
        GameSave.deleteGame(fileName);
    }

    private static String mkFileName() {
        Player[] players = ControllerData.getInstance().getPlayersOrder();
        StringBuilder sb = new StringBuilder(players[0].getUsername());

        for (int i = 1; i < players.length; i++)
            sb.append("-").append(players[i].getUsername());

        return sb.toString();
    }
}
