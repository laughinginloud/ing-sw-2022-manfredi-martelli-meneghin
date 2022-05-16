package it.polimi.ingsw.model;

import java.util.Arrays;

/**
 * Class representing the cloud tiles
 * @author Mattia Martelli
 */
public class CloudTile {
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
        return Arrays.copyOf(students, students.length);
    }

    /**
     * Removes all the students currently present on the tile and returns them in an array
     * @return An array containing the students
     */
    public Color[] retrieveStudents() {
        Color[] temp = Arrays.copyOf(students, students.length);
        students = new Color[4];
        return temp;
    }

    /**
     * Sets all the students on the tile to a specific array of students
     * @param students Array containing the set of students
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setStudents(Color[] students) throws IllegalArgumentException {
        if (students.length != this.students.length)
            throw new IllegalArgumentException("Passed wrongly dimensioned array");

        this.students = Arrays.copyOf(students, students.length);
    }
}
