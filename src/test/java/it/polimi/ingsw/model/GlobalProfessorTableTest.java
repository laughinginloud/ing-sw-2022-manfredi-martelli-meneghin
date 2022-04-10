package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Test for class "GlobalProfessorTable"
 * @author Sebastiano Meneghin
 */
class GlobalProfessorTableTest {
    private GlobalProfessorTable globalProfessorTableTest;
    private Field instanceField;
    //Using the constructor method of Player Class: Player(id, username, wizard, assistantDeck)
    private Player[] testPlayers = {new Player(10, null, null, null), new Player(20, null, null, null), new Player(30, null, null, null), new Player(40, null, null, null), new Player(50, null, null, null)};
    private Field professorLocationsField;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        globalProfessorTableTest = GlobalProfessorTable.getInstance();

        // Use reflection to get the private field "professorLocations" and change it visibility
        professorLocationsField = globalProfessorTableTest.getClass().getDeclaredField("professorLocations");
        professorLocationsField.setAccessible(true);

        professorLocationsField.set(globalProfessorTableTest, new Player[Color.values().length]);
    }

    @AfterEach
    void cleanUp() throws IllegalAccessException, NoSuchFieldException {
        //Use reflection to get the private field "instance" and change it visibility
        instanceField = globalProfessorTableTest.getClass().getDeclaredField("instance");
        instanceField.setAccessible(true);

        instanceField.set(globalProfessorTableTest, null);
    }

    /**
     * Test for the getter of the field "professorLocations"
     */
    @Test
    void getProfessorLocationsTest() throws IllegalAccessException {
        Player[] tempPlayer = new Player [Color.values().length];

        for (Color color :  Color.values())
            tempPlayer[color.ordinal()] = testPlayers[color.ordinal()];

        professorLocationsField.set(globalProfessorTableTest, tempPlayer);

        for (Color colorTest : Color.values())
            if (testPlayers[colorTest.ordinal()] != globalProfessorTableTest.getProfessorLocation(colorTest))
                throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for the setter of the field "professorLocations"
     */
    @Test
    void setProfessorLocationTest() throws IllegalAccessException {
        // Sets the location of a specific professor to a value
        for (Color testColor : Color.values()) {
            globalProfessorTableTest.setProfessorLocation(testColor, testPlayers[testColor.ordinal()]);
            if (testPlayers[testColor.ordinal()] != ((Player[]) professorLocationsField.get(globalProfessorTableTest))[testColor.ordinal()])
                throw new AssertionError("Setter set wrong value");
        }
    }
}
