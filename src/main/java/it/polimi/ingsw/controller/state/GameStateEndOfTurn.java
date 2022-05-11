package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.*;
import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.virtualView.VirtualView;

import java.util.HashMap;
import java.util.Map;

/**
 * State representing the end of a player ActionPhase's turn
 * @author Sebastiano Meneghin
 */
public class GameStateEndOfTurn implements GameStateActionPhase {
    public GameState nextState() {
        return anotherPlayerTurn() ? new GameStateMoveStudents() : new GameStateEndCheckPhase();
    }

    public void executeState() {
        try {
            Player curPlayer = ControllerData.getInstance().getCurrentPlayer();
            VirtualView curPlayerView = ControllerData.getInstance().getPlayerView(curPlayer);

            GameCommand request = new GameCommandRequestAction(GameCommandActions.ENDTURN, null);
            GameCommand response = curPlayerView.sendRequest(request);

            if (response instanceof GameCommandPlayCharacterCard c) {
                // If the player already used a CharacterCard during this turn, throws an exception
                if(ControllerData.getInstance().checkPlayedCard())
                    throw new IllegalStateException("CharacterCard has been already used by the current player!");

                // Executes the command received and set to "true" the flag hasPlayedCard stored in ControllerData
                Character calledCharacter = (Character) c.executeCommand();
                ControllerData.getInstance().setPlayedCard();

                /* TODO: [CharacterCard] Insert here the real name of the function which manage the CharacterCardUse */
                // CharacterCardUse.useCharacterCard(calledCharacter);
            }
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    private boolean anotherPlayerTurn() {
        Player[] orderedPlayer = ControllerData.getInstance().getPlayersOrder();
        Player currentPlayer   = ControllerData.getInstance().getCurrentPlayer();

        // If the currentPlayer it's not the last player of the round, then anotherPlayerTurn() -> True
        return !currentPlayer.equals(orderedPlayer[ControllerData.getInstance().getNumOfPlayers() - 1]);
    }
}
