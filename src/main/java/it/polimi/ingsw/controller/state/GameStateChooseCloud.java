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
 * State representing the retrieval of students from an island
 * @author Mattia Martelli
 */
public class GameStateChooseCloud implements GameStateActionPhase {
    public GameState nextState() { return new GameStateEndOfTurn(); }

    public void executeState() {
        try {
            Player player          = ControllerData.getInstance().getCurrentPlayer();
            VirtualView playerView = ControllerData.getInstance().getPlayerView(player);

            GameCommand request    = new GameCommandRequestValueClient(GameCommandValues.CLOUD);
            GameCommand response   = playerView.sendRequest(request);

            // If the response is of the right type, try to execute
            if (response instanceof GameCommandChooseCloud c) {
                try {
                    c.checkLegalValue();
                    Color[] students = (Color[]) c.executeCommand();
                    for (Color student : students)
                        player.getSchoolBoard().getEntrance().appendStudent(student);

                    Map<GameCommandValues, Object> map = new HashMap<>();
                    map.put(GameCommandValues.ENTRANCE, player.getSchoolBoard().getEntrance());

                    for (Player playerToUpdate : ControllerData.getInstance().getPlayersOrder())
                        ControllerData.getInstance().getPlayerView(playerToUpdate).sendMessage(new GameCommandSendInfo(map));
                }

                catch (IllegalArgumentException e) {
                    try {
                        playerView.sendMessage(new GameCommandIllegalValue());
                    }

                    catch (Exception ex) {
                        // Fatal error: print the stack trace to help debug
                        ex.printStackTrace();
                    }

                    executeState();
                }
            }

            // If the player decided to play a CharacterCard
            else if (response instanceof GameCommandPlayCharacterCard c) {
                // If the player already used a CharacterCard during this turn, throws an exception
                if(ControllerData.getInstance().checkPlayedCard())
                    throw new IllegalStateException("CharacterCard has been already used by the current player!");

                // Executes the command received and set to "true" the flag hasPlayedCard stored in ControllerData
                Character calledCharacter = (Character) c.executeCommand();
                ControllerData.getInstance().setPlayedCard();

                /* TODO: [CharacterCard] Insert here the real name of the function which manage the CharacterCardUse */
                // CharacterCardUse.useCharacterCard(calledCharacter);

                // After the CharacterCard utilization, it recalls execute once again the procedure of GameStateChooseCloud
                executeState();
            }

            // If the response is of the wrong kind, send an Illegal Command message and try this state again
            else {
                try {
                    playerView.sendMessage(new GameCommandIllegalCommand());
                }

                catch (Exception e) {
                    // Fatal error: print the stack trace to help debug
                    e.printStackTrace();
                }

                executeState();
            }
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }
}
