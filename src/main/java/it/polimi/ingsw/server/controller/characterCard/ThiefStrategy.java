package it.polimi.ingsw.server.controller.characterCard;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.PlayCharacterAction;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Strategy representing the activation of the CharacterCard 'THIEF'
 * @author Giovanni Manfredi
 */
public class ThiefStrategy extends CharacterCardStrategy {

    /**
     * Constructor of the class 'ThiefStrategy'
     * @param card the card to which the class is initialized
     */
    public ThiefStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'THIEF'
     */
    @Override
    public void activateEffect() {
        try {
            ControllerData data       = ControllerData.getInstance();
            GameModel      model      = data.getGameModel();
            Player         curPlayer  = data.getCurrentPlayer();
            VirtualView    playerView = data.getPlayerView(curPlayer);

            // Gets the reducible colors the player can choose from using the characterCard's effect
            Color[] reducibleColors = getReducibleColors(model, data);

            // Create a Map and save the field that will be sent to the player as RequestAction's payload
            Map<GameValues, Object> thiefMap = new HashMap<>();
            thiefMap.put(GameValues.CHARACTERVALUE, PlayCharacterAction.THIEFFIRST);
            thiefMap.put(GameValues.REDUCIBLECOLOR, reducibleColors);

            // The server asks the player which students would like to move (one from the Card, one from the Entrance)
            GameCommand request  = new GameCommandRequestAction(GameActions.CHARACTERCARDEFFECT, thiefMap);
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right kind
            if (response instanceof GameCommandChosenCharacterCardFields c) {
                // The player responds with the information requested by the server
                @SuppressWarnings("unchecked")
                Map<GameValues, Object> chosenField = (Map<GameValues, Object>) c.executeCommand();

                // Gets the color of the students the player wants to reduce from all the players' diningRooms
                Color colorToReduce = (Color) chosenField.get(GameValues.REDUCECOLOR);

                // The server reduces 3 (or all) students of the specified color from each SchoolBoard and add them to the Bag
                Bag      bag        = model.getBag();
                Player[] players    = model.getPlayer();
                int currentStudents;

                for (Player player : players) {
                    // Gets the students currently present in the selected player DiningRoomTable containing students of "colorToReduce" color
                    currentStudents = player.getSchoolBoard().getDiningRoom().getStudentCounters(colorToReduce);

                    // If there are three or more students, reduces three students from the diningRoomTable and adds them to the Bag
                    if (currentStudents >= 3) {
                        player.getSchoolBoard().getDiningRoom().setStudentCounters(colorToReduce, currentStudents - 3);
                        bag.setStudentCounters(colorToReduce, bag.getStudentCounters(colorToReduce) + 3);
                    }

                    // If there are less than three students, reduces them all and adds them to the Bag
                    else {
                        player.getSchoolBoard().getDiningRoom().setStudentCounters(colorToReduce, 0);
                        bag.setStudentCounters(colorToReduce, bag.getStudentCounters(colorToReduce) + currentStudents);
                    }
                }

                // Gets the diningRoom of each player and saves it in updatedDiningRooms
                DiningRoom[] updatedDiningRooms = new DiningRoom[players.length];
                for (int i = 0; i < players.length; i++)
                    updatedDiningRooms[i] = players[i].getSchoolBoard().getDiningRoom();

                // Save into the afterEffectUpdate the updated field that will be broadcast to the players
                afterEffectUpdate.put(GameValues.DININGROOM, updatedDiningRooms);
            }

            // If the response is of the wrong kind throw an exception to help debug
            else
                throw new IllegalStateException("Wrong command received: " + response);
        }

        catch (Exception ex) {
            // Fatal error: print the stack trace to help debug
            ex.printStackTrace();
        }
    }

    /**
     * Gets all the color that can be reduced from all the player's diningRoom using the characterCard effect
     * @param model The GameModel of the current Game
     * @param data The ControllerData of the current Game
     * @return An array of color representing the colors of students that can be reduced from the DiningRooms
     */
    private Color[] getReducibleColors(GameModel model, ControllerData data) {
        int          numOfPlayers       = data.getNumOfPlayers();
        Player[]     players            = model.getPlayer();
        DiningRoom[] diningRooms        = new DiningRoom[numOfPlayers];
        List<Color>  reducibleColorList = new ArrayList<>();

        // Gets all the players' diningRooms
        for (int i = 0; i < numOfPlayers; i++)
            diningRooms[i] = players[i].getSchoolBoard().getDiningRoom();

        // For each color, check if there's at least one diningRoom containing at least one student of that color
        for (Color color : Color.values()) {
            int numOfEmptyDiningTable = 0;

            // Counts how many diningRooms of the selected color are empty
            for (DiningRoom diningRoom : diningRooms)
                if (diningRoom.getStudentCounters(color) == 0)
                    numOfEmptyDiningTable++;

            // If there's at least on diningRooms which of the selected color which contains a student, add
            // the selected color to the reducibleColorList
            if (numOfEmptyDiningTable < numOfPlayers)
                reducibleColorList.add(color);
        }

        // Save the reducibleColorList in an array "reducibleColor" of the same size
        Color[] reducibleColor = new Color[reducibleColorList.size()];
        for (int i = 0; i < reducibleColorList.size(); i++)
            reducibleColor[i] = reducibleColorList.get(i);

        return reducibleColor;
    }
}
