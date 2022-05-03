package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.virtualView.VirtualView;
import it.polimi.ingsw.controller.command.*;

import java.io.IOException;

public class GameStateMoveMotherNature implements GameStateActionPhase {
    public GameState nextState() {
        return null;
    }

    public void executeState() {
        VirtualView player   = ControllerData.getInstance().getPlayerViewMap().getRight(ControllerData.getInstance().getCurrentPlayer());

        GameCommand request  = new GameCommandRequestValueClient(GameCommandValues.MOTHERNATURE);
        GameCommand response; //response instanceof GameCommandMoveMotherNature

        try {
            response = player.sendRequest(request);

            if (!(response instanceof GameCommandMoveMotherNature)) {
                try {
                    player.sendMessage(new GameCommandIllegalCommand(/*Messaggio d'errore?*/));
                }

                catch (IOException e) {
                    e.printStackTrace(); //TODO: sistemare
                }

                executeState();
            }

            else {
                try {
                    response.executeCommand();
                }

                catch (IllegalArgumentException e) {
                    try {
                        player.sendMessage(new GameCommandIllegalValue(/*Messaggio d'errore?*/));
                    }

                    catch (IOException ex) {
                        ex.printStackTrace(); //TODO: sistemare
                    }

                    executeState();
                }
            }
        }

        catch (IOException e) {
            e.printStackTrace(); //TODO: sistemare
        }
    }
}
