package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.*;
import it.polimi.ingsw.client.view.cli.ViewCLI;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import it.polimi.ingsw.client.virtualController.VirtualController;
import it.polimi.ingsw.common.model.GameModel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Client {
    private static GameModel model;

    /*
    public static void main(String[] args) {
        /*
        View view = args.length != 0 && args[0].equals("console") ? new ViewCLI() : new ViewGUI();
        view.initialize();

        boolean breakLoop = false;

        do {
            switch (view.menu()) {
                case Connect -> startGame(view);
                case Exit    -> breakLoop = true;
            }
        } while (!breakLoop);
    }
    */

    public static void setModel(GameModel model) {
        Client.model = model;
    }

    public static GameModel getModel() {
        return model;
    }

    /*
    private static void startGame(View view) {
        try (VirtualController controller = new VirtualController(view.getAddress(), view)) {
            controller.start();
            controller.join();
        }

        catch (IOException e) {
            view.signalConnectionError(); //FIXME: signalCannotConnect();
        }

        catch (Exception e) {

        }

        //View signal end
    }
    */
}
