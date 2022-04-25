package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Tests for class "Game Model"
 * @author Giovanni Manfredi
 */
class GameModelTest {
    private static final int     MAX_PLAYERS                = 4;
    private static final int     MAX_ISLANDS                = 12;
    private static final int     MAX_CLOUD_TILES            = 4;
    private static final int     MIN_MOTHER_NATURE_POSITION = 4;
    private static final boolean DEFAULT_EXPERT_MODE        = false;
    private static final int     CHARACTER_CARDS_NUMBER     = 3;

    private              GameModel            gameModelTest;
    private              Field                playersField;
    private static final Player[]             testPlayers = new Player[MAX_PLAYERS];
    private              Field                islandsField;
    private static final Island[]             testIslands = new Island[MAX_ISLANDS];
    private              Field                cloudTilesField;
    private static final CloudTile[]          testCloudTiles = new CloudTile[MAX_CLOUD_TILES];
    private              Field                motherNaturePositionField;
    private              Field                bagField;
    private static final Bag                  testBag = new Bag();
    private              Field                globalProfessorTableField;
    private static final GlobalProfessorTable testGlobalProfessorTable = new GlobalProfessorTable();
    private              Field                expertModeField;
    private              Field                characterCardsField;
    private static final CharacterCard[]      testCharacterCards = new CharacterCard[CHARACTER_CARDS_NUMBER];
    private              Field                coinPoolField;
    private        final int                  testCoinPool = 0;

    @BeforeAll
    static void dataInitialization() {
        for (int i = 0; i < testPlayers.length; ++i)
            testPlayers[i] = new Player(i, null, null, null);

        for (int i = 0; i < testIslands.length; ++i)
            testIslands[i] = new Island();

        for (int i = 0; i < testCloudTiles.length; ++i)
            testCloudTiles[i] = new CloudTile();

        for (int i = 0; i < testCharacterCards.length; ++i)
            testCharacterCards[i] = new CharacterCard(i);
    }

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        gameModelTest = new GameModel(MAX_PLAYERS, true);

        // Use reflection to get the private fields, change their visibilities and set test values
        playersField = gameModelTest.getClass().getDeclaredField("players");
        playersField.setAccessible(true);
        playersField.set(gameModelTest, new Player[MAX_PLAYERS]);

        islandsField = gameModelTest.getClass().getDeclaredField("islands");
        islandsField.setAccessible(true);
        islandsField.set(gameModelTest, new Island[MAX_ISLANDS]);

        cloudTilesField = gameModelTest.getClass().getDeclaredField("cloudTiles");
        cloudTilesField.setAccessible(true);
        cloudTilesField.set(gameModelTest, new CloudTile[MAX_CLOUD_TILES]);

        motherNaturePositionField = gameModelTest.getClass().getDeclaredField("motherNaturePosition");
        motherNaturePositionField.setAccessible(true);
        motherNaturePositionField.set(gameModelTest, MIN_MOTHER_NATURE_POSITION);

        bagField = gameModelTest.getClass().getDeclaredField("bag");
        bagField.setAccessible(true);
        bagField.set(gameModelTest, testBag);

        globalProfessorTableField = gameModelTest.getClass().getDeclaredField("globalProfessorTable");
        globalProfessorTableField.setAccessible(true);
        globalProfessorTableField.set(gameModelTest, testGlobalProfessorTable);

        expertModeField = gameModelTest.getClass().getDeclaredField("expertMode");
        expertModeField.setAccessible(true);
        expertModeField.set(gameModelTest, DEFAULT_EXPERT_MODE);

        characterCardsField = gameModelTest.getClass().getDeclaredField("characterCards");
        characterCardsField.setAccessible(true);
        characterCardsField.set(gameModelTest, new CharacterCard[CHARACTER_CARDS_NUMBER]);

        coinPoolField = gameModelTest.getClass().getDeclaredField("coinPool");
        coinPoolField.setAccessible(true);
        coinPoolField.set(gameModelTest, null);
    }

    /**
     * Test for getter of the field "player"
     */
    @Test
    void getPlayerTest() throws IllegalAccessException {
        // Gets each player of players[] and checks if they are the same as in testPlayers
        playersField.set(gameModelTest, Arrays.copyOf(testPlayers, testPlayers.length));

        for (int i = 0; i < MAX_PLAYERS; i++)
            if (testPlayers[i] != gameModelTest.getPlayer(i))
                throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for setter of the field "player"
     */
    @Test
    void setPlayerTest() throws IllegalAccessException {
        // Sets all players independently and tries to retrieve the same value
        for (int i = 0; i < MAX_PLAYERS; i++) {
            gameModelTest.setPlayer(testPlayers[i],i);
            if (!testPlayers[i].equals(((Player[]) playersField.get(gameModelTest))[i]))
                throw new AssertionError("Independent setter set wrong value");
        }
    }

    /**
     * Test for getter of the field "playersCount"
     */
    @Test
    void getPlayersCountTest() {
        // Gets the playersCount and checks if it's they same as set in BeforeEach
        if (gameModelTest.getPlayersCount() != MAX_PLAYERS)
            throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for getter of the field "islands"
     */
    @Test
    void getIslandTest() throws IllegalAccessException {
        islandsField.set(gameModelTest, Arrays.copyOf(testIslands, testIslands.length));
        for (int i = 0; i < MAX_ISLANDS; i++)
            if (testIslands[i] != gameModelTest.getIsland(i))
                throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for setter of the field "island"
     */
    @Test
    void setIslandTest() throws  IllegalAccessException {
        for (int i = 0; i < MAX_ISLANDS; i++) {
            gameModelTest.setIsland(testIslands[i], i);
            if (!testIslands[i].equals(((Island[]) islandsField.get(gameModelTest))[i]))
                throw new AssertionError("Independent setter set wrong value");
        }
    }

    /**
     * Test for the method "shiftIslands"
     */
    @Test
    void shiftIslandsTest() throws IllegalAccessException {
        islandsField.set(gameModelTest, Arrays.copyOf(testIslands, testIslands.length));

        gameModelTest.shiftIslands(0);

        Island[] islandsValue = (Island[]) islandsField.get(gameModelTest);

        if (islandsValue.length == testIslands.length)
            throw new AssertionError("Islands array not shortened");

        if (islandsValue[0].equals(testIslands[0]))
            throw new AssertionError("Island not removed");
    }

    /**
     * Test for getter of the field "islandCount"
     */
    @Test
    void getIslandCountTest() {
        // Gets the islandsCount and checks if it's they same as set in BeforeEach
        if (gameModelTest.getIslandsCount() != MAX_ISLANDS)
            throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for getter of the field "cloudTiles"
     */
    @Test
    void getCloudTileTest() throws IllegalAccessException {
        cloudTilesField.set(gameModelTest, Arrays.copyOf(testCloudTiles, testCloudTiles.length));
        for (int i = 0; i < MAX_CLOUD_TILES; i++)
            if (testCloudTiles[i] != gameModelTest.getCloudTile(i))
                throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for setter of the field "cloudTiles"
     */
    @Test
    void setCloudTileTest() throws  IllegalAccessException {
        for (int i = 0; i < MAX_CLOUD_TILES; i++) {
            gameModelTest.setCloudTile(testCloudTiles[i], i);
            if (!testCloudTiles[i].equals(((CloudTile[]) cloudTilesField.get(gameModelTest))[i]))
                throw new AssertionError("Independent setter set wrong value");
        }
    }

    /**
     * Test for getter of the field "motherNaturePosition"
     */
    @Test
    void getMotherNaturePositionTest() {
        if (gameModelTest.getMotherNaturePosition() != MIN_MOTHER_NATURE_POSITION)
            throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for setter of the field "motherNaturePosition"
     */
    @Test
    void setMotherNaturePositionTest() throws IllegalAccessException {
        gameModelTest.setMotherNaturePosition(11);
        if (!motherNaturePositionField.get(gameModelTest).equals(11))
            throw new AssertionError("Setter returned wrong value");
    }

    /**
     * Test for getter of the field "bag"
     */
    @Test
    void getBagTest() {
        if (!gameModelTest.getBag().equals(testBag))
            throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for setter of the field "bag"
     */
    @Test
    void setBagTest() throws IllegalAccessException {
        Bag tmp = new Bag();
        gameModelTest.setBag(tmp);
        if (!bagField.get(gameModelTest).equals(tmp))
            throw new AssertionError("Setter returned wrong value");
    }

    /**
     * Test for getter of the field "globalProfessorTable"
     */
    @Test
    void getGlobalProfessorTableTest() {
        if (!gameModelTest.getGlobalProfessorTable().equals(testGlobalProfessorTable))
            throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for setter of the field "globalProfessorTable"
     */
    @Test
    void setGlobalProfessorTableTest() throws IllegalAccessException {
        GlobalProfessorTable tmp = new GlobalProfessorTable();
        gameModelTest.setGlobalProfessorTable(tmp);
        if (!globalProfessorTableField.get(gameModelTest).equals(tmp))
            throw new AssertionError("Setter returned wrong value");
    }

    /**
     * Test for getter of the field "expertMode"
     */
    @Test
    void getExpertModeTest() {
        if (gameModelTest.getExpertMode() != DEFAULT_EXPERT_MODE)
            throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for setter of the field "expertMode"
     */
    @Test
    void setExpertModeTest() throws IllegalAccessException {
        gameModelTest.setExpertMode(true);
        if (!expertModeField.get(gameModelTest).equals(true))
            throw new AssertionError("Setter returned wrong value");
    }

    /**
     * Test for getter of the field "characterCards"
     */
    @Test
    void getCharacterCardsTest() throws IllegalAccessException {
        characterCardsField.set(gameModelTest, Arrays.copyOf(testCharacterCards, testCharacterCards.length));

        for (int i = 0; i < CHARACTER_CARDS_NUMBER; i++)
            if (testCharacterCards[i] != gameModelTest.getCharacterCard(i))
                throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for setter of the field "characterCards"
     */
    @Test
    void setCharacterCardTest() throws IllegalAccessException {
        for (int i = 0; i < CHARACTER_CARDS_NUMBER; i++) {
            gameModelTest.setCharacterCard(testCharacterCards[i],i);
            if (!testCharacterCards[i].equals(((CharacterCard[]) characterCardsField.get(gameModelTest))[i]))
                throw new AssertionError("Independent setter set wrong value");
        }
    }

    /**
     * Test for getter of the field "coinPool"
     */
    @Test
    void getCoinPoolTest() throws IllegalAccessException {
        if (gameModelTest.getCoinPool() != null)
            throw new AssertionError("Getter returned wrong value");

        coinPoolField.set(gameModelTest, testCoinPool);
        if (gameModelTest.getCoinPool() != testCoinPool)
            throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for setter of the field "coinPool"
     */
    @Test
    void setCoinPoolTest() throws IllegalAccessException {
        gameModelTest.setCoinPool(testCoinPool);
        if ((int) coinPoolField.get(gameModelTest) != testCoinPool)
            throw new AssertionError("Setter returned wrong value");
    }
}
