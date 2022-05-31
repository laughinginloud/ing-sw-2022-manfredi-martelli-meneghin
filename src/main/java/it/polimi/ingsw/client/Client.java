package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.*;
import it.polimi.ingsw.client.view.cli.ViewCLI;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import it.polimi.ingsw.client.virtualController.VirtualController;
import it.polimi.ingsw.common.model.GameModel;
import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;
import org.jline.utils.Display;

import java.io.IOException;
import java.io.InputStream;

/**
 * Main client class
 * @author Mattia Martelli
 */
public class Client {

    //region Fields

    // Private instance, used to wait for the signals
    private static Client instance;


    //Address and play are used for the respective menus
    private Address   address;
    private boolean   play;

    // The local copy of the model for the client
    private GameModel model;

    // Pointer to the view
    private View      view;

    // endregion

    // region Main

    /**
     * Start the client
     * @param gui <code>true</code> if the client should start in GUI mode, <code>false</code> otherwise
     */
    public static void main(boolean gui) {
        instance = new Client();
        instance.main(gui ? new ViewGUI() : new ViewCLI());
    }

    /**
     * Start the client in CLI mode, passing the existing terminal instances
     * @param terminal The main terminal instance
     * @param display The associated display
     * @param keyStream The associated input stream
     * @param attributes The attributes saved from the switch to raw mode
     */
    public static void main(Terminal terminal, Display display, InputStream keyStream, Attributes attributes) {
        instance = new Client();
        instance.main(new ViewCLI(terminal, display, keyStream, attributes));
    }

    /**
     * True main of the client
     * @param view A pointer to the view
     */
    private void main(View view) {
        this.view = view;
        view.launchUI();

        do {
            if (requestPlayExit())
                startGame();

            else
                break;
        } while (true);

        instance = null;
    }

    /**
     * Start the game and wait for it to end
     */
    private void startGame() {
        try (VirtualController controller = new VirtualController(requestAddress(), view)) {
            controller.start();
            controller.join();
        }

        catch (Exception e) {
            view.signalConnectionError();
        }
    }

    // endregion

    // region Requests and signals

    /**
     * Request whether the player wants to play or exit the app
     * @return <code>true</code> if he wants to play, <code>false</code> if he wants to exit
     */
    private boolean requestPlayExit() {
        view.playExitMenu();

        try {
            wait();
        }

        // Should never happen
        catch (Exception e) {
            e.printStackTrace();
        }

        return play;
    }

    /**
     * Signal to the main client class if the player wants to play or exit the app
     * @param playExit <code>true</code> if he wants to play, <code>false</code> if he wants to exit
     */
    public static void signalPlayExit(boolean playExit) {
        instance.play = playExit;
        instance.notify();
    }

    /**
     * Request the address of the server to the player
     * @return A tuple containing IP and port number
     */
    private Address requestAddress() {
        view.askAddress();

        try {
            wait();
        }

        // Should never happen
        catch (Exception e) {
            e.printStackTrace();
        }

        return address;
    }


    /**
     * Signal to the main client class the address of the server
     * @param address A tuple containing IP and port number
     */
    public static void signalAddress(Address address) {
        instance.address = address;
        instance.notify();
    }

    // endregion

    // region Miscellaneous

    // The constructor is private since the only instance in the entire
    // program should be the private field, used mainly to wait for the signals
    private Client() {}

    /**
     * Set the model currently stored in the client
     * @param model A pointer to the model
     */
    public static void setModel(GameModel model) {
        instance.model = model;
    }

    /**
     * Get the model currently stored in the client
     * @return A pointer to the model
     */
    public static GameModel getModel() {
        return instance.model;
    }

    // endregion
}
