package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.command.GameCommandValues;
import it.polimi.ingsw.virtualView.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;

public class GameController {
    //TODO

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6554); //TODO: gestire IOException
        while (true) {
            VirtualView view = new VirtualView(serverSocket.accept()); //TODO: gestire IOException
            view.start();
        }
    }

    public static Object requestValue(GameCommandValues value) {
        return switch (value) {
            case MOTHERNATURE -> ControllerData.getInstance().getGameModel().getMotherNaturePosition();
            default           -> throw new IllegalArgumentException();
        };
    }
}
