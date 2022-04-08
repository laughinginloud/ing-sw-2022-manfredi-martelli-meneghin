package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Tests for class "Entrance"
 * @author Martelli Mattia
 */
class EntranceTest {
    private Entrance entranceTest;
    private Field    studentsField;
    private Color[]  globalTestSet = new Color[]{Color.RED, Color.PINK, Color.GREEN, Color.GREEN, Color.BLUE};

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        entranceTest = new Entrance(globalTestSet.length);

        // Use reflection to get the private field "students" and change its visibility
        studentsField = entranceTest.getClass().getDeclaredField("students");
        studentsField.setAccessible(true);

        studentsField.set(entranceTest, Arrays.copyOf(globalTestSet, globalTestSet.length));
    }

    /**
     * Test correctness and multiple subsequent calls of the method getStudents
     */
    @Test
    void getStudentsTest() {
        Color[] firstRetrieve  = entranceTest.getStudents();
        Color[] secondRetrieve = entranceTest.getStudents();

        if (!Arrays.equals(firstRetrieve, globalTestSet))
            throw new AssertionError("Returned students differ from the ones currently on the tile");

        if (Arrays.equals(secondRetrieve, new Color[4]))
            throw new AssertionError("Cloud tile wrongly emptyied after return");

        if (!Arrays.equals(firstRetrieve, secondRetrieve))
            throw new AssertionError("Cloud tile filled with garbage data after return");
    }

    /**
     * Test correctness of the retrieve method
     */
    @Test
    void retrieveStudentTest() throws IllegalAccessException {
        Color[] testSet          = new Color[]{Color.RED, Color.GREEN, Color.GREEN, Color.BLUE, null};
        Color   retrievedStudent = entranceTest.retrieveStudent(1);

        if (retrievedStudent != Color.PINK)
            throw new AssertionError("Retrieved wrong student color");

        Color[] postRetrieveCounters = (Color[]) studentsField.get(entranceTest);

        if (!Arrays.equals(postRetrieveCounters, testSet))
            throw new AssertionError("Student not correctly removed during retrieval");
    }

    /**
     * Test correctness of the setter method
     */
    @Test
    void setStudentsTest() throws IllegalAccessException {
        Color[] testSet = new Color[]{Color.YELLOW, Color.PINK, Color.RED, Color.GREEN, Color.BLUE};
        entranceTest.setStudents(testSet);
        Color[] studentsValue = (Color[]) studentsField.get(entranceTest);

        if (Arrays.equals(studentsValue, globalTestSet))
            throw new AssertionError("Students not set");

        if (!Arrays.equals(studentsValue, testSet))
            throw new AssertionError("Students set to wrong value");
    }

    /**
     * Test correctness of the append method
     */
    @Test
    void appendStudentTest() throws IllegalAccessException {
        Color[] testSet = new Color[]{Color.RED, Color.PINK, Color.GREEN, Color.GREEN, null};
        studentsField.set(entranceTest, Arrays.copyOf(testSet, testSet.length));
        entranceTest.appendStudent(Color.BLUE);
        Color[] postAppendStudents = (Color[]) studentsField.get(entranceTest);

        if (Arrays.equals(postAppendStudents,testSet))
            throw new AssertionError("Nothing appended to students");

        if(!Arrays.equals(postAppendStudents, globalTestSet))
            throw new AssertionError("Wrong value appended to students");
    }
}
