package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardManager;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardStrategy;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.common.model.CharacterCard;
import it.polimi.ingsw.common.model.Player;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.HashMap;
import java.util.Map;

/**
 * State representing the end of a player ActionPhase's turn
 * @author Sebastiano Meneghin
 */
public final class GameStateEndOfTurn implements GameStateActionPhase {
    public GameState nextState() {
        return
            // Check if the game has been won already
            ControllerData.getInstance().checkWinTrigger() ?
                // If the game has ended, return a plain end phase, which will decide who won
                new GameStateEndGame() :
                // Otherwise, go to the next phase
                anotherPlayerTurn() ?
                    new GameStateMoveStudents() :
                    new GameStateEndCheckPhase();
    }

    public void executeState() {
        try {
            ControllerData data          = ControllerData.getInstance();
            Player         curPlayer     = data.getCurrentPlayer();
            VirtualView    curPlayerView = data.getPlayerView(curPlayer);

            // Gets the expertMode and set useful flag to "false"
            boolean expertMode = data.getExpertMode();
            boolean canPlayCharacterCard = false;

            // Creates a map where to save the fields that will be sent to the current player
            Map<GameValues, Object> characterCardInfo = new HashMap<>();

            // If the player hasn't player a card yet and the game is in ExpertMode
            if (expertMode && !data.checkPlayedCard()) {
                // Get all the playableCharacterCard, according to previous CharacterCards utilization and to current player's coin pool
                CharacterCard[] playableCharacterCard = CharacterCardManager.getPlayableCharacterCard(curPlayer);

                // If there are CharacterCard playable by the current player, add them to the moveStudentsInfo
                if (playableCharacterCard != null) {
                    characterCardInfo.put(GameValues.CHARACTERCARDARRAY, playableCharacterCard);

                    //Set the flag to true to change the GameCommandRequestAction's type during its initialization
                    canPlayCharacterCard = true;

                }

                // If a CharacterCard can be played by the current player
                if (canPlayCharacterCard) {
                    GameCommand request = new GameCommandRequestAction(GameActions.ENDTURN, characterCardInfo);
                    GameCommand response = curPlayerView.sendRequest(request);

                    // If the player decide to play a characterCard
                    if (response instanceof GameCommandPlayCharacterCard c) {
                        // If the player already used a CharacterCard during this turn, throws an exception
                        if (data.checkPlayedCard())
                            throw new IllegalStateException("CharacterCard has been already used by the current player!");

                        // Executes the command received
                        Character chosenCharacter = (Character) c.executeCommand();

                        // Gets the characterCardStrategy linked to the CharacterCard chosen by the player
                        CharacterCardStrategy chosenCardStrategy = CharacterCardManager.getChosenCharacterCardStrategy(chosenCharacter);

                        // Calls the selected characterCard's strategy effect
                        chosenCardStrategy.playCharacterCard();

                        // If the game hasn't ended reset then all the characterCard flags that has been enabled
                        // during this turn and end the player's turn
                        if (!data.checkWinTrigger())
                            resetCharacterCardFlags();
                    }

                    // If the player decide to end his turn
                    else if (response instanceof GameCommandEndTurn) {
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
        Player[] orderedPlayer = ControllerData.getInstance().getGameModel().getPlayer();
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
