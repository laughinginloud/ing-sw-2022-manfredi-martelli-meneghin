package it.polimi.ingsw.model;

/**
 * Class representing a "CharactedCardStudent"
 * @author Sebastiano Meneghin
 */
public class CharacterCardStudent extends CharacterCard{
    private Color[] students;

    protected CharacterCardStudent (int cardID) {
        super(cardID);

        if(cardID == 0 || cardID == 10)
            students = new Color[4];

        else
            students = new Color[6];
    }

    /**
     * Get the students on the CharacterCard, without deleting them
     * @return An array of Color containing currently on the CharacterCard
     */
    public Color[] getStudent() {
        Color[] temp = new Color[students.length];
        System.arraycopy(students, 0, temp, 0, students.length);
        return temp;
    }

    /**
     * Set a student of the field "students" to a specific Color
     * @param color The color to be set
     * @param index The index of the student (0 to "students.length - 1")
     */
    public void setStudents(Color color, int index) { this.students[index] = color; }
}
