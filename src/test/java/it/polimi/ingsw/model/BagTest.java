package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Test for class "Bag"
 * @author Sebastiano Meneghin
 */
class BagTest {
    private Bag bagTest;
    private Field instanceField;
    private Field studentCountersField;
    private int[] testSet = {1,2,3,4,5};

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        bagTest = Bag.getInstance();

        // Use reflection to get the private field "studentCounters" and change it visibility
        studentCountersField = bagTest.getClass().getDeclaredField("studentCounters");
        studentCountersField.setAccessible(true);

        studentCountersField.set(bagTest, new int[Color.values().length]);
    }

    @AfterEach
    void cleanUp() throws IllegalAccessException, NoSuchFieldException {
        //Use reflection to get the private field "instance" and change it visibility
        instanceField = bagTest.getClass().getDeclaredField("instance");
        instanceField.setAccessible(true);

        instanceField.set(bagTest, null);
    }

    /**
     * Test for the getter of the field "studentCounters"
     */
    @Test
    void getStudentCountersTest() throws IllegalAccessException {
        studentCountersField.set(bagTest, Arrays.copyOf(testSet, testSet.length));

        for (Color color : Color.values())
            if (testSet[color.ordinal()] != bagTest.getStudentCounters(color))
                throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for both overloads of the setter method of the field "studentCounters"
     */
    @Test
    void setStudentCountersTest() throws IllegalAccessException {
        // Sets all counters independently and tries to retrieve the same value
        for (Color testColor : Color.values()) {
            bagTest.setStudentCounters(testColor, testSet[testColor.ordinal()]);
            if (testSet[testColor.ordinal()] != ((int[]) studentCountersField.get(bagTest))[testColor.ordinal()])
                throw new AssertionError("Independent setter set wrong value");
        }

        // Sets all counters to the first value of the test set and tries to retrieve all the values
        bagTest.setStudentCounters(testSet[0]);
        int[] studentCountersTest = (int[]) studentCountersField.get(bagTest);
        for (Color testColor : Color.values()) {
            if (studentCountersTest[testColor.ordinal()] != testSet[0])
                throw new AssertionError("Global setter set wrong value");
        }
    }
}
