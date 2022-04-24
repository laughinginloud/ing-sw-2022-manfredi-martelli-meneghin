package it.polimi.ingsw.model;

import java.util.Arrays;
import java.util.Optional;

/**
 * Class representing the basic structure of the game, which will interface with the controller
 * @author Giovanni Manfredi
 */
public class GameModel {
    private final Player[]                  players;
    private       Island[]                  islands;
    private       CloudTile[]               cloudTiles;
    private       int                       motherNaturePosition;
    private       Bag                       bag;
    private       GlobalProfessorTable      globalProfessorTable;
    private       boolean                   expertMode;
    private       Optional<CharacterCard>[] characterCards;
    private       Optional<Integer>         coinPool;

    public GameModel(int numOfPlayers){
        players              = new Player[numOfPlayers];
        islands              = new Island[12];
        cloudTiles           = new CloudTile[numOfPlayers];
        bag                  = new Bag();
        globalProfessorTable = new GlobalProfessorTable();
        for (int i = 0; i < 3; i++){
            characterCards[i] = Optional.empty();
        }
        coinPool             = Optional.empty();
    }

    /**
     * Get the specific Player saved at a specific index
     * @param index The index at which the Player is saved
     * @return The specific Player requested
     */
    public Player getPlayer(int index) throws IllegalArgumentException{
        if (index < 0 || index > 3)
            throw new IllegalArgumentException("Index value invalid! Accepted index between 0 and 3");

        return players[index];
    }

    /**
     * Set the Player saved at a specific index
     * @param player The player to be saved (not null value)
     * @param index The index of the specific Player that needs to be set (between 0 and 3)
     */
    public void setPlayer(Player player, int index) throws IllegalArgumentException{
        if (player == null)
            throw new IllegalArgumentException("The player to be set is null!");
        else if (index < 0 || index > 3)
            throw new IllegalArgumentException("Index value invalid! Accepted index between 0 and 3");

        this.players[index] = player;
    }

    /**
     * Get the number of Players in the game
     * @return The number of players (from 2 to 4)
     */
    public int getPlayersCount(){ return players.length;}

    /**
     * Get the Island saved at a specific index
     * @param index The index of the island array (between 0 and 11)
     * @return The specific island requested
     */
    public Island getIsland(int index) throws IllegalArgumentException{
        if (index < 0 || index > 11)
            throw new IllegalArgumentException("Index value invalid! Accepted index between 0 and 11");

        return islands[index];
    }

    /**
     * Set the Island saved at a specific index
     * @param island The island to be saved (not null value)
     * @param index The index of the specific island that needs to be set (between 0 and 11)
     */
    public void setIsland(Island island, int index) throws IllegalArgumentException{
        if (island == null)
            throw new IllegalArgumentException("The island to be set is null!");
        else if (index < 0 || index > 11)
            throw new IllegalArgumentException("Index value invalid! Accepted index between 0 and 11");

        this.islands[index] = island;
    }

    /**
     * Shift of the islands array needed when merging islands, shrinking it in the process
     * @param index The index of the island I will overwrite (between 0 and 11)
     */
    public void shiftIslands(int index) throws IllegalArgumentException{
        if (index < 0 || index > 11)
            throw new IllegalArgumentException("Index value invalid! Accepted index between 0 and 11");

        System.arraycopy(islands, index + 1, islands, index, islands.length - index - 1);
        islands = Arrays.copyOf(islands, islands.length - 1);
    }

    /**
     * Get the number of Island present
     * @return The number of Island present (from 0 to 12)
     */
    public int getIslandsCount(){ return islands.length;}

    /**
     * Get the CloudTile saved at a specific index
     * @param index The index of the cloudTile array (between 0 and 3)
     * @return The specific cloudTile requested
     */
    public CloudTile getCloudTile(int index) throws IllegalArgumentException{
        if (index < 0 || index > 3)
            throw new IllegalArgumentException("Index value invalid! Accepted index between 0 and 3");

        return cloudTiles[index];
    }

    /**
     * Set the cloudTile saved at a specific index
     * @param cloudTile The cloudTile to be saved (not null value)
     * @param index The index of the specific cloudTile that needs to be set (between 0 and 3)
     */
    public void setCloudTile(CloudTile cloudTile, int index) throws IllegalArgumentException{
        if (cloudTile == null)
            throw new IllegalArgumentException("The cloudTile to be set is null!");
        else if (index < 0 || index > 3)
            throw new IllegalArgumentException("Index value invalid! Accepted index between 0 and 3");

        this.cloudTiles[index] = cloudTile;
    }

    /**
     * Get the position of mother nature saved
     * @return The position of mother nature (from 0 to 11)
     */
    public int getMotherNaturePosition(){ return motherNaturePosition;}

    /**
     * Set the saved position of mother nature to a parameter
     * @param motherNaturePosition The position of mother nature to be saved (between 0 and 11)
     */
    public void setMotherNaturePosition(int motherNaturePosition) throws IllegalArgumentException{
        if (motherNaturePosition < 0 || motherNaturePosition > 11)
            throw new IllegalArgumentException("MotherNaturePosition value invalid! Accepted motherNaturePosition between 0 and 11");

        this.motherNaturePosition = motherNaturePosition;
    }

    /**
     * Get the Bag saved
     * @return The Bag
     */
    public Bag getBag(){ return bag;}

    /**
     * Set the saved bag to a specific bag
     * @param bag The bag to be saved (not null value)
     */
    public void setBag(Bag bag) throws IllegalArgumentException{
        if (bag == null)
            throw new IllegalArgumentException("The bag to be set is null!");

        this.bag = bag;
    }

    /**
     * Get the globalProfessorTable saved
     * @return the globalProfessorTable
     */
    public GlobalProfessorTable getGlobalProfessorTable(){ return globalProfessorTable;}

    /**
     * Set the saved globalProfessorTable to a specific globalProfessorTable
     * @param globalProfessorTable The globalProfessorTable to be saved (not null value)
     */
    public void setGlobalProfessorTable(GlobalProfessorTable globalProfessorTable) throws IllegalArgumentException{
        if (globalProfessorTable == null)
            throw new IllegalArgumentException("The globalProfessorTable to be set is null!");

        this.globalProfessorTable = globalProfessorTable;
    }

    /**
     * Get if the GameMode is expert or not
     * @return true if Expert is set, false otherwise
     */
    public boolean getExpertMode(){ return expertMode;}

    /**
     * Set GameMode to Expert
     * @param expertMode true is Expert, false otherwise
     */
    public void setExpertMode(boolean expertMode){ this.expertMode = expertMode;}

    /**
     * Get the specific characterCard saved at a specific index
     * @param index The index at which the characterCard is saved (between 0 and 2)
     * @return The specific characterCard requested
     */
    public Optional<CharacterCard> getCharacterCard(int index) throws IllegalArgumentException{
        if (index < 0 || index > 2)
            throw new IllegalArgumentException("Index value invalid! Accepted index between 0 and 2");

        return characterCards[index];
    }

    /**
     * Set the characterCard saved at a specific index
     * @param characterCard The characterCard to be saved (not null value)
     * @param index The index of the specific characterCard that needs to be set (between 0 and 2)
     */
    public void setCharacterCard(CharacterCard characterCard, int index) throws IllegalArgumentException{
        if (characterCard == null)
            throw new IllegalArgumentException("The characterCard to be set is null!");
        else if (index < 0 || index > 2)
            throw new IllegalArgumentException("Index value invalid! Accepted index between 0 and 2");

        this.characterCards[index] = Optional.of(characterCard);
    }

    /**
     * Get the number of coins in the pool
     * @return The number of coins currently in the pool (from 0 to 20)
     */
    public Optional<Integer> getCoinPool(){ return coinPool;}

    /**
     * Set the number of coins in the pool
     * @param coinPool The number of coins that are in the pool (from 0 to 20)
     */
    public void setCoinPool(int coinPool) throws IllegalArgumentException{
        if (coinPool < 0 || coinPool > 20)
            throw new IllegalArgumentException("CoinPool value invalid! Accepted coinPool between 0 and 20");

        this.coinPool = Optional.of(coinPool);
    }
}
