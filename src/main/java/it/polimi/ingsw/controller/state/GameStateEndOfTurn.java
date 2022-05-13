package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.characterCard.CharacterCardManager;
import it.polimi.ingsw.controller.command.*;
import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.model.CharacterCard;
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
            Map<GameCommandValues, Object> characterCardInfo = new HashMap<>();
            boolean expertMode = ControllerData.getInstance().getExpertMode();
            boolean canPlayCharacterCard = false;

            // If the player hasn't player a card yet and the game is in ExpertMode
            if (expertMode && !ControllerData.getInstance().checkPlayedCard()) {
                // Get all the playableCharacterCard, according to previous CharacterCards utilization and to current player's coin pool
                CharacterCard[] playableCharacterCard = CharacterCardManager.getPlayableCharacterCard(curPlayer);

                // If there are CharacterCard playable by the current player, add them to the moveStudentsInfo
                if (playableCharacterCard != null) {
                    characterCardInfo.put(GameCommandValues.CHARACTERCARDARRAY, playableCharacterCard);

                    //Set the flag to true to change the GameCommandRequestAction's type during its initialization
                    canPlayCharacterCard = true;

                }

                // If a CharacterCard can be played by the current player
                if (canPlayCharacterCard) {
                    GameCommand request = new GameCommandRequestAction(GameCommandActions.ENDTURN, characterCardInfo);
                    GameCommand response = curPlayerView.sendRequest(request);

                    if (response instanceof GameCommandPlayCharacterCard c) {
                        // If the player already used a CharacterCard during this turn, throws an exception
                        if (ControllerData.getInstance().checkPlayedCard())
                            throw new IllegalStateException("CharacterCard has been already used by the current player!");

                        // Executes the command received and set to "true" the flag hasPlayedCard stored in ControllerData
                        Character calledCharacter = (Character) c.executeCommand();
                        ControllerData.getInstance().setPlayedCard();

                        /* TODO: [CharacterCard] Insert here the real name of the function which manage the CharacterCardUse */
                        // CharacterCardUse.useCharacterCard(calledCharacter);
                    }
                }
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
