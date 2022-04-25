package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * Tests for class "SchoolBoard"
 * @author Mattia Martelli
 */
class SchoolBoardTest {
    private SchoolBoard schoolBoardTest;
    private Entrance    entranceTest;
    private DiningRoom  diningRoomTest;

    @BeforeEach
    void setUp() {
        entranceTest    = new Entrance(7);
        diningRoomTest  = new DiningRoom();
        schoolBoardTest = new SchoolBoard(TowerColor.BLACK, 5, entranceTest, diningRoomTest);
    }

    /**
     * Test the getter of the field towerColor
     */
    @Test
    void getTowerColorTest() {
        if (!schoolBoardTest.getTowerColor().equals(TowerColor.BLACK))
            throw new AssertionError("Returned wrong tower color");
    }

    /**
     * Test the getter of the field towerCount
     */
    @Test
    void getTowerCountTest() {
        if (schoolBoardTest.getTowerCount() != 5)
            throw new AssertionError("Returned wrong tower count");
    }

    /**
     * Test the setter of the field towerCount
     */
    @Test
    void setTowerCountTest() throws NoSuchFieldException, IllegalAccessException {
        schoolBoardTest.setTowerCount(3);

        Field towerCountField = schoolBoardTest.getClass().getDeclaredField("towerCount");
        towerCountField.setAccessible(true);
        int towerCountValue = (int) towerCountField.get(schoolBoardTest);

        if (towerCountValue == 5)
            throw new AssertionError("Setter did not set towerCount");

        if (towerCountValue != 3)
            throw new AssertionError("Setter set wrong towerCount");
    }

    /**
     * Test the getter of the field entrance
     */
    @Test
    void getEntranceTest() {
        if (schoolBoardTest.getEntrance() != entranceTest)
            throw new AssertionError("Returned wrong entrance");
    }

    /**
     * Test the getter of the field diningRoom
     */
    @Test
    void getDiningRoomTest() {
        if (schoolBoardTest.getDiningRoom() != diningRoomTest)
            throw new AssertionError("Returned wrong dining room");
    }
}
