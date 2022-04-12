package it.polimi.ingsw.model;

import java.util.Optional;

/**
 * Class representing the basic structure of the game, which will interface with the controller
 * @author Giovanni Manfredi
 */
public class GameModel {
    private static GameModel instance;
    private Player[] players;
    // playersCount >=2 && playersCount <= 4
    private int playersCount;
    private Island[] islands;
    // islandCount <=12 islandCount >= 2
    private int islandsCount;
    // motherNaturePosition (condition) do we want to enum from 1 or 0?
    private int motherNaturePosition;
    private Optional<Integer> coinPool;
    private boolean expertMode;

    private GameModel(){
        players = new Player[4];
        islands = new Island[12];
        coinPool = Optional.empty();
    }

    public static GameModel getInstance(){
        if (instance == null){
            instance = new GameModel();
        }
        return instance;
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
    public int getPlayersCount(){ return playersCount;}

    /**
     * Set the number of Players in the game
     * @param playersCount The number of players to be set (from 2 to 4)
     */
    public void setPlayersCount(int playersCount){ this.playersCount = playersCount;}

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
     * Shift of the islands array needed when merging islands
     * @param index The index of the island I will overwrite
     */
    public void shiftIslands(int index){
        System.arraycopy(islands, index + 1, islands, index, islands.length - index - 1);
    }

    /**
     * Get the number of Island present
     * @return The number of Island present (from 0 to 12)
     */
    public int getIslandsCount(){ return islandsCount;}

    /**
     * Set the number of Island present
     * @param islandsCount The value of island to be set (from 0 to 12)
     */
    public void setIslandsCount(int islandsCount){ this.islandsCount = islandsCount;}

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
     * Get the number of coins in the pool
     * @return The number of coins currently in the pool (from 0 to 20)
     */
    public Optional<Integer> getCoinPool(){ return coinPool;}

    /**
     * Set the number of coins in the pool
     * @param coinPool The number of coins that are in the pool (from 0 to 20)
     */
    public void setCoinPool(int coinPool){ this.coinPool = Optional.of(coinPool);}

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
}
