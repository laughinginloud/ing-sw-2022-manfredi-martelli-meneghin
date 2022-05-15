package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.*;

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
        ControllerData data = ControllerData.getInstance();
        GameModel model = data.getGameModel();
        Player currentPlayer = data.getCurrentPlayer();

        // TODO [CharacterCardStrategy] Command implementation
        // The server asks the player which student he wants to move to a DiningRoom (requestAction())
        // The player responds with the information requested by the server (responseAction(student_index))

        // The server moves the student from the CharacterCard to the SchoolBoard (FullBoardException?)
        Color movedStudent = ((CharacterCardStudent) card).retrieveStudent(student_index);
        DiningRoom diningRoom = currentPlayer.getSchoolBoard().getDiningRoom();
        int currentStudents = diningRoom.getStudentCounters(movedStudent);
        diningRoom.setStudentCounters(movedStudent, currentStudents + 1);

        // The server refills the card taking a student from the Bag (EmptyBagException)
        try {
            movedStudent = model.getBag().drawStudents(1).drawnStudents()[0];
        }

        catch (EmptyBagException e){
            data.setEmptyBagTrigger();
        }

        ((CharacterCardStudent) card).appendStudent(movedStudent);

        // TODO [CharacterCardStrategy] Command implementation
        // sendInfo to all players
    }
}
