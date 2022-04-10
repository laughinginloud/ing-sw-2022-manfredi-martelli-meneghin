package it.polimi.ingsw.model;

import java.util.Arrays;

/**
 * Class representing the school's entrance
 * To be used as part of SchoolBoard
 * @author Mattia Martelli
 */
public class Entrance {
    private Color[] students;

    public Entrance(int size) {
        students = new Color[size];
    }

    /**
     * Get all the students currently in the entrance, without deleting them
     * @return An array containing all the colors that represent the students
     */
    public Color[] getStudents() {
        return students;
    }

    /**
     * Get a specific student, removing it from the array in the process
     * @param index The position of the student in the entrance, starting from 0
     * @return The color that represents the student
     */
    public Color retrieveStudent(int index) {
        Color temp = students[index];
        System.arraycopy(students, index + 1, students, index, students.length - index - 1);
        students[students.length - 1] = null;
        return temp;
    }

    /**
     * Set all the students in the entrance to the set specified
     * @param students The set containing the students
     */
    public void setStudents(Color[] students) {
        this.students = Arrays.copyOf(students, students.length);
    }

    /**
     * Appends a students to the end of the end of the array
     * @param student The color of the student to add
     */
    public void appendStudent(Color student) {
        for (int i = 0; i < students.length; ++i)
            if (students[i] == null) {
                students[i] = student;
                return;
            }
    }
}
