package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.common.viewRecord.MoveStudentInfo;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.common.model.Color;
import it.polimi.ingsw.common.model.DiningRoom;
import it.polimi.ingsw.common.model.Island;
import it.polimi.ingsw.common.model.Player;

/**
 * Move a student from the entrance of the current player to his dining room or a specified island
 * @author Sebastiano Meneghin
 */
public final class GameCommandMoveStudent implements GameCommand {
    private final int     movedStudentIndex;
    private final boolean toDiningRoom;
    private final Integer islandIndex;

    /**
     * Build the command with the info received from the player
     * @param moveStudentInfo A record containing the movement info
     */
    public GameCommandMoveStudent(MoveStudentInfo moveStudentInfo) {
        this.movedStudentIndex = moveStudentInfo.studentIndex();
        this.toDiningRoom      = moveStudentInfo.toDiningRoom();
        this.islandIndex       = moveStudentInfo.islandNum();
    }

    /**
     * Move the student to the specified location
     * @return An <code>Tuple&lt;Boolean, Color[]&gt;</code> containing a <code>boolean</code> that
     *         represents whether the student was moved to the current player's dining room and a <code>Color</code>
     *         that represents the student that was moved
     */
    public Tuple<Boolean, Color> executeCommand() {
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

        return new Tuple<>(toDiningRoom, movedStudent);
    }
}
