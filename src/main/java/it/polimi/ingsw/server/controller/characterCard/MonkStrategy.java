package it.polimi.ingsw.server.controller.characterCard;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Strategy representing the activation of the CharacterCard 'MONK'
 * @author Giovanni Manfredi
 */
public class MonkStrategy extends CharacterCardStrategy {

    /**
     * Constructor of the class 'MonkStrategy'
     * @param card the card to which the class is initialized
     */
    public MonkStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'MONK'
     */
    @Override
    public void activateEffect() {
        try {
            ControllerData data = ControllerData.getInstance();
            GameModel model = data.getGameModel();
            Player player = data.getCurrentPlayer();
            VirtualView playerView = data.getPlayerView(player);

            // Cast the CharacterCard to a CharacterCardStudent
            CharacterCardStudent enhancedCard = (CharacterCardStudent) card;

            // The server select the students that can be moved and the Island (the not-Null students)
            Color[] availableStudents = getAvailableStudents(enhancedCard.getStudents(), 4);
            Island[] availableIslands = model.getIslands();

            // Create a Map and save the fields that will be sent to the player as RequestAction's payload
            Map<GameValues, Object> monkMap = new HashMap<>();
            monkMap.put(GameValues.CARDSTUDENTS, availableStudents);
            monkMap.put(GameValues.ISLANDARRAY, availableIslands);

            // The server asks the player which student he wants to move and the island he wants to move the student to
            GameCommand request = new GameCommandRequestAction(GameActions.CHARACTERCARDEFFECT, monkMap);
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right kind
            if (response instanceof GameCommandChosenCharacterCardFields c) {
                // The player responds with the information requested by the server
                @SuppressWarnings("unchecked")
                Map<GameValues, Object> chosenFields = (Map<GameValues, Object>) c.executeCommand();

                // A cast for the information requested by the server (student and island index)
                int student_index = (int) chosenFields.get(GameValues.STUDENTINDEX);
                int  island_index = (int) chosenFields.get(GameValues.ISLANDINDEX);

                // The server moves the student from the CharacterCard to the selected island
                Color movedStudent = enhancedCard.retrieveStudent(student_index);
                Island targetIsland = model.getIsland(island_index);
                targetIsland.setStudentCounters(movedStudent, targetIsland.getStudentCounters(movedStudent) + 1);

                // The server refills the card (appending the player) taking a student from the Bag (EmptyBagException)
                try {
                    movedStudent = model.getBag().drawStudents(1).drawnStudents()[0];
                }

                catch (EmptyBagException e){
                    data.setEmptyBagTrigger();
                }
                enhancedCard.appendStudent(movedStudent);

                // After the server managed the use of the CharacterCard, gets the updated values of
                // CharacterCardsArray and IslandsArray and put them in the map that will be broadcast
                afterEffectUpdate.put(GameValues.CHARACTERCARDARRAY, model.getCharacterCards());
                afterEffectUpdate.put(GameValues.ISLANDARRAY,        model.getIslands());
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

    /**
     * This method gets the Students array of this card and reduces it, removing all null elements
     * @param students the array that needs to be reduced
     * @param max_length the length of the array
     * @return the new array of not-Null elements
     */
    private Color [] getAvailableStudents(Color[] students, int max_length){
        int length = 0;
        do {
            if (students[length] != null)
                length++;
        } while (length < max_length && students[length] != null);

        return Arrays.copyOf(students,length);
    }
}


