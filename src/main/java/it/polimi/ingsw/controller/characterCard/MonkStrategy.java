package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualView.VirtualView;

import java.util.HashMap;
import java.util.Map;


/**
 * Strategy representing the activation of the CharacterCard 'MONK'
 * @author Giovanni Manfredi
 */
public class MonkStrategy extends CharacterCardStrategy {
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

            /* TODO: [CommandFields] Trovare gli studenti che davvero sono utilizzabili
            *        Potremmo dover utilizzare un array di int (invece di un array di Color, siccome la sua selezione
            *        sarà posizionale rispetto agli studenti presenti sulla carta
            */
            // The server select the students that can be moved and the Island
            Color[] availableStudents = enhancedCard.getStudents();
            Island[] availableIslands = model.getIslands();

            // Create a Map and save the fields that will be sent to the player as RequestAction's payload
            Map<GameCommandValues, Object> monkMap = new HashMap<>();
            monkMap.put(GameCommandValues.COLORARRAY, availableStudents);
            monkMap.put(GameCommandValues.ISLANDARRAY, availableIslands);

            // The server asks the player which student he wants to move and the island he wants to move the student to
            GameCommand request = new GameCommandRequestAction(GameCommandActions.CHARACTERCARDEFFECT, monkMap);
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right kind
            if (response instanceof GameCommandChosenCharacterCardFields c) {
                // The player responds with the information requested by the server
                @SuppressWarnings("unchecked")
                Map<GameCommandValues, Object> chosenFields = (Map<GameCommandValues, Object>) c.executeCommand();

                // TODO [CharacterCard] index access
                // The server moves the student from the CharacterCard to the selected island
                Color tmp = enhancedCard.retrieveStudent(student_index);
                Island target = model.getIsland(island_index);
                target.setStudentCounters(tmp, target.getStudentCounters(tmp) + 1);

                // The server refills the card taking a student from the Bag (EmptyBagException)
                try{
                    tmp = model.getBag().drawStudents(1).drawnStudents()[0];
                } catch (EmptyBagException e){
                    data.setEmptyBagTrigger();
                }
                enhancedCard.appendStudent(tmp);

                // The server sets the Player to hasPlayedCard = true
                data.setPlayedCard();


                /* TODO [CharacterCard] - Check coinIncrease
                It could be useful create a method in CharacterCardStrategy who implements the following procedures
                       The server decrease the Player coinCount
                       The server increase the global coinCount
                       Only the first time {
                            The server update the characterCard's flag hasCoin
                            The server increase the characterCard's cost
                        }
                       The server notify all the players about those new game board conditions
                */


                // After the server managed the use of the CharacterCard, gets the updated values of CharacterCardsArray and IslandsArray
                CharacterCard[] updatedCharacterCards = model.getCharacterCards();
                Island[]        updatedIslands        = model.getIslands();

                // Creates a map containing the updated field to send to all the players
                Map<GameCommandValues, Object> afterEffectUpdate = new HashMap<>();
                afterEffectUpdate.put(GameCommandValues.CHARACTERCARDARRAY, updatedCharacterCards);
                afterEffectUpdate.put(GameCommandValues.ISLANDARRAY, updatedIslands);

                // Sends to all the players the updated fields
                Player[] players = data.getPlayersOrder();
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

        catch (Exception e){
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }
}
