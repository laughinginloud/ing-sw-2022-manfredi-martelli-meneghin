package it.polimi.ingsw.common.model;

import static it.polimi.ingsw.common.utils.Methods.copyOf;

/**
 * Class representing the cloud tiles
 * @author Mattia Martelli
 */
public final class CloudTile {
    private Color[] students = new Color[4];

    /**
     * Constructor of the class 'CloudTile'
     */
    public CloudTile() {}

    /**
     * Gets all the students currently present on the tile, without removing them
     * @return An array containing the students
     */
    public Color[] getStudents() {
        return copyOf(students);
    }

    /**
     * Removes all the students currently present on the tile and returns them in an array
     * @return An array containing the students
     */
    public Color[] retrieveStudents() {
        Color[] temp = copyOf(students);
        students = new Color[4];
        return temp;
    }

    /**
     * Sets all the students on the tile to a specific array of students
     * @param students Array containing the set of students
     */
    public void setStudents(Color[] students) {
        assert students.length == this.students.length: "Passed wrongly dimensioned array";

        this.students = copyOf(students);
    }
}
