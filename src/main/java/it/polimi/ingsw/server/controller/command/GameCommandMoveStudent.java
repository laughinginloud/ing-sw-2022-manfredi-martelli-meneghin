package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.viewRecord.MoveStudentInfo;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.common.model.Color;
import it.polimi.ingsw.common.model.DiningRoom;
import it.polimi.ingsw.common.model.Island;
import it.polimi.ingsw.common.model.Player;

import java.util.Optional;

public class GameCommandMoveStudent implements GameCommand{
    private final int     movedStudentIndex;
    private final boolean toDiningRoom;
    private final Integer islandIndex;

    public GameCommandMoveStudent(MoveStudentInfo moveStudentInfo) {
        this.movedStudentIndex = moveStudentInfo.studentIndex();
        this.toDiningRoom      = moveStudentInfo.toDiningRoom();
        this.islandIndex       = moveStudentInfo.islandNum();
    }

    public Object executeCommand() {
        Player mover        = ControllerData.getInstance().getCurrentPlayer();
        Color  movedStudent = mover.getSchoolBoard().getEntrance().retrieveStudent(movedStudentIndex);

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
