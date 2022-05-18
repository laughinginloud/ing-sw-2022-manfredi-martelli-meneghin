package it.polimi.ingsw.server.controller.state;

public class GameStateThread extends Thread {
    public synchronized void run() {
        GameState state = new GameStateModelInitialization();

        do {
            state.executeState();
            state = state.nextState();
        } while (state != null);
    }
}