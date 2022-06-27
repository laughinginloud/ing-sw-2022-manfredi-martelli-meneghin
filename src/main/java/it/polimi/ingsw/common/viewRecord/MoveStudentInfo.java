package it.polimi.ingsw.common.viewRecord;

/**
 * Record representing the result of a student's movement
 * @param toDiningRoom <code>true</code> if it was moved to the player's dining room, <code>false</code> otherwise
 * @param islandNum    The index of the island where the student was moved, if it exists
 * @param studentIndex The index of the student that was moved
 * @author Sebastiano Meneghin
 */
public record MoveStudentInfo(boolean toDiningRoom, Integer islandNum, int studentIndex) {}
