package it.polimi.ingsw.model;

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
    private final Player[]             players;
    private       Island[]             islands;
    private final CloudTile[]          cloudTiles;
    private       int                  motherNaturePosition;
    private       Bag                  bag;
    private       GlobalProfessorTable globalProfessorTable;
    private final boolean              expertMode;
    private final CharacterCard[]      characterCards;
    private       Integer              coinPool;

    // endregion

    // region Construction

    // Constructor of the class GameModel
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
     * Select randomly the cards that will be used during the game
     * @return An array containing the cards
     */
    private CharacterCard[] chooseCharacterCards() {
        Random rng = new Random();
        // Use a set to avoid getting duplicates
        Set<Integer> cardSet = new HashSet<>();

        // Create a subset of three elements from [0, 11]
        for (int i = 0; i < 3; ++i)
            // Add a new random integer, retrying if add returns false, i.e. the set already contains the element
            while (!cardSet.add(rng.nextInt(12)));

        // Create a stream from the set, transform it into a stream containing the cards and morph it into the destination array
        return cardSet.stream().map(CharacterCard::build).toArray(CharacterCard[]::new);
    }

    private void buildIslands() {
        int seed = (int) (Math.random() * 3);
        for (int i = 0; i < islands.length; ++i) {
            islands[i] = new Island();
            islands[i].addBackgroundID(seed++ % 4);
        }
    }

    private void buildClouds() {
        for (int i = 0; i < cloudTiles.length; ++i)
            cloudTiles[i] = new CloudTile();
    }

    // endregion

    // region Getter

    /**
     * Get the Player saved at a specific index
     * @param index The index at which the Player is saved
     * @return The Player requested
     */
    public Player getPlayer(int index) throws IllegalArgumentException {
        // Checks if the given index is within the range accepted [0, 3]
        if (index < 0 || index > 3)
            throw new IllegalArgumentException("getPlayer: Index value invalid! Accepted index in the range [0, 3]");

        return players[index];
    }

    /**
     * Get the number of Players in the game
     * @return The number of players (in the range [2, 4])
     */
    public int getPlayersCount() { return players.length; }

    /**
     * Get the Island saved at a specific index
     * @param index The index of the island array
     * @return The island requested
     */
    public Island getIsland(int index) throws IllegalArgumentException {
        // Checks if the index is within the accepted range [0, 11]
        if (index < 0 || index > 11)
            throw new IllegalArgumentException("getIsland: Index value invalid! Accepted index in the range [0, 11]");

        return islands[index];
    }

    /**
     * Get the number of Island present, i.e. the length of islands
     * @return The number of Island present (in the range [0, 12])
     */
    public int getIslandsCount() { return islands.length; }

    /**
     * Get the CloudTile saved at a specific index
     * @param index The index of the cloudTile array (in the range [0, 3])
     * @return The cloudTile requested
     */
    public CloudTile getCloudTile(int index) throws IllegalArgumentException {
        // Checks if the index is within the accepted range [0, 3]
        if (index < 0 || index > 3)
            throw new IllegalArgumentException("getCloudTile: Index value invalid! Accepted index in the range [0, 3]");

        return cloudTiles[index];
    }

    /**
     * Get the position of mother nature saved
     * @return The position of mother nature (from 0 to 11)
     */
    public int getMotherNaturePosition() { return motherNaturePosition; }

    /**
     * Set the Player saved at a specific index
     * @param player The player to be saved
     * @param index The index of the Player that needs to be set
     */
    public void setPlayer(Player player, int index) throws IllegalArgumentException {
        // Checks if the player is not null and if the index is within the accepted range [0, 3]
        if (player == null)
            throw new IllegalArgumentException("setPlayer: The player to be set is null!");
        else if (index < 0 || index > 3)
            throw new IllegalArgumentException("setPlayer: Index value invalid! Accepted index in the range [0, 3]");

        this.players[index] = player;
    }

    /**
     * Get the Bag saved
     * @return The Bag
     */
    public Bag getBag() { return bag; }

    /**
     * Get the globalProfessorTable saved
     * @return the globalProfessorTable
     */
    public GlobalProfessorTable getGlobalProfessorTable() { return globalProfessorTable; }

    /**
     * Get if the GameMode is expert or not
     * @return true if Expert is set, false otherwise
     */
    public boolean getExpertMode() { return expertMode; }

    /**
     * Get the characterCard saved at a specific index
     * @param index The index at which the characterCard is saved
     * @return The characterCard requested
     */
    public CharacterCard getCharacterCard(int index) throws IllegalArgumentException {
        // Checks if the index is within the accepted range [0, 2]
        if (index < 0 || index > 2)
            throw new IllegalArgumentException("getCharacterCard: Index value invalid! Accepted index in the range [0, 2]");

        return characterCards[index];
    }

    /**
     * Get the number of coins in the pool
     * @return The number of coins currently in the pool
     */
    public Integer getCoinPool() { return coinPool; }

    // endregion

    // region Setter

    /**
     * Set the Island saved at a specific index
     * @param island The island to be saved
     * @param index The index of the island that needs to be set
     */
    public void setIsland(Island island, int index) throws IllegalArgumentException {
        // Checks if the island is not null and if the index is within the accepted range [0, 11]
        if (island == null)
            throw new IllegalArgumentException("setIsland: The island to be set is null!");
        else if (index < 0 || index > 11)
            throw new IllegalArgumentException("setIsland: Index value invalid! Accepted index in the range [0, 11]");

        this.islands[index] = island;
    }

    /**
     * Set the cloudTile saved at a specific index
     * @param cloudTile The cloudTile to be saved
     * @param index The index of the cloudTile that needs to be set
     */
    public void setCloudTile(CloudTile cloudTile, int index) throws IllegalArgumentException {
        // Checks if the cloudTile is not null and if the index is within the accepted range [0, 3]
        if (cloudTile == null)
            throw new IllegalArgumentException("setCloudTile: The cloudTile to be set is null!");
        else if (index < 0 || index > 3)
            throw new IllegalArgumentException("setCloudTile: Index value invalid! Accepted index in the range [0, 3]");

        this.cloudTiles[index] = cloudTile;
    }

    /**
     * Set the saved position of mother nature to a parameter
     * @param motherNaturePosition The position of mother nature to be saved (between 0 and 11)
     */
    public void setMotherNaturePosition(int motherNaturePosition) throws IllegalArgumentException {
        // Checks if the motherNaturePosition is within the accepted range [0, 11]
        if (motherNaturePosition < 0 || motherNaturePosition > 11)
            throw new IllegalArgumentException("setMotherNaturePosition: MotherNaturePosition value invalid! Accepted motherNaturePosition in the range [0, 11]");

        this.motherNaturePosition = motherNaturePosition;
    }

    public void movemotherNature(int movementPoints) {
        setMotherNaturePosition((motherNaturePosition + movementPoints) % 12);
    }

    /**
     * Set the saved bag to a specific bag
     * @param bag The bag to be saved
     */
    public void setBag(Bag bag) throws IllegalArgumentException {
        // Checks if the bag is not null
        if (bag == null)
            throw new IllegalArgumentException("setBag: The bag to be set is null!");

        this.bag = bag;
    }

    /**
     * Set the saved globalProfessorTable to a specific globalProfessorTable
     * @param globalProfessorTable The globalProfessorTable to be saved
     */
    public void setGlobalProfessorTable(GlobalProfessorTable globalProfessorTable) throws IllegalArgumentException {
        // Checks if the globalProfessorTable is not null
        if (globalProfessorTable == null)
            throw new IllegalArgumentException("setGlobalProfessorTable: The globalProfessorTable to be set is null!");

        this.globalProfessorTable = globalProfessorTable;
    }

    /**
     * Set the characterCard saved at a specific index
     * @param characterCard The characterCard to be saved
     * @param index The index of the specific characterCard that needs to be set
     */
    public void setCharacterCard(CharacterCard characterCard, int index) throws IllegalArgumentException {
        // Checks if the CharacterCard is not null and if the index is within the accepted range [0, 2]
        if (characterCard == null)
            throw new IllegalArgumentException("setCharacterCard: The characterCard to be set is null!");
        else if (index < 0 || index > 2)
            throw new IllegalArgumentException("setCharacterCard: Index value invalid! Accepted index in the range [0, 2]");

        this.characterCards[index] = characterCard;
    }

    /**
     * Set the number of coins in the pool
     * @param coinPool The number of coins that are in the pool
     */
    public void setCoinPool(Integer coinPool) throws IllegalArgumentException {
        // Checks if the coinPool is within the accepted range [0, 20]
        if (coinPool < 0 || coinPool > 20)
            throw new IllegalArgumentException("setCoinPool: CoinPool value invalid! Accepted coinPool in the range [0, 20]");

        this.coinPool = coinPool;
    }

    // endregion

    // region Others

    /**
     * Shift of the islands array needed when merging islands, shrinking it in the process
     * @param index The index of the island I will overwrite
     */
    public void shiftIslands(int index) throws IllegalArgumentException {
        // Checks if the index is within the accepted range [0, 11]
        if (index < 0 || index > 11)
            throw new IllegalArgumentException("shiftIslands: Index value invalid! Accepted index in the range [0, 11]");

        // Copies the part of the island Array after the index over the index and then shrinks it
        System.arraycopy(islands, index + 1, islands, index, islands.length - index - 1);
        islands = Arrays.copyOf(islands, islands.length - 1);
    }

    /**
     * Decrease the coin pool of a specified amount
     * @param n The amount to remove from the pool
     */
    public void decreaseCoinPool(Integer n) throws IllegalArgumentException {
        if (n <= 0 || n > coinPool)
            throw new IllegalArgumentException("Quantity n to remove must be 0 < n <= coinPool");

        coinPool -= n;
    }

    // endregion
}
