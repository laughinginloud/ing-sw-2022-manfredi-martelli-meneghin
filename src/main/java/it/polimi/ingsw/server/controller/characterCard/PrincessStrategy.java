package it.polimi.ingsw.server.controller.characterCard;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.PlayCharacterAction;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.server.controller.state.GameStateMoveStudents;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Strategy representing the activation of the CharacterCard 'PRINCESS'
 * @author Giovanni Manfredi
 */
public class PrincessStrategy extends CharacterCardStrategy {

    /**
     * Constructor of the class 'PrincessStrategy'
     * @param card the card to which the class is initialized
     */
    public PrincessStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'PRINCESS'
     */
    @Override
    public void activateEffect() {
        try {
            ControllerData data = ControllerData.getInstance();
            GameModel model = data.getGameModel();
            Player currentPlayer = data.getCurrentPlayer();
            VirtualView playerView = data.getPlayerView(currentPlayer);

            // Cast the current characterCard to a CharacterCardStudent
            CharacterCardStudent enhancedCard = (CharacterCardStudent) card;

            // Gets the students currently present on the CharacterCard, then select the student that can be moved to a DiningRoomTable
            Color[] characterCardStudents = enhancedCard.getStudents();
            Color[] movableStudents       = getMovableStudents(currentPlayer, characterCardStudents);

            // Gets the position of the characterCard in the model's characterCardArray
            int position = CharacterCardManager.getCharacterCardPosition(Character.PRINCESS);

            // Create a Map and save the field that will be sent to the player as RequestAction's payload
            InfoMap princessMap = new InfoMap();
            princessMap.put(GameValues.CHARACTERVALUE,        PlayCharacterAction.PRINCESSFIRST);
            princessMap.put(GameValues.CARDSTUDENTS,          movableStudents);
            princessMap.put(GameValues.CHARACTERCARDPOSITION, position);

            // The server asks the player which students would like to move from the entrance to its DiningRoom
            GameCommand request = new GameCommandRequestAction(GameActions.CHARACTERCARDEFFECT, princessMap);
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right kind
            if (response instanceof GameCommandChosenCharacterCardFields c) {
                // The player responds with the information requested by the server
                InfoMap chosenField = (InfoMap) c.executeCommand();

                // Gets the index of students that the player wants to move, from the Map received from the client
                int characterCardStudentIndex = (int) chosenField.get(GameValues.STUDENTINDEX);

                // The server moves the student from the CharacterCard to the SchoolBoard
                Color movedStudent = enhancedCard.retrieveStudent(characterCardStudentIndex);
                DiningRoom diningRoom = currentPlayer.getSchoolBoard().getDiningRoom();
                int currentStudents = diningRoom.getStudentCounters(movedStudent);
                diningRoom.setStudentCounters(movedStudent, currentStudents + 1);

                // The server refills the card taking a student from the Bag (EmptyBagException)
                Color drawnStudent = null;
                try {
                    drawnStudent = model.getBag().drawStudents(1).drawnStudents()[0];
                } catch (EmptyBagException e) {
                    data.setEmptyBagTrigger();
                }

                enhancedCard.appendStudent(drawnStudent);

                // Update the globalProfessorTable according to the new SchoolBoards (professor could change location)
                for (Color student: Color.values()) {
                    Player controllingPlayer = model.getGlobalProfessorTable().getProfessorLocation(student);
                    // If the currentPlayer is not the controllingPlayer and the professor needs to be moved
                    // The professorLocation is changed to the currentPlayer
                    if (!currentPlayer.equals(controllingPlayer) &&
                        GameStateMoveStudents.checkProfessorMovement(currentPlayer, controllingPlayer, student)) {

                        model.getGlobalProfessorTable().setProfessorLocation(student, currentPlayer);
                    }
                }

                // After the server managed the use of the CharacterCard, gets the updated values of schoolBoards, globalProfessorTable
                // and characterCards
                Player[]             players               = model.getPlayer();
                DiningRoom[]         updatedDiningRooms    = new DiningRoom[players.length];
                GlobalProfessorTable updatedGPT            = model.getGlobalProfessorTable();
                CharacterCard[]      updatedCharacterCards = model.getCharacterCards();

                // Gets the diningRoom of each player and saves it in updatedDiningRooms
                for (int i = 0; i < players.length; i++)
                    updatedDiningRooms[i] = players[i].getSchoolBoard().getDiningRoom();

                // Save into the afterEffectUpdate the updated fields that will be broadcast to the players
                afterEffectUpdate.put(GameValues.CHARACTERCARDARRAY, updatedCharacterCards);
                afterEffectUpdate.put(GameValues.DININGROOMARRAY, updatedDiningRooms);
                afterEffectUpdate.put(GameValues.GLOBALPROFESSORTABLE, updatedGPT );
            }

            // If the response is of the wrong kind throw an exception to help debug
            else
                throw new IllegalStateException("Wrong command received: " + response);
        }

        catch (Exception e){
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    /**
     * Select which students from the availableStudents are really movable, according to the seats on the DiningRoomTables
     * @param player The player who will move the student
     * @param availableStudents The students that are currently on the characterCard
     * @return An array of color representing the students that can really be moved
     */
    private Color[] getMovableStudents (Player player, Color[] availableStudents) {
        DiningRoom diningRoom = player.getSchoolBoard().getDiningRoom();
        List<Color> movableStudentsList = new ArrayList<>();

        // For each student of the array availableStudents select if it's movable
        for (Color availableStudent : availableStudents)
            // A student is movable if the DiningTable of the same color has free seat(s)
            if (diningRoom.getStudentCounters(availableStudent) < 10)
                movableStudentsList.add(availableStudent);

        // Save the movableStudentList in an array movableStudents of the same size
        Color[] movableStudents = new Color[movableStudentsList.size()];
        for (int i = 0; i < movableStudentsList.size(); i++)
            movableStudents[i] = movableStudentsList.get(i);

        return movableStudents;

    }

    /**
     * Link the selected movableStudent to the correspondent characterCardStudent
     * @param characterCardStudents An array of students containing the characterCard's students
     * @param movableStudents An array of students containing the movableStudents
     * @param movableStudentIndex The index of the students selected by the player from the movableStudents
     * @return The index of the student selected by the player mapped on the characterCardStudents
     */
    private int getCharacterCardStudentIndex (Color[] characterCardStudents, Color[] movableStudents, int movableStudentIndex) {
        Color movedStudent = movableStudents[movableStudentIndex];
        int sameColorAntecedentMovedStudents  = 0;
        int sameColorAntecedentCharacterCardStudents = 0;
        boolean sameAntecedents = false;

        for (int i = 0; i < movableStudentIndex; i++)
            if (movableStudents[i] == movedStudent)
                sameColorAntecedentMovedStudents++;

        if (sameColorAntecedentCharacterCardStudents == sameColorAntecedentMovedStudents)
            sameAntecedents = true;

        for (int i = 0; i < characterCardStudents.length; i++)
            if (characterCardStudents[i] == movedStudent)
                if (!sameAntecedents)
                    sameColorAntecedentMovedStudents++;
                else
                    return i;

        throw new IllegalStateException("CharacterCard must contain the student select by the movableStudents");
    }
}
