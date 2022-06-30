package it.polimi.ingsw.server.controller.characterCard;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.PlayCharacterAction;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.net.SocketException;

/**
 * Strategy representing the activation of the CharacterCard 'JESTER'
 * @author Giovanni Manfredi
 */
public class JesterStrategy extends CharacterCardStrategy {

    /**
     * Constructor of the class 'JesterStrategy'
     * @param card the card to which the class is initialized
     */
    public JesterStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'JESTER'
     */
    @Override
    public void activateEffect() throws SocketException {
        try {
            ControllerData data       = ControllerData.getInstance();
            GameModel      model      = data.getGameModel();
            Player         curPlayer  = data.getCurrentPlayer();
            VirtualView    playerView = data.getPlayerView(curPlayer);

            // Sets the max number of students that can be moved using a single time this characterCard
            int maxNumOfMovements = 3;

            // Create a Map and save in it the maxNumOfMovement in order to send it to the player
            InfoMap movementMap = new InfoMap();
            movementMap.put(GameValues.CHARACTERVALUE, PlayCharacterAction.JESTERFIRST);
            movementMap.put(GameValues.MAXMOVEMENTJESTER, maxNumOfMovements);

            // The server asks the player how many students he would like to move using the Jester's cardEffect
            GameCommand request  = new GameCommandRequestAction(GameActions.CHARACTERCARDEFFECT, movementMap);
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right kind
            if (response instanceof GameCommandChosenCharacterCardFields c) {
                // The player responds with the information requested by the server
                InfoMap chosenField = (InfoMap) c.executeCommand();

                // Gets the number of students that the player wants to move, from the Map received from the client
                // TODO: [ClientImplementation] Int movementJester chosen from the client has to be between 1 and maxNumOfMovements
                int chosenNumOfMovement = (int) chosenField.get(GameValues.MOVEMENTJESTER);

                // For chosenNumOfMovement times asks the player which student he would like to move, waits for response and notifies all the players after the movement
                for (int i = 0; i < chosenNumOfMovement; i++) {
                    boolean lastMovement = i == chosenNumOfMovement - 1;
                    changeStudent(data, model, curPlayer, playerView, lastMovement);
                }
            }

            // If the response is of the wrong kind throw an exception to help debug
            else
                throw new IllegalStateException("Wrong command received: " + response);
        }

        catch (SocketException e) {
            throw e;
        }

        catch (Exception e){
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    public void changeStudent(ControllerData data, GameModel model, Player curPlayer, VirtualView playerView, boolean lastMovement) throws Exception {
        Player[] players = data.getGameModel().getPlayers();

        // Cast the CharacterCard to a CharacterCardStudent
        CharacterCardStudent enhancedCard = (CharacterCardStudent) card;

        // Gets the students present in the current player's Entrance and on the characterCard Jester
        Color[] entranceStudents      = curPlayer.getSchoolBoard().getEntrance().getStudents();
        Color[] characterCardStudents = enhancedCard.getStudents();

        // Gets the position of the characterCard in the model's characterCardArray
        int position = CharacterCardManager.getCharacterCardPosition(Character.JESTER);

        // Create a Map and save the field that will be sent to the player as RequestAction's payload
        InfoMap jesterMap = new InfoMap();
        jesterMap.put(GameValues.CHARACTERVALUE,        PlayCharacterAction.JESTERSECOND);
        jesterMap.put(GameValues.ENTRANCESTUDENTS,      entranceStudents);
        jesterMap.put(GameValues.CARDSTUDENTS,          characterCardStudents);
        jesterMap.put(GameValues.CHARACTERCARDPOSITION, position);

        // The server asks the player which students would like to move (one from the Card, one from the Entrance)
        GameCommand movementRequest  = new GameCommandRequestAction(GameActions.CHARACTERCARDEFFECT, jesterMap);
        GameCommand movementResponse = playerView.sendRequest(movementRequest);

        // If the response is of the right kind
        if (movementResponse instanceof GameCommandChosenCharacterCardFields c) {
            // The player responds with the information requested by the server
            InfoMap chosenField = (InfoMap) c.executeCommand();

            // Gets the indexes of the entranceStudent and the cardStudent the player wants swap
            int entranceStudentIndex = (int) chosenField.get(GameValues.ENTRANCESTUDENTINDEX);
            int cardStudentIndex     = (int) chosenField.get(GameValues.CARDSTUDENTINDEX);

            // The server exchanges the students (using a temporary object) from the Entrance and the card
            Color tmpEntranceStudent = curPlayer.getSchoolBoard().getEntrance().retrieveStudent(entranceStudentIndex);
            Color tmpCardStudent = ((CharacterCardStudent) card).retrieveStudent(cardStudentIndex);

            curPlayer.getSchoolBoard().getEntrance().appendStudent(tmpCardStudent);
            ((CharacterCardStudent) card).appendStudent(tmpEntranceStudent);

            // After the server managed the use of the CharacterCard, gets the updated values of CharacterCardsArray and players' Entrances
            CharacterCard[] updatedCharacterCards = model.getCharacterCards();
            Entrance[]      updatedEntrances      = new Entrance[players.length];

            // Gets the entrance of each player and saves it an updatedEntrances
            for (int i = 0; i < players.length; i++)
                updatedEntrances[i] = players[i].getSchoolBoard().getEntrance();

            // If it's the last iteration of "changeStudent" saves these data in the Map afterEffectUpdate
            if (lastMovement) {
                afterEffectUpdate.put(GameValues.CHARACTERCARDARRAY, updatedCharacterCards);
                afterEffectUpdate.put(GameValues.ENTRANCEARRAY, updatedEntrances);
            }

            else
                updateDuringIteration(model, updatedCharacterCards, updatedEntrances);
        }

        // If the response is of the wrong kind throw an exception to help debug
        else
            throw new IllegalStateException("Wrong command received: " + movementResponse);
    }

    /**
     * Update the players about the students' movements caused by the effect of the
     * CharacterCard 'JESTER' on the CharacterCards and on the Entrances
     * @param model The updated GameModel
     * @param updatedCharacterCards The updated CharacterCardArray
     * @param updatedEntrances The updated EntranceArray
     */
    private void updateDuringIteration(GameModel model, CharacterCard[] updatedCharacterCards, Entrance[] updatedEntrances) {
        InfoMap duringInteractionMap = new InfoMap();

        duringInteractionMap.put(GameValues.CHARACTERCARDARRAY, updatedCharacterCards);
        duringInteractionMap.put(GameValues.ENTRANCEARRAY, updatedEntrances);

        GameCommand sendInfo = new GameCommandSendInfo(duringInteractionMap);

        Player[] players = model.getPlayers();
        for (Player playersToUpdate : players) {
            VirtualView playerToUpdateView = ControllerData.getInstance().getPlayerView(playersToUpdate);
            playerToUpdateView.sendMessage(sendInfo);
        }
    }
}

