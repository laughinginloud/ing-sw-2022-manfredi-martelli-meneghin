package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardManager;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardStrategy;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.common.model.CharacterCard;
import it.polimi.ingsw.common.model.Player;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.net.SocketException;

/**
 * State representing the end of a player ActionPhase's turn
 * @author Sebastiano Meneghin
 */
public final class GameStateEndOfTurn implements GameStateActionPhase {
    private boolean turnEnd;

    public GameState nextState() {
        return
            // Check if the game has been won already
            ControllerData.getInstance().checkWinTrigger() ?
                // If the game has ended, return a plain end phase, which will decide who won
                new GameStateEndGame() :
                // Otherwise, go to the next phase
                turnEnd ?
                    new GameStateEndCheckPhase():
                    new GameStateMoveStudents();
    }

    public void executeState() throws SocketException {
        try {
            ControllerData data          = ControllerData.getInstance();
            Player         curPlayer     = data.getCurrentPlayer();
            VirtualView    curPlayerView = data.getPlayerView(curPlayer);

            turnEnd = false;

            // Gets the expertMode
            boolean expertMode = data.getExpertMode();

            // Creates a map where to save the fields that will be sent to the current player
            InfoMap characterCardInfo = new InfoMap();

            // If the player hasn't player a card yet and the game is in ExpertMode
            if (expertMode && !data.checkPlayedCard()) {
                // Get all the playableCharacterCard, according to previous CharacterCards utilization and to current player's coin pool
                CharacterCard[] playableCharacterCard = CharacterCardManager.getPlayableCharacterCard(curPlayer);

                // If there are CharacterCard playable by the current player, add them to the moveStudentsInfo
                if (playableCharacterCard != null) {
                    characterCardInfo.put(GameValues.CHARACTERCARDARRAY, playableCharacterCard);

                    GameCommand request  = new GameCommandRequestAction(GameActions.ENDTURN, characterCardInfo);
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
                    else if (response instanceof GameCommandEndTurn)
                        // Reset then all the characterCard flags that has been enabled during this turn and end the player's turn
                        resetCharacterCardFlags();

                    // If the response is of the wrong kind throw an exception to help debug
                    else
                        throw new IllegalStateException("Wrong command received: " + response);
                }
            }

            // If the player can't play a CharacterCard, notify him about the end of his actionPhase turn and
            // resets the CharacterCardFlags
            else {
                resetCharacterCardFlags();
                curPlayerView.sendMessage(new GameCommandNotifyEndTurn());
            }

            if (data.turnEnd())
                turnEnd = true;

            else
                data.nextPlayer();
        }

        catch (SocketException e) {
            throw e;
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    /**
     * #ExpertModeOnly - Resets all the characterCard flags to false, resets the ExcludedColor
     */
    private void resetCharacterCardFlags() {
        ControllerData data = ControllerData.getInstance();

        if (!data.getExpertMode())
            return;

        for (ControllerData.Flags flag : ControllerData.Flags.values())
            data.setCharacterCardFlag(flag, false);

        data.setExcludedColor(null);
    }
}
