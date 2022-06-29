package it.polimi.ingsw.common.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static it.polimi.ingsw.common.utils.Methods.copyOf;

/**
 * Class representing the basic structure of the game,
 * which will interface with the controller - Facade Pattern
 * @author Giovanni Manfredi
 */
public final class GameModel {

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
    @SuppressWarnings("StatementWithEmptyBody")
    private CharacterCard[] chooseCharacterCards() {
        Random rng = new Random();
        // Use a set to avoid getting duplicates
        Set<Integer> cardSet = new HashSet<>();

        // TODO: Remove this hardcoded part
        cardSet.add(0);
        cardSet.add(1);
        cardSet.add(2);

        /*
        // Create a subset of three elements from [0, 11]
        for (int i = 0; i < 3; ++i)
            // Add a new random integer, retrying if add returns false, i.e. the set already contains the element
            while (!cardSet.add(rng.nextInt(12)));
         */

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
        for (int i = 0; i < islands.length; ++i, ++seed) {
            islands[i] = new Island();
            islands[i].setBackgroundID(seed % 3);
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
     */
    public Player getPlayer(int index) {
        // Checks if the given index is within the range accepted [0, numOfPlayers]
        assert index >= 0 && index < players.length: "getPlayer: Index value invalid! Accepted index in the range [0, " + (players.length - 1) + "]";

        return players[index];
    }

    /**
     * Gets the entire array of players
     * @author Mattia Martelli
     * @return The desired array
     */
    public Player[] getPlayers() {
        return copyOf(players);
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
     */
    public Island getIsland(int index) {
        // Checks if the index is within the accepted range [0, islandCount]
        assert index >= 0 && index < islands.length: "getIsland: Index value invalid! Accepted index in the range [0, " + (islands.length - 1) + "]";

        return islands[index];
    }

    /**
     * Gets the entire array of islands
     * @return the array of islands
     */
    public Island[] getIslands() {
        return copyOf(islands);
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
     */
    public CloudTile getCloudTile(int index) {
        // Checks if the index is within the accepted range [0, cloudTilesCount]
        assert index >= 0 && index < cloudTiles.length: "getCloudTile: Index value invalid! Accepted index in the range [0, " + (cloudTiles.length - 1) + "]";

        return cloudTiles[index];
    }

    /**
     * Gets the CloudTiles of the current Game
     * @return An Array containing all the CloudTiles of the GameModel
     */
    public CloudTile[] getCloudTiles() { return copyOf(cloudTiles); }

    /**
     * Gets the position of mother nature saved
     * @return The position of mother nature (from 0 to 11)
     */
    public int getMotherNaturePosition() { return motherNaturePosition; }

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
     * @param index The index at which the characterCard is saved (positive integer from 0 to 2)
     * @return The characterCard requested
     */
    public CharacterCard getCharacterCard(int index) {
        // Checks if the index is within the accepted range [0, numOfCards)
        assert index >= 0 && index < characterCards.length: "getCharacterCard: Index value invalid! Accepted index in the range [0, " + (characterCards.length - 1) + "]";

        return characterCards[index];
    }

    /**
     * Gets the CharacterCard's Array
     * @return An array containing all the CharacterCards drawn at the beginning of the game
     */
    public CharacterCard[] getCharacterCards() {
        return copyOf(characterCards);
    }

    /**
     * Gets the number of coins in the pool
     * @return The number of coins currently in the pool (positive integer from 0 to 20)
     */
    public Integer getCoinPool() { return coinPool; }

    // endregion

    // region Setter

    /**
     * Sets the Player saved at a specific index
     * @param player The player to be saved (not null)
     * @param index The index of the Player that needs to be set (positive integer from 0 to 3)
     */
    public void setPlayer(Player player, int index) {
        // Checks if the player is not null and if the index is within the accepted range [0, numOfPlayers]
        assert player != null: "setPlayer: The player to be set is null!";
        assert index >= 0 && index < players.length: "setPlayer: Index value invalid! Accepted index in the range [0, " + (players.length - 1) + "]";

        this.players[index] = player;
    }

    /**
     * Set the entire array of players
     * @param players The array to copy into the model
     */
    public void setPlayers(Player[] players) {
        this.players = copyOf(players);
    }

    /**
     * Sets the Island saved at a specific index
     * @param island The island to be saved (not null)
     * @param index The index of the island that needs to be set (positive integer from 0 to 11)
     */
    public void setIsland(Island island, int index) {
        // Checks if the island is not null and if the index is within the accepted range [0, islandCount)
        assert island != null: "setIsland: The island to be set is null!";
        assert index >= 0 && index < islands.length: "setIsland: Index value invalid! Accepted index in the range [0, " + (islands.length - 1) + "]";

        this.islands[index] = island;
    }

    /**
     * Set the entire Island array
     * @param islands The array to be copied in the model
     */
    public void setIslands(Island[] islands) {
        this.islands = copyOf(islands);
    }

    /**
     * Sets the cloudTile saved at a specific index
     * @param cloudTile The cloudTile to be saved (not null)
     * @param index The index of the cloudTile that needs to be set (positive integer from 0 to 3)
     */
    public void setCloudTile(CloudTile cloudTile, int index) {
        // Checks if the cloudTile is not null and if the index is within the accepted range [0, cloudTilesCount)
        assert cloudTile != null: "setCloudTile: The cloudTile to be set is null!";
        assert index >= 0 && index < cloudTiles.length: "setCloudTile: Index value invalid! Accepted index in the range [0, " + (cloudTiles.length - 1) + "]";

        this.cloudTiles[index] = cloudTile;
    }

    /**
     * Set the entire array of cloud tiles
     * @param cloudTiles The array to copy into the model
     */
    public void setCloudTiles(CloudTile[] cloudTiles) {
        this.cloudTiles = copyOf(cloudTiles);
    }

    /**
     * Sets the saved position of mother nature to a parameter
     * @param motherNaturePosition The position of mother nature to be saved (between 0 and 11)
     */
    public void setMotherNaturePosition(int motherNaturePosition) {
        // Checks if the motherNaturePosition is within the accepted range [0, islandCount)
        assert motherNaturePosition >= 0 && motherNaturePosition < islands.length:
            "setMotherNaturePosition: MotherNaturePosition value invalid! Accepted motherNaturePosition in the range [0, " + (islands.length - 1) + "]";

        this.motherNaturePosition = motherNaturePosition;
    }

    /**
     * Sets the saved bag to a specific bag
     * @param bag The bag to be saved (not null)
     */
    public void setBag(Bag bag) {
        // Checks if the bag is not null
        assert bag != null: "setBag: The bag to be set is null!";

        this.bag = bag;
    }

    /**
     * Sets the saved globalProfessorTable to a specific globalProfessorTable
     * @param globalProfessorTable The globalProfessorTable to be saved (not null)
     */
    public void setGlobalProfessorTable(GlobalProfessorTable globalProfessorTable) {
        // Checks if the globalProfessorTable is not null
        assert globalProfessorTable != null: "setGlobalProfessorTable: The globalProfessorTable to be set is null!";

        this.globalProfessorTable = globalProfessorTable;
    }

    /**
     * Sets the characterCard saved at a specific index
     * @param characterCard The characterCard to be saved (not null)
     * @param index The index of the specific characterCard that needs to be set (positive integer from 0 to 2)
     */
    public void setCharacterCard(CharacterCard characterCard, int index) {
        // Checks if the CharacterCard is not null and if the index is within the accepted range [0, numOfCards)
        assert characterCard != null: "setCharacterCard: The characterCard to be set is null!";
        assert index >= 0 && index < characterCards.length: "setCharacterCard: Index value invalid! Accepted index in the range [0, " + characterCards.length + "]";

        this.characterCards[index] = characterCard;
    }

    /**
     * Set the entire array of character cards
     * @param characterCards The array to copy into the model
     */
    public void setCharacterCards(CharacterCard[] characterCards) {
        this.characterCards = copyOf(characterCards);
    }

    /**
     * Sets the number of coins in the pool
     * @param coinPool The number of coins that are in the pool (positive integer from 0 to 20)
     */
    public void setCoinPool(Integer coinPool) {
        // Checks if the coinPool is within the accepted range [0, 20]
        assert coinPool >= 0 && coinPool <= 20: "setCoinPool: CoinPool value invalid! Accepted coinPool in the range [0, 20]";

        this.coinPool = coinPool;
    }

    // endregion

    // region Others

    /**
     * Shifts of the islands array needed when merging islands, shrinking it in the process
     * @param index The index of the island I will overwrite (positive integer from 0 to 11)
     */
    public void shiftIslands(int index) {
        // Checks if the index is within the accepted range [0, islandsCount)
        assert index >= 0 && index < islands.length: "shiftIslands: Index value invalid! Accepted index in the range [0, " + (islands.length - 1) + "]";

        // Copies the part of the island Array after the index over the index and then shrinks it
        System.arraycopy(islands, index + 1, islands, index, islands.length - index - 1);
        islands = Arrays.copyOf(islands, islands.length - 1);
    }

    /**
     * Decreases the coin pool of a specified amount
     * @param n The amount to remove from the pool (positive integer from 0 to coinPool)
     */
    public void decreaseCoinPool(Integer n) {
        assert n > 0 && n <= coinPool: "Quantity n to remove must be 0 < n <= coinPool";

        coinPool -= n;
    }

    // endregion

}
