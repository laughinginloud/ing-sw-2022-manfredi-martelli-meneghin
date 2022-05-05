package it.polimi.ingsw.controller.state;

public class GameStateThread extends Thread {
    public synchronized void start() {
        GameState state = new GameStateModelInitialization();
        while (true) {
            state.executeState();
            state.nextState();
        }
    }
}
