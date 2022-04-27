package it.polimi.ingsw.model;

import java.util.Arrays;

/**
 * Class representing the cloud tiles
 * @author Mattia Martelli
 */
public class CloudTile {
    private Color[] students = new Color[4];

    public CloudTile() {}

    /**
     * Return all the students currently present on the tile, without removing them
     * @return An array containing the students
     */
    public Color[] getStudents() {
        return Arrays.copyOf(students, students.length);
    }

    /**
     * Return all the students currently present on the tile and emptyes the tile
     * @return An array containing the students
     */
    public Color[] retrieveStudents() {
        Color[] temp = Arrays.copyOf(students, students.length);
        students = new Color[4];
        return temp;
    }

    /**
     * Set all the students on the tile to a specific set
     * @param students Array containing the set of students
     */
    public void setStudents(Color[] students) throws IllegalArgumentException {
        if (students.length != this.students.length)
            throw new IllegalArgumentException("Passed wrongly dimensioned array");

        this.students = Arrays.copyOf(students, students.length);
    }
}
