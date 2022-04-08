package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Tests for class "DiningRoom"
 * @author Mattia Martelli
 */
class DiningRoomTest {
    private DiningRoom diningRoomTest;
    private Field      studentCountersField;
    private int[]      testSet = {1,2,3,4,5};

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        diningRoomTest = new DiningRoom();

        // Use reflection to get the private field "studentCounters" and change its visibility
        studentCountersField = diningRoomTest.getClass().getDeclaredField("studentCounters");
        studentCountersField.setAccessible(true);

        studentCountersField.set(diningRoomTest, new int[Color.values().length]);
    }

    /**
     * Test for the getter of the field "studentCounters"
     */
    @Test
    void getStudentCountersTest() throws IllegalAccessException {
        studentCountersField.set(diningRoomTest, Arrays.copyOf(testSet, testSet.length));

        for (Color color : Color.values())
            if (testSet[color.ordinal()] != diningRoomTest.getStudentCounters(color))
                throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for both overloads of the setter method
     */
    @Test
    void setStudentCountersTest() throws IllegalAccessException {
        // Sets all counters independently and tries to retrieve the same value
        for (Color testColor : Color.values()) {
            diningRoomTest.setStudentCounters(testColor, testSet[testColor.ordinal()]);
            if (testSet[testColor.ordinal()] != ((int[]) studentCountersField.get(diningRoomTest))[testColor.ordinal()])
                throw new AssertionError("Independent setter set wrong value");
        }

        // Sets all counters to the first value of the test set and tries to retrieve all the values
        diningRoomTest.setStudentCounters(testSet[0]);
        int[] studentCountersTest = (int[]) studentCountersField.get(diningRoomTest);
        for (Color testColor : Color.values()) {
            if (studentCountersTest[testColor.ordinal()] != testSet[0])
                throw new AssertionError("Global setter set wrong value");
        }
    }
}
