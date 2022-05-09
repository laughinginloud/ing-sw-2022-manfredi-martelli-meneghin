package it.polimi.ingsw.controller.command;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.*;

import java.util.HashMap;
import java.util.Map;

public class GameCommandMoveStudent implements GameCommand{
    private final int     movedStudentIndex;
    private final boolean toDiningRoom;
    private final Integer islandIndex;

    public GameCommandMoveStudent(int movedStudentIndex, boolean toDiningRoom, Integer islandIndex) {
        this.movedStudentIndex = movedStudentIndex;
        this.toDiningRoom = toDiningRoom;
        this.islandIndex = islandIndex;
    }

    public Object executeCommand() {
        Player mover = ControllerData.getInstance().getCurrentPlayer();
        Color movedStudent = mover.getSchoolBoard().getEntrance().retrieveStudent(movedStudentIndex);

        // If the player decided to move the student to an Island
        if (!toDiningRoom) {
            Island islandToMoveTo = ControllerData.getInstance().getGameModel().getIsland(islandIndex);
            islandToMoveTo.setStudentCounters(movedStudent, islandToMoveTo.getStudentCounters(movedStudent) + 1 );
        }

        //If the player decided to move the student into his DiningRoom
        else {
            DiningRoom diningRoomToMoveTo = mover.getSchoolBoard().getDiningRoom();
            diningRoomToMoveTo.setStudentCounters(movedStudent, diningRoomToMoveTo.getStudentCounters(movedStudent) + 1);
        }

        return new Object[]{toDiningRoom, movedStudent};
    }
}
