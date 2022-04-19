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
    private       Optional<Integer>         coinPool;

    public GameModel(int numOfPlayers, int numOfCloudTiles){
        players              = new Player[numOfPlayers];
        islands              = new Island[12];
        cloudTiles           = new CloudTile[numOfCloudTiles];
        bag                  = new Bag();
        globalProfessorTable = new GlobalProfessorTable();
        coinPool             = Optional.empty();
    }

    /**
     * Get the specific Player saved at a specific index
     * @param index The index at which the Player is saved
     * @return The specific Player requested
     */
    public Player getPlayer(int index){ return players[index];}

    /**
     * Set the Player saved at a specific index
     * @param player The player to be saved
     * @param index The index of the specific Player that needs to be set
     */
    public void setPlayer(Player player, int index){ this.players[index] = player;}

    /**
     * Get the number of Players in the game
     * @return The number of players (from 2 to 4)
     */
    public int getPlayersCount(){ return players.length;}

    /**
     * Get the Island saved at a specific index
     * @param index The index of the island array
     * @return The specific island requested
     */
    public Island getIsland(int index){ return islands[index];}

    /**
     * Set the Island saved at a specific index
     * @param island The island to be saved
     * @param index The index of the specific island that needs to be set
     */
    public void setIsland(Island island, int index){ this.islands[index] = island;}

    /**
     * Shift of the islands array needed when merging islands, shrinking it in the process
     * @param index The index of the island I will overwrite
     */
    public void shiftIslands(int index){
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
     * @param index The index of the cloudTile array
     * @return The specific cloudTile requested
     */
    public CloudTile getCloudTile(int index){ return cloudTiles[index];}

    /**
     * Set the cloudTile saved at a specific index
     * @param cloudTile The cloudTile to be saved
     * @param index The index of the specific cloudTile that needs to be set
     */
    public void setCloudTile(CloudTile cloudTile, int index){ this.cloudTiles[index] = cloudTile;}

    /**
     * Get the number of cloudTiles present
     * @return The number of CloudTiles present (from 2 to 4)
     */
    public int getCloudTilesCount(){ return cloudTiles.length;}

    /**
     * Get the position of mother nature saved
     * @return The position of mother nature (from 0 to 11)
     */
    public int getMotherNaturePosition(){ return motherNaturePosition;}

    /**
     * Set the saved position of mother nature to a parameter
     * @param motherNaturePosition The position of mother nature to be saved (from 0 to 11)
     */
    public void setMotherNaturePosition(int motherNaturePosition){ this.motherNaturePosition = motherNaturePosition;}

    /**
     * Get the Bag saved
     * @return The Bag
     */
    public Bag getBag(){ return bag;}

    /**
     * Set the saved bag to a specific bag
     * @param bag The bag to be saved
     */
    public void setBag(Bag bag){ this.bag = bag;}

    /**
     * Get the globalProfessorTable saved
     * @return the globalProfessorTable
     */
    public GlobalProfessorTable getGlobalProfessorTable(){ return globalProfessorTable;}

    /**
     * Set the saved globalProfessorTable to a specific globalProfessorTable
     * @param globalProfessorTable The globalProfessorTable to be saved
     */
    public void setGlobalProfessorTable(GlobalProfessorTable globalProfessorTable){ this.globalProfessorTable = globalProfessorTable;}

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
     * Get the number of coins in the pool
     * @return The number of coins currently in the pool (from 0 to 20)
     */
    public Optional<Integer> getCoinPool(){ return coinPool;}

    /**
     * Set the number of coins in the pool
     * @param coinPool The number of coins that are in the pool (from 0 to 20)
     */
    public void setCoinPool(int coinPool){ this.coinPool = Optional.of(coinPool);}
}
