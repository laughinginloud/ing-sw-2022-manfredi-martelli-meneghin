package it.polimi.ingsw.server.controller.state;

public class GameStateThread extends Thread {
    private GameState state;

    public GameStateThread() {
        state = new GameStateModelInitialization();
    }

    public GameStateThread(GameState state) {
        this.state = state;
    }

    public synchronized void run() {
        do {
            state.executeState();
            state = state.nextState();
        } while (state != null && !isInterrupted());
    }

    public synchronized GameState saveGameState() {
        interrupt();
        return state;
    }
}
