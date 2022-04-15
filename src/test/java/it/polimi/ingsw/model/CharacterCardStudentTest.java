package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Test for class "CharacterCardStudent"
 * @author Sebastiano Meneghin
 */
class CharacterCardStudentTest {
    private CharacterCardStudent characterCardStudentTest;
    private Color[] studentsTest = new Color[]{Color.RED, Color.PINK, Color.GREEN, Color.GREEN, Color.BLUE, Color.YELLOW};
    private Field studentsField;
    private final int cardIDTest = 100;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        characterCardStudentTest = new CharacterCardStudent(cardIDTest);

        // Use reflection to get the private field "noEntryCount" and change its visibility
        studentsField = characterCardStudentTest.getClass().getDeclaredField("students");
        studentsField.setAccessible(true);

        studentsField.set(characterCardStudentTest, null);
    }

    /**
     * Test for the getter of the field "students"
     */
    @Test
    void getStudentTest() throws IllegalAccessException {
        studentsField.set(characterCardStudentTest, Arrays.copyOf(studentsTest, studentsTest.length));

        for (int i = 0; i < studentsTest.length; i++)
            if (studentsTest[i] != (characterCardStudentTest.getStudent())[i])
                throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for the setter of the field "students"
     */
    @Test
    void setStudentsTest() throws IllegalAccessException {
        //Set each student to the corresponding value in studentsTest
        for (int i = 0; i < studentsTest.length; i++) {
            characterCardStudentTest.setStudents(studentsTest[i], i);
            if (studentsTest[i] != ((Color[]) studentsField.get(characterCardStudentTest))[i])
                throw new AssertionError("Setter set wrong value");
        }

        //Check correctness at the end of multiple "set" calls
        for (int i = 0; i < studentsTest.length; i++) {
            if (studentsTest[i] != ((Color[]) studentsField.get(characterCardStudentTest))[i])
                throw new AssertionError("Setter set wrong value");
        }
    }
}
