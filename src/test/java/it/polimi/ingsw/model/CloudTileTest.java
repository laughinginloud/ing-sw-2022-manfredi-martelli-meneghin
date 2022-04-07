package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

class CloudTileTest {
    private CloudTile cloudTileTest;
    private Color[]   globalTestArray;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        cloudTileTest   = new CloudTile();
        globalTestArray = new Color[]{Color.RED, Color.YELLOW, Color.GREEN, Color.PINK};

        // Use reflection to get the private field "students" and change its visibility
        Field studentField = cloudTileTest.getClass().getDeclaredField("students");
        studentField.setAccessible(true);

        // Set the field to a predetermined test value
        studentField.set(cloudTileTest, Arrays.copyOf(globalTestArray, globalTestArray.length));
    }

    /**
     * Test that checks multiple subsequent calls of the method getStudents
     */
    @Test
    void getStudentsSuccessionTest() {
        Color[] firstRetrieve  = cloudTileTest.getStudents();
        Color[] secondRetrieve = cloudTileTest.getStudents();

        if (Arrays.equals(secondRetrieve, new Color[4]))
            throw new AssertionError("Cloud tile wrongly emptyied after return");

        if (!Arrays.equals(firstRetrieve, secondRetrieve))
            throw new AssertionError("Cloud tile filled with garbage data after return");
    }

    /**
     * Test that checks the correctness of the result of a single call of the method getStudents
     */
    @Test
    void getStudentsCorrectTest() {
        if (!Arrays.equals(cloudTileTest.getStudents(), globalTestArray))
            throw new AssertionError("Returned students differ from the ones currently on the tile");
    }

    /**
     * Test that checks multiple subsequent calls of the method retieveStudents
     */
    @Test
    void retreiveStudentsSuccessionTest() {
        Color[] firstRetrieve  = cloudTileTest.retrieveStudents();
        Color[] secondRetrieve = cloudTileTest.retrieveStudents();

        if (Arrays.equals(firstRetrieve, secondRetrieve))
            throw new AssertionError("Cloud tile not emptyied after retrieval");

        if (!Arrays.equals(secondRetrieve, new Color[4]))
            throw new AssertionError("Cloud tile filled with garbage data after retrieval");
    }

    /**
     * Test that checks the correctness of the result of a single call of the method retrieveStudents
     */
    @Test
    void retreiveStudentsCorrectTest() {
        if (!Arrays.equals(cloudTileTest.retrieveStudents(), globalTestArray))
            throw new AssertionError("Retrieved students differ from the ones that were on the tile");
    }

    /**
     * Test that checks the correctness of the method setStudents
     */
    @Test
    void setStudentsTest() throws NoSuchFieldException, IllegalAccessException {
        Color[] testSet = new Color[]{Color.BLUE, Color.RED, Color.GREEN, Color.PINK};
        cloudTileTest.setStudents(testSet);

        // Use reflection to get the private field "students" and change its visibility
        Field studentField = cloudTileTest.getClass().getDeclaredField("students");
        studentField.setAccessible(true);
        Color[] studentValue = (Color[]) studentField.get(cloudTileTest);

        if (Arrays.equals(globalTestArray, studentValue))
            throw new AssertionError("Attribute students not set");

        if (!Arrays.equals(testSet, studentValue))
            throw new AssertionError("Attribute students set to wrong value");
    }
}
