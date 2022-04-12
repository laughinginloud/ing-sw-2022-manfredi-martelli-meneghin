package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Tests for class "Game Model"
 * @author Giovanni Manfredi
 */
class GameModelTest {
    private static final int MAX_PLAYERS = 4;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_ISLANDS = 12;
    private static final int MIN_MOTHERNATUREPOSITION = 4;
    private static final int MAX_COINPOOL = 20;
    private static final boolean DEFAULT_MODE = false;

    private GameModel gameModelTest;
    private Field instanceField;
    private Field playersField;
    //Using the constructor method of Player Class: Player(id, username, wizard, assistantDeck)
    private Player[] testPlayers = {new Player(10, null, null, null), new Player(20, null, null, null), new Player(30, null, null, null), new Player(40, null, null, null), new Player(50, null, null, null)};
    private Field playersCountField;
    private int testPlayersCount = 4;
    private Field islandsField;
    //Using the constructor method of Island Class: Island(studentCounters, multiplicity, towerColor, noEntryTile*, backgroundID)
    private Island testIsland = new Island(null, 1, null, null, null);
    private int testIndex = 0;
    private Field islandsCountField;
    private int testIslandsCount = 12;
    private Field motherNaturePositionField;
    private int testMotherNaturePosition = 11;
    private Field coinPoolField;
    private int testCoinPool = 0;
    private Field expertModeField;
    private boolean testExpertMode = true;


    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        gameModelTest = GameModel.getInstance();

        // Use reflection to get the private field "players" and change its visibility
        playersField = gameModelTest.getClass().getDeclaredField("players");
        playersField.setAccessible(true);

        playersField.set(gameModelTest, Arrays.copyOf(testPlayers, testPlayers.length));

        // Use reflection to get the private field "playersCount" and change its visibility
        playersCountField = gameModelTest.getClass().getDeclaredField("playersCount");
        playersCountField.setAccessible(true);

        playersCountField.set(gameModelTest, MIN_PLAYERS);

        // Use reflection to get the private field "islands" and change its visibility
        islandsField = gameModelTest.getClass().getDeclaredField("islands");
        islandsField.setAccessible(true);

        islandsField.set(gameModelTest,new Island[MAX_ISLANDS]);

        // Use reflection to get the private field "islandsCount" and change its visibility
        islandsCountField = gameModelTest.getClass().getDeclaredField("islandsCount");
        islandsCountField.setAccessible(true);

        islandsCountField.set(gameModelTest, MAX_ISLANDS);

        // Use reflection to get the private field "motherNaturePosition" and change its visibility
        motherNaturePositionField = gameModelTest.getClass().getDeclaredField("motherNaturePosition");
        motherNaturePositionField.setAccessible(true);

        motherNaturePositionField.set(gameModelTest, MIN_MOTHERNATUREPOSITION);

        // Use reflection to get the private field "coinPool" and change its visibility
        coinPoolField = gameModelTest.getClass().getDeclaredField("coinPool");
        coinPoolField.setAccessible(true);

        coinPoolField.set(gameModelTest, MAX_COINPOOL);

        // Use reflection to get the private field "expertMode" and change its visibility
        expertModeField = gameModelTest.getClass().getDeclaredField("expertMode");
        expertModeField.setAccessible(true);

        expertModeField.set(gameModelTest, DEFAULT_MODE);
    }

    @AfterEach
    void cleanUp() throws IllegalAccessException, NoSuchFieldException {
        //Use reflection to get the private field "instance" and change it visibility
        instanceField = gameModelTest.getClass().getDeclaredField("instance");
        instanceField.setAccessible(true);

        instanceField.set(gameModelTest, null);
    }

    /**
     * Test for getter of the field "player"
     */
    @Test
    void getPlayerTest() throws IllegalAccessException {
        // Gets each player of players[] and checks if they are the same as in testPlayers
        Player tmpPlayer;
        for (int i = 0; i < MAX_PLAYERS; i++){
            tmpPlayer = gameModelTest.getPlayer(i);
            if ( tmpPlayer == null || tmpPlayer.getPlayerID() != testPlayers[i].getPlayerID() ){
                throw new AssertionError("Getter returned wrong value");
            }
        }
    }

    /**
     * Test for setter of the field "player"
     */
    @Test
    void setPlayerTest() throws IllegalAccessException {
        // Sets

    }

    /**
     * Test for getter of the field "islands"
     */
    @Test
    void getIslandTest() throws IllegalAccessException {
        // Gets

    }

    /**
     * Test for setter of the field "island"
     */
    @Test
    void setIslandTest() throws  IllegalAccessException {
        // Sets
    }

    /**
     * Test for the method "shiftIslands"
     */
    @Test
    void shiftIslands() throws IllegalAccessException {
        // Shifts
    }

    /**
     * Test for getter of the field "islandCount"
     */
    @Test
    void getIslandCountTest() throws IllegalAccessException {
        // Gets
    }

    /**
     * Test for setter of the field "islandCount"
     */
    @Test
    void setIslandCountTest() throws IllegalAccessException {
        // Sets
    }

    /**
     * Test for getter of the field "motherNaturePosition"
     */
    @Test
    void getMotherNaturePositionTest() throws IllegalAccessException {
        // Gets
    }

    /**
     * Test for setter of the field "motherNaturePosition"
     */
    @Test
    void setMotherNaturePositionTest() throws IllegalAccessException {
        // Sets
    }

    /**
     * Test for getter of the field "coinPool"
     */
    @Test
    void getCoinPoolTest() throws IllegalAccessException {
        // Gets
    }

    /**
     * Test for setter of the field "coinPool"
     */
    @Test
    void setCoinPoolTest() throws IllegalAccessException {
        // Sets
    }

    /**
     * Test for getter of the field "expertMode"
     */
    @Test
    void getExpertModeTest() throws IllegalAccessException {
        // Gets
    }

    /**
     * Test for setter of the field "expertMode"
     */
    @Test
    void setExpertModeTest() throws IllegalAccessException {
        // Sets
    }
}
