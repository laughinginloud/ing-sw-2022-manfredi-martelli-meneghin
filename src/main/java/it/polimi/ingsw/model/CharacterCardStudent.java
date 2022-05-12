package it.polimi.ingsw.model;

/**
 * Class representing a "CharacterCardStudent"
 * @author Sebastiano Meneghin
 */
public class CharacterCardStudent extends CharacterCard {
    private final Color[] students;

    protected CharacterCardStudent (Character character) {
        super(character);

        if(character == Character.MONK || character == Character.PRINCESS)
            students = new Color[4];

        else
            students = new Color[6];
    }

    /**
     * Get the students on the CharacterCard, without deleting them
     * @return An array of Color containing currently on the CharacterCard
     */
    public Color[] getStudents() {
        Color[] temp = new Color[students.length];
        System.arraycopy(students, 0, temp, 0, students.length);
        return temp;
    }

    public Color retrieveStudent(int index) {
        if (index < 0 || index > students.length)
            throw new IllegalArgumentException("Index out of bounds");

        Color temp = students[index];
        System.arraycopy(students, index + 1, students, index, students.length - index - 1);
        students[students.length - 1] = null;
        return temp;
    }

    /**
     * Set a student of the field "students" to a specific Color
     * @param color The color to be set
     * @param index The index of the student (0 to "students.length - 1")
     */
    public void setStudents(Color color, int index) throws IllegalArgumentException {
        if (index < 0 || index > 5)
            throw new IllegalArgumentException("CharacterCardStudent.students has maximum length 6");

        this.students[index] = color;
    }

    public void appendStudent(Color color) {
        for (int i = 0; i < students.length; ++i) {
            if (students[i] == null) {
                students[i] = color;
                return;
            }
        }
    }
}
