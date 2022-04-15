package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * Test for class "GlobalProfessorTable"
 * @author Sebastiano Meneghin
 */
class GlobalProfessorTableTest {
    private GlobalProfessorTable globalProfessorTableTest;
    private static Player[] professorLocationsTest = new Player[5];
    private Field professorLocationsField;

    @BeforeAll
    static void dataInitialization() {
        for (int i = 0; i < professorLocationsTest.length; ++i)
            professorLocationsTest[i] = new Player((i + 1) * 10, null, null, null);
    }

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        globalProfessorTableTest = new GlobalProfessorTable();

        // Use reflection to get the private field "professorLocations" and change it visibility
        professorLocationsField = globalProfessorTableTest.getClass().getDeclaredField("professorLocations");
        professorLocationsField.setAccessible(true);

        professorLocationsField.set(globalProfessorTableTest, new Player[Color.values().length]);
    }

    /**
     * Test for the getter of the field "professorLocations"
     */
    @Test
    void getProfessorLocationsTest() throws IllegalAccessException {
        Player[] tempPlayer = new Player[Color.values().length];

        for (Color color : Color.values())
            tempPlayer[color.ordinal()] = professorLocationsTest[color.ordinal()];

        professorLocationsField.set(globalProfessorTableTest, tempPlayer);

        for (Color colorTest : Color.values())
            if (professorLocationsTest[colorTest.ordinal()] != globalProfessorTableTest.getProfessorLocation(colorTest))
                throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for the setter of the field "professorLocations"
     */
    @Test
    void setProfessorLocationTest() throws IllegalAccessException {
        // Sets the location of a specific professor to a value
        for (Color testColor : Color.values()) {
            globalProfessorTableTest.setProfessorLocation(testColor, professorLocationsTest[testColor.ordinal()]);
            if (professorLocationsTest[testColor.ordinal()] != ((Player[]) professorLocationsField.get(globalProfessorTableTest))[testColor.ordinal()])
                throw new AssertionError("Setter set wrong value");
        }

        // Check the correctness of multiple consequential calls of setProfessorLocation
        for (Color testColor : Color.values())
            if (professorLocationsTest[testColor.ordinal()] != ((Player[]) professorLocationsField.get(globalProfessorTableTest))[testColor.ordinal()])
                throw new AssertionError("Setter set wrong value");
    }
}
