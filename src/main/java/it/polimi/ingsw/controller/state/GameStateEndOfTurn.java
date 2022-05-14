package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.characterCard.CharacterCardManager;
import it.polimi.ingsw.controller.characterCard.CharacterCardStrategy;
import it.polimi.ingsw.controller.command.*;
import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.model.CharacterCard;
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

                    // If the player decide to play a characterCard
                    if (response instanceof GameCommandPlayCharacterCard c) {
                        // If the player already used a CharacterCard during this turn, throws an exception
                        if (ControllerData.getInstance().checkPlayedCard())
                            throw new IllegalStateException("CharacterCard has been already used by the current player!");

                        // Executes the command received
                        Character chosenCharacter = (Character) c.executeCommand();

                        // Gets the characterCardStrategy linked to the CharacterCard chosen by the player
                        CharacterCardStrategy chosenCardStrategy = CharacterCardManager.getChosenCharacterCardStrategy(chosenCharacter);

                        // Calls the selected characterCard's strategy effect
                        chosenCardStrategy.playCharacterCard();

                        // Reset then all the characterCard flags that has been enabled during this turn and end the player's turn
                        resetCharacterCardFlags();
                    }

                    // If the player decide to end his turn
                    else if (response instanceof GameCommandEndTurn c) {
                        // Reset then all the characterCard flags that has been enabled during this turn and end the player's turn
                        resetCharacterCardFlags();
                    }

                    // If the response is of the wrong kind, send an Illegal Command message and try this state again
                    else {
                        try {
                            curPlayerView.sendMessage(new GameCommandIllegalCommand());
                        }

                        catch (Exception e) {
                            // Fatal error: print the stack trace to help debug
                            e.printStackTrace();
                        }

                        executeState();
                    }
                }
            }
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    /**
     * Check if there's another player that has to play his turn during this Round
     * @return A boolean representing the presence of another player that has to play his turn
     */
    private boolean anotherPlayerTurn() {
        Player[] orderedPlayer = ControllerData.getInstance().getPlayersOrder();
        Player currentPlayer   = ControllerData.getInstance().getCurrentPlayer();

        // If the currentPlayer it's not the last player of the round, then anotherPlayerTurn() -> True
        return !currentPlayer.equals(orderedPlayer[ControllerData.getInstance().getNumOfPlayers() - 1]);
    }

    /**
     * #ExpertModeOnly - Resets all the characterCard flags to false, resets the ExcludedColor
     */
    private void resetCharacterCardFlags() {
        ControllerData data = ControllerData.getInstance();
        for (ControllerData.Flags flag : ControllerData.Flags.values())
            data.setCharacterCardFlag(flag, false);

        ControllerData.getInstance().setExcludedColor(null);
    }


}
