package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.*;
import it.polimi.ingsw.client.view.ViewCLI;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;
import org.jline.utils.Display;

import java.io.IOException;
import java.io.InputStream;

/**
 * Client's main class
 * @author Mattia Martelli
 */
public class Client {
    /**
     * Start the client
     * @param gui <code>true</code> if the client should start in GUI mode, <code>false</code> otherwise
     */
    public static void main(boolean gui) {
        try {
            main(gui ? new ViewGUI() : new ViewCLI());
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the client in CLI mode, passing the existing terminal instances
     * @param terminal The main terminal instance
     * @param display The associated display
     * @param keyStream The associated input stream
     * @param attributes The attributes saved from the switch to raw mode
     */
    public static void main(Terminal terminal, Display display, InputStream keyStream, Attributes attributes) {
        main(new ViewCLI(terminal, display, keyStream, attributes));
    }

    /**
     * True main of the client
     * @param view A pointer to the view
     */
    private static void main(View view) {
        view.launchUI();
    }
}
