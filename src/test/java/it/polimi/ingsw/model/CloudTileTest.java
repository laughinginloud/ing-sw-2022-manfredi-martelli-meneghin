package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Tests for class "CloudTile"
 * @author Mattia Martelli
 */
class CloudTileTest {
    private       CloudTile cloudTileTest;
    private       Field     studentsField;
    private final Color[]   globalTestSet = new Color[]{Color.RED, Color.YELLOW, Color.GREEN, Color.PINK};

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        cloudTileTest = new CloudTile();

        // Use reflection to get the private field "students" and change its visibility
        studentsField = cloudTileTest.getClass().getDeclaredField("students");
        studentsField.setAccessible(true);

        studentsField.set(cloudTileTest, Arrays.copyOf(globalTestSet, globalTestSet.length));
    }

    /**
     * Test correctness and multiple subsequent calls of the method getStudents
     */
    @Test
    void getStudentsSuccessionTest() {
        Color[] firstRetrieve  = cloudTileTest.getStudents();
        Color[] secondRetrieve = cloudTileTest.getStudents();

        if (!Arrays.equals(firstRetrieve, globalTestSet))
            throw new AssertionError("Returned students differ from the ones currently on the tile");

        if (Arrays.equals(secondRetrieve, new Color[4]))
            throw new AssertionError("Cloud tile wrongly emptied after return");

        if (!Arrays.equals(firstRetrieve, secondRetrieve))
            throw new AssertionError("Cloud tile filled with garbage data after return");
    }

    /**
     * Test correctness and multiple subsequent calls of the method retieveStudents
     */
    @Test
    void retreiveStudentsSuccessionTest() {
        Color[] firstRetrieve  = cloudTileTest.retrieveStudents();
        Color[] secondRetrieve = cloudTileTest.retrieveStudents();

        if (!Arrays.equals(firstRetrieve, globalTestSet))
            throw new AssertionError("Retrieved students differ from the ones currently on the tile");

        if (Arrays.equals(firstRetrieve, secondRetrieve))
            throw new AssertionError("Cloud tile not emptyied after retrieval");

        if (!Arrays.equals(secondRetrieve, new Color[4]))
            throw new AssertionError("Cloud tile filled with garbage data after retrieval");
    }

    /**
     * Test the correctness of the method setStudents
     */
    @Test
    void setStudentsTest() throws IllegalAccessException {
        Color[] testSet = new Color[]{Color.BLUE, Color.RED, Color.GREEN, Color.PINK};
        cloudTileTest.setStudents(testSet);

        Color[] studentValue = (Color[]) studentsField.get(cloudTileTest);

        if (Arrays.equals(globalTestSet, studentValue))
            throw new AssertionError("Attribute students not set");

        if (!Arrays.equals(testSet, studentValue))
            throw new AssertionError("Attribute students set to wrong value");
    }
}
