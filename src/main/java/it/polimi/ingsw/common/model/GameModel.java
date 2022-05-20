package it.polimi.ingsw.common.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Class representing the basic structure of the game,
 * which will interface with the controller - Facade Pattern
 * @author Giovanni Manfredi
 */
public class GameModel {

    // region Fields

    // Attributes of the class GameModel
    private       Player[]             players;
    private       Island[]             islands;
    private       CloudTile[]          cloudTiles;
    private       int                  motherNaturePosition;
    private       Bag                  bag;
    private       GlobalProfessorTable globalProfessorTable;
    private final boolean              expertMode;
    private       CharacterCard[]      characterCards;
    private       Integer              coinPool;

    // endregion

    // region Construction

    /**
     * Constructor of the class 'GameModel'
     * @author Giovanni Manfredi & Mattia Martelli
     * @param numOfPlayers the number of players in the game (positive integer between 2 and 4)
     * @param expertMode a boolean specifying if the game is in ExpertMode or not (true - expert, false - normal)
     */
    public GameModel(int numOfPlayers, boolean expertMode) {
        players              = new Player[numOfPlayers];
        islands              = new Island[12];
        cloudTiles           = new CloudTile[numOfPlayers];
        bag                  = new Bag();
        globalProfessorTable = new GlobalProfessorTable();
        this.expertMode      = expertMode;
        characterCards       = expertMode ? chooseCharacterCards() : null;
        coinPool             = expertMode ? 20 : null;

        buildIslands();
        buildClouds();
    }

    /**
     * Selects randomly the CharacterCards that will be used during the game - expertMode only
     * @author Giovanni Manfredi & Mattia Martelli
     * @return An array containing the cards (not null)
     */
    private CharacterCard[] chooseCharacterCards() {
        Random rng = new Random();
        // Use a set to avoid getting duplicates
        Set<Integer> cardSet = new HashSet<>();

        // Create a subset of three elements from [0, 11]
        for (int i = 0; i < 3; ++i)
            // Add a new random integer, retrying if add returns false, i.e. the set already contains the element
            while (!cardSet.add(rng.nextInt(12)));

        // Create a stream from the set to ease operations
        return cardSet.stream()
            // Map each element of the set to the corresponding enum constant
            .map(Character::fromInt)
            // Map each enum constant to the corresponding CharacterCard
            .map(CharacterCard::build)
            // Collect the results into a new array
            .toArray(CharacterCard[]::new);
    }

    /**
     * Builder of the island field
     * @author Mattia Martelli
     */
    private void buildIslands() {
        int seed = (int) (Math.random() * 3);
        for (int i = 0; i < islands.length; ++i) {
            islands[i] = new Island();
            islands[i].addBackgroundID(seed++ % 4);
        }
    }

    /**
     * Builder of the clouds field
     * @author Mattia Martelli
     */
    private void buildClouds() {
        for (int i = 0; i < cloudTiles.length; ++i)
            cloudTiles[i] = new CloudTile();
    }

    // endregion

    // region Getter

    /**
     * Gets the Player saved at a specific index
     * @param index The index at which the Player is saved (positive integer between 0 and 3)
     * @return The Player requested (not null)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public Player getPlayer(int index) throws IllegalArgumentException {
        // Checks if the given index is within the range accepted [0, 3]
        if (index < 0 || index > 3)
            throw new IllegalArgumentException("getPlayer: Index value invalid! Accepted index in the range [0, 3]");

        return players[index];
    }

    // TODO: test
    /**
     * Gets the entire array of players
     * @author Mattia Martelli
     * @return The desired array
     */
    public Player[] getPlayer() {
        return players;
    }

    /**
     * Gets the number of Players in the game
     * @author Mattia Martelli
     * @return The number of players (in the range [2, 4])
     */
    public int getPlayersCount() { return players.length; }

    /**
     * Gets the Island saved at a specific index
     * @param index The index of the island array (positive integer between 0 and 11)
     * @return The island requested (not null)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public Island getIsland(int index) throws IllegalArgumentException {
        // Checks if the index is within the accepted range [0, 11]
        if (index < 0 || index > 11)
            throw new IllegalArgumentException("getIsland: Index value invalid! Accepted index in the range [0, 11]");

        return islands[index];
    }

    /**
     * Gets the number of Island present, i.e. the length of islands
     * @author Mattia Martelli
     * @return The number of Island present (in the range [0, 12])
     */
    public int getIslandsCount() { return islands.length; }

    /**
     * Gets the CloudTile saved at a specific index
     * @param index The index of the cloudTile array (in the range [0, 3])
     * @return The cloudTile requested
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public CloudTile getCloudTile(int index) throws IllegalArgumentException {
        // Checks if the index is within the accepted range [0, 3]
        if (index < 0 || index > 3)
            throw new IllegalArgumentException("getCloudTile: Index value invalid! Accepted index in the range [0, 3]");

        return cloudTiles[index];
    }

    /**
     * Gets the CloudTiles of the current Game
     * @return An Array containing all the CloudTiles of the GameModel
     */
    public CloudTile[] getCloudTile() { return Arrays.copyOf(cloudTiles, cloudTiles.length); }

    /**
     * Gets the position of mother nature saved
     * @return The position of mother nature (from 0 to 11)
     */
    public int getMotherNaturePosition() { return motherNaturePosition; }

    /**
     * Sets the Player saved at a specific index
     * @param player The player to be saved (not null)
     * @param index The index of the Player that needs to be set (positive integer from 0 to 3)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setPlayer(Player player, int index) throws IllegalArgumentException {
        // Checks if the player is not null and if the index is within the accepted range [0, 3]
        if (player == null)
            throw new IllegalArgumentException("setPlayer: The player to be set is null!");
        else if (index < 0 || index > 3)
            throw new IllegalArgumentException("setPlayer: Index value invalid! Accepted index in the range [0, 3]");

        this.players[index] = player;
    }

    //TODO: test
    /**
     * Set the entire array of players
     * @param players The array to copy into the model
     */
    public void setPlayer(Player[] players) {
        this.players = Arrays.copyOf(players, players.length);
    }

    /**
     * Gets the Bag saved
     * @return The Bag
     */
    public Bag getBag() { return bag; }

    /**
     * Gets the globalProfessorTable saved
     * @return the globalProfessorTable
     */
    public GlobalProfessorTable getGlobalProfessorTable() { return globalProfessorTable; }

    /**
     * Gets if the GameMode is expert or not
     * @return true if Expert is set, false otherwise
     */
    public boolean getExpertMode() { return expertMode; }

    /**
     * Gets the characterCard saved at a specific index
     * @param index The index at which the characterCard is saved (positive integer from 0 to 3)
     * @return The characterCard requested
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public CharacterCard getCharacterCard(int index) throws IllegalArgumentException {
        // Checks if the index is within the accepted range [0, 2]
        if (index < 0 || index > 2)
            throw new IllegalArgumentException("getCharacterCard: Index value invalid! " +
                                               "Accepted index in the range [0, 2]");

        return characterCards[index];
    }

    /**
     * Gets the CharacterCard's Array
     * @return An array containing all the CharacterCards drawn at the beginning of the game
     */
    public CharacterCard[] getCharacterCards() {
        return Arrays.copyOf(characterCards, characterCards.length);
    }

    /**
     * Gets the number of coins in the pool
     * @return The number of coins currently in the pool (positive integer from 0 to 20)
     */
    public Integer getCoinPool() { return coinPool; }

    // TODO Test
    /**
     * Gets the entire array of islands
     * @return the array of islands
     */
    public Island[] getIslands() {
        return islands;
    }

    // endregion

    // region Setter

    /**
     * Sets the Island saved at a specific index
     * @param island The island to be saved (not null)
     * @param index The index of the island that needs to be set (positive integer from 0 to 11)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setIsland(Island island, int index) throws IllegalArgumentException {
        // Checks if the island is not null and if the index is within the accepted range [0, 11]
        if (island == null)
            throw new IllegalArgumentException("setIsland: The island to be set is null!");
        else if (index < 0 || index > 11)
            throw new IllegalArgumentException("setIsland: Index value invalid! " +
                                               "Accepted index in the range [0, 11]");

        this.islands[index] = island;
    }

    //TODO: test
    /**
     * Set the entire Island array
     * @param islands The array to be copied in the model
     */
    public void setIsland(Island[] islands) {
        this.islands = Arrays.copyOf(islands, islands.length);
    }

    /**
     * Sets the cloudTile saved at a specific index
     * @param cloudTile The cloudTile to be saved (not null)
     * @param index The index of the cloudTile that needs to be set (positive integer from 0 to 3)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setCloudTile(CloudTile cloudTile, int index) throws IllegalArgumentException {
        // Checks if the cloudTile is not null and if the index is within the accepted range [0, 3]
        if (cloudTile == null)
            throw new IllegalArgumentException("setCloudTile: The cloudTile to be set is null!");
        else if (index < 0 || index > 3)
            throw new IllegalArgumentException("setCloudTile: Index value invalid! " +
                                               "Accepted index in the range [0, 3]");

        this.cloudTiles[index] = cloudTile;
    }

    //TODO: test
    /**
     * Set the entire array of cloud tiles
     * @param cloudTiles The array to copy into the model
     */
    public void setCloudTile(CloudTile[] cloudTiles) {
        this.cloudTiles = Arrays.copyOf(cloudTiles, cloudTiles.length);
    }

    /**
     * Sets the saved position of mother nature to a parameter
     * @param motherNaturePosition The position of mother nature to be saved (between 0 and 11)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setMotherNaturePosition(int motherNaturePosition) throws IllegalArgumentException {
        // Checks if the motherNaturePosition is within the accepted range [0, 11]
        if (motherNaturePosition < 0 || motherNaturePosition > 11)
            throw new IllegalArgumentException("setMotherNaturePosition: MotherNaturePosition value invalid! " +
                                               "Accepted motherNaturePosition in the range [0, 11]");

        this.motherNaturePosition = motherNaturePosition;
    }

    /**
     * Moves Mother Nature by increasing motherNaturePosition
     * @param movementPoints the steps that motherNature makes (positive integer from 1 to 5)
     */
    //TODO: test
    public void moveMotherNature(int movementPoints) {
        setMotherNaturePosition((motherNaturePosition + movementPoints) % getIslandsCount());
    }

    /**
     * Sets the saved bag to a specific bag
     * @param bag The bag to be saved (not null)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setBag(Bag bag) throws IllegalArgumentException {
        // Checks if the bag is not null
        if (bag == null)
            throw new IllegalArgumentException("setBag: The bag to be set is null!");

        this.bag = bag;
    }

    /**
     * Sets the saved globalProfessorTable to a specific globalProfessorTable
     * @param globalProfessorTable The globalProfessorTable to be saved (not null)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setGlobalProfessorTable(GlobalProfessorTable globalProfessorTable) throws IllegalArgumentException {
        // Checks if the globalProfessorTable is not null
        if (globalProfessorTable == null)
            throw new IllegalArgumentException("setGlobalProfessorTable: The globalProfessorTable to be set is null!");

        this.globalProfessorTable = globalProfessorTable;
    }

    /**
     * Sets the characterCard saved at a specific index
     * @param characterCard The characterCard to be saved (not null)
     * @param index The index of the specific characterCard that needs to be set (positive integer from 0 to 2)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setCharacterCard(CharacterCard characterCard, int index) throws IllegalArgumentException {
        // Checks if the CharacterCard is not null and if the index is within the accepted range [0, 2]
        if (characterCard == null)
            throw new IllegalArgumentException("setCharacterCard: The characterCard to be set is null!");
        else if (index < 0 || index > 2)
            throw new IllegalArgumentException("setCharacterCard: Index value invalid! " +
                                               "Accepted index in the range [0, 2]");

        this.characterCards[index] = characterCard;
    }

    //TODO: test
    /**
     * Set the entire array of character cards
     * @param characterCards The array to copy into the model
     */
    public void setCharacterCard(CharacterCard[] characterCards) {
        this.characterCards = Arrays.copyOf(characterCards, characterCards.length);
    }

    /**
     * Sets the number of coins in the pool
     * @param coinPool The number of coins that are in the pool (positive integer from 0 to 20)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setCoinPool(Integer coinPool) throws IllegalArgumentException {
        // Checks if the coinPool is within the accepted range [0, 20]
        if (coinPool < 0 || coinPool > 20)
            throw new IllegalArgumentException("setCoinPool: CoinPool value invalid! " +
                                               "Accepted coinPool in the range [0, 20]");

        this.coinPool = coinPool;
    }

    // endregion

    // region Others

    /**
     * Shifts of the islands array needed when merging islands, shrinking it in the process
     * @param index The index of the island I will overwrite (positive integer from 0 to 11)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void shiftIslands(int index) throws IllegalArgumentException {
        // Checks if the index is within the accepted range [0, 11]
        if (index < 0 || index > 11)
            throw new IllegalArgumentException("shiftIslands: Index value invalid! " +
                                               "Accepted index in the range [0, 11]");

        // Copies the part of the island Array after the index over the index and then shrinks it
        System.arraycopy(islands, index + 1, islands, index, islands.length - index - 1);
        islands = Arrays.copyOf(islands, islands.length - 1);
    }

    /**
     * Decreases the coin pool of a specified amount
     * @param n The amount to remove from the pool (positive integer from 0 to coinPool)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void decreaseCoinPool(Integer n) throws IllegalArgumentException {
        if (n <= 0 || n > coinPool)
            throw new IllegalArgumentException("Quantity n to remove must be 0 < n <= coinPool");

        coinPool -= n;
    }

    // endregion

}
