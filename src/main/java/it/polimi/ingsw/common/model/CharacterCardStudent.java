package it.polimi.ingsw.common.model;

/**
 * Class representing CharacterCards that need students on them - MONK, JESTER and PRINCESS
 * @author Sebastiano Meneghin
 */
public final class CharacterCardStudent extends CharacterCard {
    private final Color[] students;

    /**
     * Constructor of the class 'CharacterCardStudent'
     * @param character the character to which the card is set (not null and in the set {MONK, JESTER, PRINCESS})
     */
    CharacterCardStudent (Character character) {
        super(character);

        if (character == Character.MONK || character == Character.PRINCESS)
            students = new Color[4];

        else
            students = new Color[6];
    }

    /**
     * Gets the students on the CharacterCard, without deleting them
     * @return An array of Color containing currently on the CharacterCard
     */
    public Color[] getStudents() {
        Color[] temp = new Color[students.length];
        System.arraycopy(students, 0, temp, 0, students.length);
        return temp;
    }

    /**
     * Removes the student from the specified index and moves it the other elements in the array,
     * leaving the last element as null
     * @author Giovanni Manfredi
     * @param index the index of the student that needs to be removed (positive integer between 0 and 5)
     * @return the student removed from the array
     */
    public Color retrieveStudent(int index) {
        assert index >= 0 && index < students.length: "Index out of bounds";

        Color temp = students[index];
        System.arraycopy(students, index + 1, students, index, students.length - index - 1);
        students[students.length - 1] = null;
        return temp;
    }

    /**
     * Sets a student of the field "students" to a specific Color
     * @param color The color to be set (not null)
     * @param index The index of the student (0 to "students.length - 1")
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setStudents(Color color, int index) throws IllegalArgumentException {
        assert index >= 0 && index < students.length: "Index out of bounds";

        this.students[index] = color;
    }
    
    /**
     * Adds the specified student at the end of the student's array
     * @author Giovanni Manfredi
     * @param color the student to be added (not null)
     */
    public void appendStudent(Color color) {
        for (int i = 0; i < students.length; ++i) {
            if (students[i] == null) {
                students[i] = color;
                return;
            }
        }
    }
}
