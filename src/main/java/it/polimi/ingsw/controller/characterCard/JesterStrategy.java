package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualView.VirtualView;

import java.util.HashMap;
import java.util.Map;

/**
 * Strategy representing the activation of the CharacterCard 'JESTER'
 * @author Giovanni Manfredi
 */
public class JesterStrategy extends CharacterCardStrategy {
    public JesterStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'JESTER'
     */
    @Override
    public void activateEffect() {
        try {
            ControllerData data       = ControllerData.getInstance();
            GameModel      model      = data.getGameModel();
            Player         curPlayer  = data.getCurrentPlayer();
            VirtualView    playerView = data.getPlayerView(curPlayer);

            // Sets the max number of students that can be moved using a single time this characterCard
            int maxNumOfMovements = 3;

            // Create a Map and save in it the maxNumOfMovement in order to send it to the player
            Map<GameCommandValues, Object> movementMap = new HashMap<>();
            movementMap.put(GameCommandValues.MAXMOVEMENTJESTER, maxNumOfMovements);

            // The server asks the player how many students he would like to move using the Jester's cardEffect
            GameCommand request  = new GameCommandRequestAction(GameCommandActions.CHARACTERCARDEFFECT, movementMap);
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right kind
            if (response instanceof GameCommandChosenCharacterCardFields c) {
                // The player responds with the information requested by the server
                @SuppressWarnings("unchecked")
                Map<GameCommandValues, Object> chosenField = (Map<GameCommandValues, Object>) c.executeCommand();

                // Gets the number of students that the player wants to move, from the Map received from the client
                // TODO: [ClientImplementation] Int movementJester chosen from the client has to be between 1 and maxNumOfMovements
                int chosenNumOfMovement = (int) chosenField.get(GameCommandValues.MOVEMENTJESTER);

                // For chosenNumOfMovement times asks the player which student he would like to move, waits for response and notifies all the players after the movement
                for (int i = 0; i < chosenNumOfMovement; i++)
                    changeStudent(data, model, curPlayer, playerView);

            }

            // If the response is of the wrong kind, send an Illegal Command message and restart the method
            else {
                try {
                    playerView.sendMessage(new GameCommandIllegalCommand());
                }

                catch (Exception ex) {
                    // Fatal error: print the stack trace to help debug
                    ex.printStackTrace();
                }

                activateEffect();
            }
        }

        catch (Exception e){
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    public void changeStudent(ControllerData data, GameModel model, Player curPlayer, VirtualView playerView) throws Exception {
        Player[] players = data.getPlayersOrder();

        // Cast the CharacterCard to a CharacterCardStudent
        CharacterCardStudent enhancedCard = (CharacterCardStudent) card;

        // Gets the students present in the current player's Entrance and on the characterCard Jester
        Color[] entranceStudents      = curPlayer.getSchoolBoard().getEntrance().getStudents();
        Color[] characterCardStudents = enhancedCard.getStudents();

        // Create a Map and save the field that will be sent to the player as RequestAction's payload
        Map<GameCommandValues, Object> jesterMap = new HashMap<>();
        jesterMap.put(GameCommandValues.ENTRANCESTUDENTS, entranceStudents);
        jesterMap.put(GameCommandValues.CARDSTUDENTS, characterCardStudents);

        // The server asks the player which students would like to move (one from the Card, one from the Entrance)
        GameCommand movementRequest  = new GameCommandRequestAction(GameCommandActions.CHARACTERCARDEFFECT, jesterMap);
        GameCommand movementResponse = playerView.sendRequest(movementRequest);

        // If the response is of the right kind
        if (movementResponse instanceof GameCommandChosenCharacterCardFields c) {
            // The player responds with the information requested by the server
            @SuppressWarnings("unchecked")
            Map<GameCommandValues, Object> chosenField = (Map<GameCommandValues, Object>) c.executeCommand();

            // Gets the indexes of the entranceStudent and the cardStudent the player wants swap
            int entranceStudentIndex = (int) chosenField.get(GameCommandValues.ENTRANCESTUDENTINDEX);
            int cardStudentIndex     = (int) chosenField.get(GameCommandValues.CARDSTUDENTINDEX);



            // TODO [CharacterCardStrategy] implementation
            // The server exchanges the students (using a temporary array) from the Entrance and the card



            // After the server managed the use of the CharacterCard, gets the updated values of CharacterCardsArray and players' Entrances
            CharacterCard[] updatedCharacterCards = model.getCharacterCards();
            Entrance[]      updatedEntrances      = new Entrance[players.length];

            // Gets the entrance of each player and saves it an updatedEntrances
            for (int i = 0; i < players.length; i++)
                updatedEntrances[i] = players[i].getSchoolBoard().getEntrance();

            // Creates a map containing the updated field to send to all the players
            Map<GameCommandValues, Object> afterEffectUpdate = new HashMap<>();
            afterEffectUpdate.put(GameCommandValues.CHARACTERCARDARRAY, updatedCharacterCards);
            afterEffectUpdate.put(GameCommandValues.ENTRANCEARRAY, updatedEntrances);

            // Sends to all the players the updated fields
            for (Player playersToUpdate : players) {
                VirtualView playerToUpdateView = data.getPlayerView(playersToUpdate);
                playerToUpdateView.sendMessage(new GameCommandSendInfo(afterEffectUpdate));
            }
        }

        // If the response is of the wrong kind, send an Illegal Command message and restart the method
        else {
            try {
                playerView.sendMessage(new GameCommandIllegalCommand());
            }

            catch (Exception ex) {
                // Fatal error: print the stack trace to help debug
                ex.printStackTrace();
            }

            activateEffect();
        }
    }
}
