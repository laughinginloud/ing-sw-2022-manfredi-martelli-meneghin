package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Test for class "Bag"
 * @author Sebastiano Meneghin
 */
class BagTest {
    private       Bag   bagTest;
    private       Field studentCountersField;
    private final int[] studentCountersTest           = {1,2,3,4,5};
    private final int[] studentCountersTestSingleDraw = {1,0,0,0,0};
    private final int[] studentCountersTestDoubleDraw = {1,1,0,0,0};
    private       Field numOfStudentsField;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        bagTest = new Bag();

        // Use reflection to get the private field "studentCounters" and change it visibility
        studentCountersField = bagTest.getClass().getDeclaredField("studentCounters");
        studentCountersField.setAccessible(true);

        // Use reflection to get the private field "numOfStudent" and change it visibility
        numOfStudentsField = bagTest.getClass().getDeclaredField("numOfStudents");
        numOfStudentsField.setAccessible(true);

        studentCountersField.set(bagTest, new int[Color.values().length]);
        numOfStudentsField.set(bagTest, 0);
    }

    /**
     * Test for the getter of the field "studentCounters"
     */
    @Test
    void getStudentCountersTest() throws IllegalAccessException {
        studentCountersField.set(bagTest, Arrays.copyOf(studentCountersTest, studentCountersTest.length));

        for (Color color : Color.values()) {
            if (studentCountersTest[color.ordinal()] != bagTest.getStudentCounters(color))
                throw new AssertionError("Getter returned wrong value");
        }
    }

    /**
     * Test for both overloads of the setter method of the field "studentCounters"
     */
    @Test
    void setStudentCountersTest() throws IllegalAccessException {
        // Local variable used for checking the numOfStudents' update procedure
        int numOfAddedStudents = 0;

        // Sets all counters independently and tries to retrieve the same value
        for (Color color : Color.values()) {
            bagTest.setStudentCounters(color, studentCountersTest[color.ordinal()]);
            numOfAddedStudents = numOfAddedStudents + studentCountersTest[color.ordinal()];

            if (studentCountersTest[color.ordinal()] != ((int[]) studentCountersField.get(bagTest))[color.ordinal()])
                throw new AssertionError("Independent setter set wrong value");
        }

        if ((int) numOfStudentsField.get(bagTest) != numOfAddedStudents)
            throw new AssertionError("Setter did not update correctly the counter 'numOfStudents' ");


        // Sets all counters to the first value of the test set and tries to retrieve all the values
        numOfAddedStudents = 0;
        bagTest.setStudentCounters(studentCountersTest[0]);
        int[] studentCountersTest = (int[]) studentCountersField.get(bagTest);
        for (Color color : Color.values()) {
            numOfAddedStudents = numOfAddedStudents + studentCountersTest[color.ordinal()];

            if (studentCountersTest[color.ordinal()] != studentCountersTest[0])
                throw new AssertionError("Global setter set wrong value");
        }
    }


    /**
     * Test for both methods drawStudents of class "Bag"
     */
    @Test
    void drawStudentsTest() throws IllegalAccessException {
        BagResult drawResult;

        //Set a single counter to 1 and try a single draw
        studentCountersField.set(bagTest, Arrays.copyOf(studentCountersTestSingleDraw, studentCountersTestSingleDraw.length));
        numOfStudentsField.set(bagTest, studentCountersTestSingleDraw.length);

        try {
            drawResult = bagTest.drawStudents(1);
            if (drawResult.drawnStudents()[0] != Color.values()[0])
                throw new AssertionError("Single drawStudents drawn wrong student");

            if (drawResult.drawnStudents().length != 1)
                throw new AssertionError("Single drawStudents drawn more than one student");
        }
        catch (EmptyBagException e) {
            throw new AssertionError("Test has been implemented incorrectly");
        }


        //Set two different counter to 1 and try a double draw
        studentCountersField.set(bagTest, Arrays.copyOf(studentCountersTestDoubleDraw, studentCountersTestDoubleDraw.length));
        numOfStudentsField.set(bagTest, studentCountersTestDoubleDraw.length);

        try {
            drawResult = bagTest.drawStudents(2);
        }
        catch (EmptyBagException e) {
            throw new AssertionError("Test has been implemented incorrectly");
        }

        if ((drawResult.drawnStudents()[0] != Color.RED && drawResult.drawnStudents()[1] != Color.RED) || (drawResult.drawnStudents()[0] != Color.YELLOW && drawResult.drawnStudents()[1] != Color.YELLOW))
            throw new AssertionError("Double drawStudents drawn wrong students");
    }

}
