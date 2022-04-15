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
    private Bag bagTest;
    private Field studentCountersField;
    private final int[] studentCountersTest = {1,2,3,4,5};

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        bagTest = new Bag();

        // Use reflection to get the private field "studentCounters" and change it visibility
        studentCountersField = bagTest.getClass().getDeclaredField("studentCounters");
        studentCountersField.setAccessible(true);

        studentCountersField.set(bagTest, new int[Color.values().length]);
    }

    /**
     * Test for the getter of the field "studentCounters"
     */
    @Test
    void getStudentCountersTest() throws IllegalAccessException {
        studentCountersField.set(bagTest, Arrays.copyOf(studentCountersTest, studentCountersTest.length));

        for (Color color : Color.values())
            if (studentCountersTest[color.ordinal()] != bagTest.getStudentCounters(color))
                throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for both overloads of the setter method of the field "studentCounters"
     */
    @Test
    void setStudentCountersTest() throws IllegalAccessException {
        // Sets all counters independently and tries to retrieve the same value
        for (Color color : Color.values()) {
            bagTest.setStudentCounters(color, studentCountersTest[color.ordinal()]);
            if (studentCountersTest[color.ordinal()] != ((int[]) studentCountersField.get(bagTest))[color.ordinal()])
                throw new AssertionError("Independent setter set wrong value");
        }

        // Sets all counters to the first value of the test set and tries to retrieve all the values
        bagTest.setStudentCounters(studentCountersTest[0]);
        int[] studentCountersTest = (int[]) studentCountersField.get(bagTest);
        for (Color color : Color.values()) {
            if (studentCountersTest[color.ordinal()] != studentCountersTest[0])
                throw new AssertionError("Global setter set wrong value");
        }
    }
}
