package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Test for class "Island"
 * @author Sebastiano Meneghin
 */
public class IslandTest {
    private              Island        islandTest;
    private              Field         studentCountersField;
    private        final int[]         studentCountersTest = new int[]{1, 2, 3, 4, 5};
    private              Field         multiplicityField;
    private static final int           multiplicityTest = 3;
    private              Field         towerColorField;
    private        final TowerColor    towerColorTest = TowerColor.GREY;
    private              Field         noEntryTileCountField;
    private        final int           noEntryTileCountTest = 1;
    private              Field         backgroundIDField;
    private static       List<Integer> backgroundIDTest;

    @BeforeAll
    static void dataInitialization() {
        backgroundIDTest = new ArrayList<>();
        for (int i = 0; i < multiplicityTest; i++)
            backgroundIDTest.add(i % 4);
    }

    //Consider initial settings present in the constructor method
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        islandTest = new Island();

        // Use reflection to get the following private fields change their visibility
        studentCountersField = islandTest.getClass().getDeclaredField("studentCounters");
        studentCountersField.setAccessible(true);

        multiplicityField = islandTest.getClass().getDeclaredField("multiplicity");
        multiplicityField.setAccessible(true);

        towerColorField = islandTest.getClass().getDeclaredField("towerColor");
        towerColorField.setAccessible(true);

        noEntryTileCountField = islandTest.getClass().getDeclaredField("noEntryTileCount");
        noEntryTileCountField.setAccessible(true);

        backgroundIDField = islandTest.getClass().getDeclaredField("backgroundID");
        backgroundIDField.setAccessible(true);

        //Use reflection to reset the following private fields
        studentCountersField.set(islandTest, new int[Color.values().length]);
        backgroundIDField.set(islandTest, new ArrayList<Integer>());
    }

    /**
     * Test for the getter of the field "studentCounters"
     */
    @Test
    void getStudentCounterTest() throws IllegalAccessException {
        studentCountersField.set(islandTest, Arrays.copyOf(studentCountersTest, studentCountersTest.length));

        for (Color color : Color.values())
            if (studentCountersTest[color.ordinal()] != islandTest.getStudentCounters(color))
                throw new AssertionError("Getter of studentCounters returned wrong value");
    }

    /**
     * Test for both the setter of the field "studentCounters"
     */
    @Test
    void setStudentCounterTest() throws IllegalAccessException {
        // Sets all counters independently and tries to retrieve the same value
        for (Color color : Color.values()) {
            islandTest.setStudentCounters(color, studentCountersTest[color.ordinal()]);
            if (studentCountersTest[color.ordinal()] != ((int[]) studentCountersField.get(islandTest))[color.ordinal()]) {
                throw new AssertionError("Independent setter of studentCounters returned wrong value");
            }
        }

        // Sets all counters to the first value of the test set and tries to retrieve all the values
        islandTest.setStudentCounters(studentCountersTest[0]);
        int[] studentCountersTest = (int[]) studentCountersField.get(islandTest);
        for (Color color : Color.values()) {
            if (studentCountersTest[color.ordinal()] != studentCountersTest[0]) {
                throw new AssertionError("Global setter of studentCounters returned wrong value");
            }
        }
    }

    /**
     * Test for the getter of the field "multiplicity"
     */
    @Test
    void getMultiplicityTest() throws IllegalAccessException {
        multiplicityField.set(islandTest, multiplicityTest);

        if (multiplicityTest != islandTest.getMultiplicity())
            throw new AssertionError("Getter of multiplicity returned wrong value");
    }

    /**
     * Test for the setter of the field "multiplicity"
     */
    @Test
    void setMultiplicityTest() throws IllegalAccessException {
        islandTest.setMultiplicity(multiplicityTest);

        if (multiplicityTest != ((int) multiplicityField.get(islandTest)))
            throw new AssertionError("Setter of multiplicity returned wrong value");
    }

    /**
     * Test for the getter of the field "towerColor"
     */
    @Test
    void getTowerColorTest() throws IllegalAccessException {
        towerColorField.set(islandTest, towerColorTest);

        if (towerColorTest != islandTest.getTowerColor())
            throw new AssertionError("Getter of towerColor returned wrong value");
    }

    /**
     * Test for the setter of the field "towerColor"
     */
    @Test
    void setTowerColorTest() throws IllegalAccessException {
        islandTest.setTowerColor(towerColorTest);

        if (towerColorTest != towerColorField.get(islandTest))
            throw new AssertionError("Setter of towerColor returned wrong value");
    }

    /**
     * Test for the getter of the field "noEntryTileCount"
     */
    @Test
    void getNoEntryTileCountTest() throws IllegalAccessException {
        noEntryTileCountField.set(islandTest, Optional.of(noEntryTileCountTest));

        if (islandTest.getNoEntryTileCount().equals(Optional.empty()))
            throw new AssertionError("Getter of noEntryTileCount returned wrongly a null value");

        if (!islandTest.getNoEntryTileCount().equals(Optional.of(noEntryTileCountTest)))
            throw new AssertionError("Getter of noEntryTileCount returned wrong value");
    }

    /**
     * Test for the setter of the field "noEntryTileCount"
     */
    @Test
    void setNoEntryTileCountTest() throws IllegalAccessException {
        islandTest.setNoEntryTileCount(noEntryTileCountTest);

        if (!(noEntryTileCountField.get(islandTest)).equals(Optional.of(noEntryTileCountTest)))
            throw new AssertionError("Setter of noEntryTileCount returned wrong value");
    }

    /**
     * Test for the getter of the field "backgroundID"
     */
    @Test
    void getBackgroundIDTest() throws IllegalAccessException {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < multiplicityTest; i++)
            temp.add(backgroundIDTest.get(i));

        backgroundIDField.set(islandTest, temp);

        for (int i = 0; i < multiplicityTest; i++)
            if (backgroundIDTest.get(i) != islandTest.getBackgroundID()[i])
                throw new AssertionError("Getter of backgroundID returned wrong value");
    }

    /**
     * Test for both the setter of the field "backgroundID"
     */
    @Test
    void addBackgroundIDTest() throws IllegalAccessException {
        //Add multiple backgroundIDs to the backgroundID's list
        for (int i = 0; i < multiplicityTest; i++)
            islandTest.addBackgroundID(backgroundIDTest.get(i));

        @SuppressWarnings("unchecked")
        List<Integer> backgroundIDValue = (ArrayList<Integer>) backgroundIDField.get(islandTest);

        //Check at the end
        for (int i = 0; i < multiplicityTest; i++)
            if (!Objects.equals(backgroundIDTest.get(i), backgroundIDValue.get(i)))
                throw new AssertionError("Adder of backgroundID added value in wrong position");

        //Problem with different lengths
        if (backgroundIDValue.size() != backgroundIDTest.size())
            throw new AssertionError("Adder of backgroundID added too many/not enough elements");
    }
}
