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
    // Constants used in the testing
    private static final int     MAX_PLAYERS                = 4;
    private static final int     MAX_ISLANDS                = 12;
    private static final int     MAX_CLOUD_TILES            = 4;
    private static final int     MIN_MOTHER_NATURE_POSITION = 0;
    private static final int     MAX_MOTHER_NATURE_POSITION = 11;
    private static final boolean DEFAULT_EXPERT_MODE        = false;
    private static final int     CHARACTER_CARDS_NUMBER     = 3;

    // Field and testSets declaration
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
    private              Field                characterCardsField;
    private static final CharacterCard[]      testCharacterCards = new CharacterCard[CHARACTER_CARDS_NUMBER];
    private              Field                coinPoolField;
    private        final Integer              testCoinPool = 0;

    @BeforeAll
    static void dataInitialization() {
        // Initialization of the testSets arrays used
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
        gameModelTest = new GameModel(MAX_PLAYERS, DEFAULT_EXPERT_MODE);

        // Use of reflection to get the private fields, change their visibilities and set test values
        // This is repeated for each Field that needs to be tested
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

        characterCardsField = gameModelTest.getClass().getDeclaredField("characterCards");
        characterCardsField.setAccessible(true);
        characterCardsField.set(gameModelTest, new CharacterCard[CHARACTER_CARDS_NUMBER]);

        coinPoolField = gameModelTest.getClass().getDeclaredField("coinPool");
        coinPoolField.setAccessible(true);
        coinPoolField.set(gameModelTest, null);
    }

    /**
     * Test for the getter of an element (Player) of the array of Player from the field "players"
     */
    @Test
    void getPlayerTest() throws IllegalAccessException {
        // Sets playersField with testPlayers using the method set (library method)
        playersField.set(gameModelTest, Arrays.copyOf(testPlayers, testPlayers.length));

        // Gets each Player of Players[] independently and checks if they are the same as in testPlayers
        for (int i = 0; i < MAX_PLAYERS; ++i)
            if (testPlayers[i] != gameModelTest.getPlayer(i))
                throw new AssertionError("getPlayer returned wrong value");
    }

    /**
     * Test for the setter of and element (Player) of the array of Player from the field "player"
     */
    @Test
    void setPlayerTest() throws IllegalAccessException {
        // Sets all players independently and tries to retrieve the same value using the method get (library method)
        for (int i = 0; i < MAX_PLAYERS; ++i) {
            gameModelTest.setPlayer(testPlayers[i],i);
            if (!testPlayers[i].equals(((Player[]) playersField.get(gameModelTest))[i]))
                throw new AssertionError("Independent setPlayer set wrong value");
        }
    }

    /**
     * Test for the method which gets the length of the field "players"
     */
    @Test
    void getPlayersCountTest() {
        // Gets the length of the array players and checks if it's they same as the array set in BeforeEach
        if (gameModelTest.getPlayersCount() != MAX_PLAYERS)
            throw new AssertionError("getPlayersCount returned wrong value");
    }

    /**
     * Test for the getter of an element (Island) of the array of Island from the field "islands"
     */
    @Test
    void getIslandTest() throws IllegalAccessException {
        // Sets islandsField with testIslands using the method set (library method)
        islandsField.set(gameModelTest, Arrays.copyOf(testIslands, testIslands.length));

        // Gets each Island of Island[] independently and checks if they are the same as in testIslands
        for (int i = 0; i < MAX_ISLANDS; ++i)
            if (testIslands[i] != gameModelTest.getIsland(i))
                throw new AssertionError("getIsland returned wrong value");
    }

    /**
     * Test for the setter of and element (Island) of the array of Island from the field "islands"
     */
    @Test
    void setIslandTest() throws  IllegalAccessException {
        // Sets all islands independently and tries to retrieve the same value using the method get (library method)
        for (int i = 0; i < MAX_ISLANDS; ++i) {
            gameModelTest.setIsland(testIslands[i], i);
            if (!testIslands[i].equals(((Island[]) islandsField.get(gameModelTest))[i]))
                throw new AssertionError("Independent setIsland set wrong value");
        }
    }

    /**
     * Test for the method "shiftIslands"
     */
    @Test
    void shiftIslandsTest() throws IllegalAccessException {
        // Sets islandsField with testIslands using the method set (library method)
        islandsField.set(gameModelTest, Arrays.copyOf(testIslands, testIslands.length));

        // Call the method shiftIslands
        gameModelTest.shiftIslands(0);

        // Gets the islandsField with the method set (library method)
        // Saves the islands array in a temporary field
        Island[] islandsValue = (Island[]) islandsField.get(gameModelTest);

        // Checks if the array was shortened
        if (islandsValue.length == testIslands.length)
            throw new AssertionError("Islands array not shortened");

        // Checks if the island 0 was removed
        if (islandsValue[0].equals(testIslands[0]))
            throw new AssertionError("Island not removed");
    }

    /**
     * Test for the method which gets the length of the field "islands"
     */
    @Test
    void getIslandsCountTest() {
        // Gets the length of the array islands and checks if it's they same as the array set in BeforeEach
        if (gameModelTest.getIslandsCount() != MAX_ISLANDS)
            throw new AssertionError("getIslandsCount returned wrong value");
    }

    /**
     * Test for the getter of an element (CloudTile) of the array of CloudTile from the field "cloudTiles"
     */
    @Test
    void getCloudTileTest() throws IllegalAccessException {
        // Sets cloudTilesField with testCloudTiles using the method set (library method)
        cloudTilesField.set(gameModelTest, Arrays.copyOf(testCloudTiles, testCloudTiles.length));

        // Gets each CloudTile of CloudTiles[] independently and checks if they are the same as in testCloudTiles
        for (int i = 0; i < MAX_CLOUD_TILES; ++i)
            if (testCloudTiles[i] != gameModelTest.getCloudTile(i))
                throw new AssertionError("getCloudTile returned wrong value");
    }

    /**
     * Test for the setter of and element (CloudTile) of the array of CloudTile from the field "cloudTiles"
     */
    @Test
    void setCloudTileTest() throws  IllegalAccessException {
        // Sets all cloudTiles independently and tries to retrieve the same value using the method get (library method)
        for (int i = 0; i < MAX_CLOUD_TILES; ++i) {
            gameModelTest.setCloudTile(testCloudTiles[i], i);
            if (!testCloudTiles[i].equals(((CloudTile[]) cloudTilesField.get(gameModelTest))[i]))
                throw new AssertionError("Independent setCloudTile set wrong value");
        }
    }

    /**
     * Test for getter of the field "motherNaturePosition"
     */
    @Test
    void getMotherNaturePositionTest() {
        // Gets motherNaturePosition and checks if it's the same set in BeforeAll
        if (gameModelTest.getMotherNaturePosition() != MIN_MOTHER_NATURE_POSITION)
            throw new AssertionError("getMotherNaturePosition returned wrong value");
    }

    /**
     * Test for setter of the field "motherNaturePosition"
     */
    @Test
    void setMotherNaturePositionTest() throws IllegalAccessException {
        // Sets motherNaturePosition to MAX_MOTHER_NATURE_POSITION
        gameModelTest.setMotherNaturePosition(MAX_MOTHER_NATURE_POSITION);

        // Gets motherNaturePosition using get (library method) and checks if it is equal to MAX_NATURE_POSITION
        if (!motherNaturePositionField.get(gameModelTest).equals(MAX_MOTHER_NATURE_POSITION))
            throw new AssertionError("setMotherNaturePosition set wrong value");
    }

    /**
     * Test for getter of the field "bag"
     */
    @Test
    void getBagTest() {
        // Gets bag and checks if it is the same set in BeforeAll
        if (!gameModelTest.getBag().equals(testBag))
            throw new AssertionError("getBag returned wrong value");
    }

    /**
     * Test for setter of the field "bag"
     */
    @Test
    void setBagTest() throws IllegalAccessException {
        // Sets bag to a temporary Bag
        Bag tmpBag = new Bag();
        gameModelTest.setBag(tmpBag);

        // Gets bag using get (library method) and checks if it is equal to tmpBag
        if (!bagField.get(gameModelTest).equals(tmpBag))
            throw new AssertionError("setBag set wrong value");
    }

    /**
     * Test for getter of the field "globalProfessorTable"
     */
    @Test
    void getGlobalProfessorTableTest() {
        // Gets globalProfessorTable and checks if it is the same set in BeforeAll
        if (!gameModelTest.getGlobalProfessorTable().equals(testGlobalProfessorTable))
            throw new AssertionError("getGlobalProfessorTable returned wrong value");
    }

    /**
     * Test for setter of the field "globalProfessorTable"
     */
    @Test
    void setGlobalProfessorTableTest() throws IllegalAccessException {
        // Sets globalProfessorTable to a temporary GlobalProfessorTable
        GlobalProfessorTable tmpGlobalProfessorTable = new GlobalProfessorTable();
        gameModelTest.setGlobalProfessorTable(tmpGlobalProfessorTable);

        // Gets globalProfessorTable using get (library method) and checks if it is equal to tmpGlobalProfessorTable
        if (!globalProfessorTableField.get(gameModelTest).equals(tmpGlobalProfessorTable))
            throw new AssertionError("setGlobalProfessorTable set wrong value");
    }

    /**
     * Test for getter of the field "expertMode"
     */
    @Test
    void getExpertModeTest() {
        // Gets expertMode and checks if it is the same set in BeforeAll
        if (gameModelTest.getExpertMode() != DEFAULT_EXPERT_MODE)
            throw new AssertionError("getExpertMode returned wrong value");
    }

    /**
     * Test for the getter of an element (CharacterCard) of the array of CharacterCards from the field "characterCards"
     */
    @Test
    void getCharacterCardTest() throws IllegalAccessException {
        // Sets characterCardsField with testCharacterCards using the method set (library method)
        characterCardsField.set(gameModelTest, Arrays.copyOf(testCharacterCards, testCharacterCards.length));

        // Gets each CharacterCard of CharacterCards[] independently and checks if they are the same as in testCharacterCards
        for (int i = 0; i < CHARACTER_CARDS_NUMBER; ++i)
            if (testCharacterCards[i] != gameModelTest.getCharacterCard(i))
                throw new AssertionError("getCharacterCard returned wrong value");
    }

    /**
     * Test for the setter of and element (CharacterCard) of the array of CharacterCard from the field "characterCards"
     */
    @Test
    void setCharacterCardTest() throws IllegalAccessException {
        // Sets all CharacterCards independently and tries to retrieve the same value using the method get (library method)
        for (int i = 0; i < CHARACTER_CARDS_NUMBER; ++i) {
            gameModelTest.setCharacterCard(testCharacterCards[i],i);
            if (!testCharacterCards[i].equals(((CharacterCard[]) characterCardsField.get(gameModelTest))[i]))
                throw new AssertionError("Independent setCharacterCard set wrong value");
        }
    }

    /**
     * Test for getter of the field "coinPool"
     */
    @Test
    void getCoinPoolTest() throws IllegalAccessException {
        // Sets coinPoolField with testCoinPool using the method set (library method)
        coinPoolField.set(gameModelTest, testCoinPool);

        // Gets coinPool and checks if it is equal to testCoinPool
        if (!gameModelTest.getCoinPool().equals(testCoinPool))
            throw new AssertionError("getCoinPool returned wrong value");
    }

    /**
     * Test for setter of the field "coinPool"
     */
    @Test
    void setCoinPoolTest() throws IllegalAccessException {
        // Sets coinPoolField with testCoinPool
        gameModelTest.setCoinPool(testCoinPool);

        // Gets coinPool using the method get (library method) and checks if it is equal to testCoinPool
        if (!coinPoolField.get(gameModelTest).equals(testCoinPool))
            throw new AssertionError("setCoinPool set wrong value");
    }

    /**
     * Test for the coin pool's decreaser
     */
    @Test
    void decreaseCoinPoolTest() throws IllegalAccessException {
        coinPoolField.set(gameModelTest, 5);
        gameModelTest.decreaseCoinPool(3);

        if ((int) coinPoolField.get(gameModelTest) != 2)
            throw new AssertionError("Decreased the wrong amount of coins");

        try {
            gameModelTest.decreaseCoinPool(10);

            throw new AssertionError("No exception thrown when going out of bounds");
        }
        catch (IllegalArgumentException e) {
            if ((int) coinPoolField.get(gameModelTest) != 2)
                throw new AssertionError("Still decreased even after throwing the exception");
        }
        catch (Exception e) {
            throw new AssertionError("Wrong exception thrown");
        }
    }
}
