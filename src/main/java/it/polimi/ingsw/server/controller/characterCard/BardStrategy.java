package it.polimi.ingsw.server.controller.characterCard;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.PlayCharacterAction;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.server.controller.state.GameStateMoveStudents;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Strategy representing the activation of the CharacterCard 'BARD'
 * @author Giovanni Manfredi
 */
public class BardStrategy extends CharacterCardStrategy {

    /**
     * Constructor of the class 'BardStrategy'
     * @param card the card to which the class is initialized
     */
    public BardStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'BARD'
     */
    @Override
    public void activateEffect() {
        try {
            ControllerData data       = ControllerData.getInstance();
            GameModel      model      = data.getGameModel();
            Player         curPlayer  = data.getCurrentPlayer();
            VirtualView    playerView = data.getPlayerView(curPlayer);

            // Sets the max number of students that can be moved using a single time this characterCard
            int maxNumOfMovements = 2;

            // Create a Map and save in it the maxNumOfMovement in order to send it to the player
            Map<GameValues, Object> movementMap = new HashMap<>();
            movementMap.put(GameValues.CHARACTERVALUE, PlayCharacterAction.BARDFIRST);
            movementMap.put(GameValues.MAXMOVEMENTBARD, maxNumOfMovements);

            // The server asks the player how many students he would like to move using the Bard's cardEffect
            GameCommand request  = new GameCommandRequestAction(GameActions.CHARACTERCARDEFFECT, movementMap);
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right kind
            if (response instanceof GameCommandChosenCharacterCardFields c) {
                // The player responds with the information requested by the server
                @SuppressWarnings("unchecked")
                Map<GameValues, Object> chosenField = (Map<GameValues, Object>) c.executeCommand();

                // Gets the number of students that the player wants to move, from the Map received from the client
                // TODO: [ClientImplementation] Int movementBard chosen from the client has to be between 1 and maxNumOfMovements
                int chosenNumOfMovement = (int) chosenField.get(GameValues.MOVEMENTBARD);

                // For chosenNumOfMovement times asks the player which students he would like to move, waits for response and notifies all the players after the movement
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

    private void changeStudent(ControllerData data, GameModel model, Player curPlayer, VirtualView playerView) throws Exception {
        Player[] players = model.getPlayer();

        // Gets the students present in the current player's Entrance
        Color[] entranceStudents = curPlayer.getSchoolBoard().getEntrance().getStudents();

        // Gets the entranceStudents that can be swapped using the Bard's effect
        Color[] swappableStudents = getSwappableStudents(curPlayer, entranceStudents);

        // Create a Map where save the fields that will be sent to the player as RequestAction's payload
        Map<GameValues, Object> bardMap = new HashMap<>();
        bardMap.put(GameValues.CHARACTERVALUE, PlayCharacterAction.BARDSECOND);
        bardMap.put(GameValues.BARDSWAPPABLESTUDENTS, swappableStudents);

        // Add to the Map that will be sent to the player the SwapMap related to the swappableStudents
        Map<Color, Boolean[]> bardPossibleMovementMap = setBardPossibleMovements(curPlayer, swappableStudents);
        bardMap.put(GameValues.BARDSWAPMAP, bardPossibleMovementMap);

        // The server asks the player which students would like to move (one from the Card, one from the Entrance)
        GameCommand movementRequest  = new GameCommandRequestAction(GameActions.CHARACTERCARDEFFECT, bardMap);
        GameCommand movementResponse = playerView.sendRequest(movementRequest);

        // If the response is of the right kind
        if (movementResponse instanceof GameCommandChosenCharacterCardFields c) {
            // The player responds with the information requested by the server
            @SuppressWarnings("unchecked")
            Map<GameValues, Object> chosenField = (Map<GameValues, Object>) c.executeCommand();

            // Gets the index of the swappableStudent and the diningRoomTable's student color of the player wants to swap the swappableStudent with
            int   entranceStudentIndex    = (int)   chosenField.get(GameValues.ENTRANCESTUDENTINDEX);
            Color diningRoomTableStudent   = (Color) chosenField.get(GameValues.DININGROOMTABLECOLOR);

            // The server exchanges the students across Entrance and DiningRoom (using a tmp student)
            DiningRoom diningRoom = curPlayer.getSchoolBoard().getDiningRoom();
            Entrance entrance = curPlayer.getSchoolBoard().getEntrance();

            // Removes the chosen student from the entrance
            Color movedStudent = entrance.retrieveStudent(entranceStudentIndex);

            // Gets the number of students in the dining room of the chosen color and increases their count by one
            int numTargetDiningRoomStudents = diningRoom.getStudentCounters(movedStudent);
            diningRoom.setStudentCounters(movedStudent, numTargetDiningRoomStudents + 1);

            // Gets the number of students in the dining room of the exchanged color and decreases their count by one
            int numExchangeDiningRoomStudents = diningRoom.getStudentCounters(diningRoomTableStudent);
            diningRoom.setStudentCounters(diningRoomTableStudent, numExchangeDiningRoomStudents - 1 );

            // Adds the exchanged student in the entrance
            entrance.appendStudent(diningRoomTableStudent);

            // Update the globalProfessorTable according to the new SchoolBoard (professor could change location)
            for (Color student: Color.values()) {
                Player controllingPlayer = model.getGlobalProfessorTable().getProfessorLocation(student);
                // If the currentPlayer is not the controllingPlayer and the professor needs to be moved
                // The professorLocation is changed to the currentPlayer
                if (!curPlayer.equals(controllingPlayer) &&
                    GameStateMoveStudents.checkProfessorMovement(curPlayer, controllingPlayer, student)) {

                    model.getGlobalProfessorTable().setProfessorLocation(student, curPlayer);
                }
            }

            // After the server managed the use of the CharacterCard, gets the updated values of the SchoolBoards and of the GlobalProfessorTable
            SchoolBoard[]        updatedSchoolBoards = new SchoolBoard[players.length];
            GlobalProfessorTable updatedGPT          = model.getGlobalProfessorTable();

            // Gets the schoolBoard of each player and saves it in updatedSchoolBoards
            for (int i = 0; i < players.length; i++)
                updatedSchoolBoards[i] = players[i].getSchoolBoard();

            // Save into the afterEffectUpdate the updated fields that will be broadcast to the players
            afterEffectUpdate.put(GameValues.SCHOOLBOARDARRAY, updatedSchoolBoards);
            afterEffectUpdate.put(GameValues.GLOBALPROFESSORTABLE, updatedGPT);
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

            changeStudent(data, model, curPlayer, playerView);
        }
    }

    /**
     * Gets all the entranceStudents that can be swapped with a diningRoom's student
     * @param player The player who is going to swap a student
     * @param entranceStudents The student in the entrance of the current player
     * @return An array of Color representing the students of the entrance that can be swapped
     */
    public static Color[] getSwappableStudents(Player player, Color[] entranceStudents) {
        DiningRoom diningRoom = player.getSchoolBoard().getDiningRoom();
        List<Color> swappableStudentsList = new ArrayList<>();
        boolean addable = false;

        // For each student currently present in the entrance
        for (Color color : entranceStudents) {
            // If there's a DiningTable of a different color of the student that is not empty, set "addable" to true
            for (Color compareColor : Color.values())
                if (color != compareColor && diningRoom.getStudentCounters(compareColor) > 0)
                    addable = true;

            // If the DiningTable of the same color of the student is full, set "addable" to false
            if (diningRoom.getStudentCounters(color) == 10)
                addable = false;

            // If the student is swappable at least with a single DiningRoomTable, it is not already present on the list, then add it to the swappableStudents list
            if (addable && !swappableStudentsList.contains(color))
                swappableStudentsList.add(color);
        }

        // If the swappableStudentsList is empty, return null (it should happen only during the CharacterCardManager's function call
        if (swappableStudentsList.isEmpty())
            return null;

        // Save the swappableStudentsList in a ColorArray of the same size
        Color[] swappableStudents = new Color[swappableStudentsList.size()];
        for (int i = 0; i < swappableStudentsList.size(); i++)
            swappableStudents[i] = swappableStudentsList.get(i);

        return swappableStudents;
    }

    /**
     * For each swappableStudents calculates which DiningTable contains a student which is swappable with the entranceStudent
     * @param player The player that has to swap the student
     * @param swappableStudents The entranceStudents that can be swapped
     * @return A Map(Color, Boolean[]) containing, for each swappableStudent, the compatibleDiningTable flags
     */
    private Map<Color, Boolean[]> setBardPossibleMovements (Player player, Color[] swappableStudents) {
        DiningRoom diningRoom = player.getSchoolBoard().getDiningRoom();
        Map<Color, Boolean[]> swapMap = new HashMap<>();

        // For each swappableStudents (that is contained in the Entrance)
        for (Color student : swappableStudents) {
            Boolean[] compatibleDiningTable = {false, false, false, false, false};

            // For each color different by the student's color, set the compatibleDiningTable to true
            // That is because the entrance's student can only be swapped with different colored students
            // and then at least one DiningTable has to contain at least one student
            for (Color compareColor : Color.values())
                if (student != compareColor && diningRoom.getStudentCounters(compareColor) > 0)
                    compatibleDiningTable[compareColor.ordinal()] = true;

            // Add an entry in the map with the swappableStudent as a key and the compatibleDiningTable flags array as value
            swapMap.putIfAbsent(student, compatibleDiningTable);
        }

        return swapMap;
    }

    /**
     * Link the selected swappableStudent to the correspondent entranceStudent
     * @param entranceStudents An array of students containing the entrance's students
     * @param swappableStudent An array of students containing the swappableStudents
     * @param swappableStudentIndex The index of the students selected by the player from the swappableStudents
     * @return The index of the student selected by the player mapped on the entranceStudents
     */
    private int getEntranceStudentIndex (Color[] entranceStudents, Color[] swappableStudent, int swappableStudentIndex) {
        Color swappedStudent = swappableStudent[swappableStudentIndex];
        int sameColorAntecedentSwappedStudents  = 0;
        int sameColorAntecedentEntranceStudents = 0;
        boolean sameAntecedents = false;

        for (int i = 0; i < swappableStudentIndex; i++)
            if (swappableStudent[i] == swappedStudent)
                sameColorAntecedentSwappedStudents++;

        if (sameColorAntecedentEntranceStudents == sameColorAntecedentSwappedStudents)
            sameAntecedents = true;

        for (int i = 0; i < entranceStudents.length; i++)
            if (entranceStudents[i] == swappedStudent)
                if (!sameAntecedents)
                    sameColorAntecedentSwappedStudents++;
                else
                    return i;

        throw new IllegalStateException("Entrance must contain the student select by the swappableStudents");
    }
}
