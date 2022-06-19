package it.polimi.ingsw.common.model;

/**
 * Data type dedicated to the output of 'drawStudents(int n)'
 * @author Mattia Martelli
 * @param drawnStudents A variable length array of Color containing the students drawn
 *                      (if there are fewer students than required, its size is < n) - not null
 * @param emptyBag true if the EmptyBagException was thrown, false otherwise
 */
public record BagResult(Color[] drawnStudents, boolean emptyBag) {}
