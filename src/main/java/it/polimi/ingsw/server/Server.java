package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.GameController;

/**
 * Server's main class
 * @author Mattia Martelli
 */
public class Server {
    /**
     * Start the server with a specified port
     * @param port The port the server will listen to
     */
    public static void main(int port) {
        GameController.main(port);
    }

    /**
     * Start the server with the default port
     */
    public static void main() {
        GameController.main();
    }
}
