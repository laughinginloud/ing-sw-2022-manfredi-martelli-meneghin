package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.*;
import it.polimi.ingsw.client.view.cli.ViewCLI;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import it.polimi.ingsw.client.virtualController.VirtualController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Client {
    public static void main(String[] args) {
        View view = args.length != 0 && args[0].equals("console") ? new ViewCLI() : new ViewGUI();

        view.initialize();

        try (VirtualController controller = new VirtualController(view.getAddress())) {
            //TODO
        }

        catch (SocketException ignored) {
            view.signalConnectionError();
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
