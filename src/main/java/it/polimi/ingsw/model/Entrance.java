package it.polimi.ingsw.model;

import java.util.Arrays;

/**
 * Class representing the school's entrance,
 * to be used as part of SchoolBoard
 * @author Mattia Martelli
 */
public class Entrance {
    private Color[] students;

    /**
     * Constructor of the class 'Entrance'
     * @param size the dimension of the entrance to which the Entrance is initialized (an integer in the set {7,9})
     * @throws IllegalArgumentException exception thrown when an illegal argument is given
     */
    public Entrance(int size) throws IllegalArgumentException {
        if (size != 7 && size != 9)
            throw new IllegalArgumentException("Entrance's size must be either 7 or 9");

        students = new Color[size];
    }

    /**
     * Gets all the students currently in the entrance, without deleting them
     * @return An array containing all the colors that represent the students
     */
    public Color[] getStudents() {
        return students;
    }

    /**
     * Gets a specific student, removing it from the array in the process
     * @param index The position of the student in the entrance (a positive integer between 0 and students.length
     * @return The color that represents the student
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public Color retrieveStudent(int index) throws IllegalArgumentException {
        if (index < 0 || index > students.length)
            throw new IllegalArgumentException("Index out of bounds");

        Color temp = students[index];
        System.arraycopy(students, index + 1, students, index, students.length - index - 1);
        students[students.length - 1] = null;
        return temp;
    }

    /**
     * Set all the students in the entrance to a specified array
     * @param students The array containing the students
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setStudents(Color[] students) throws IllegalArgumentException {
        if (students.length != this.students.length)
            throw new IllegalArgumentException("Passed wrongly dimensioned array");

        this.students = Arrays.copyOf(students, students.length);
    }

    /**
     * Appends a students to the end of the array
     * @param student The color of the student to add (not null)
     */
    public void appendStudent(Color student) {
        for (int i = 0; i < students.length; ++i)
            if (students[i] == null) {
                students[i] = student;
                return;
            }
    }
}
