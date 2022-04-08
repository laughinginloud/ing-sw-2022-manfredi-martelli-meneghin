package it.polimi.ingsw.model;

import java.util.Optional;

/**
 * Class representing the basic structure of the game, which will interface with the controller
 */
public class GameModel {
    private static GameModel instance;
    private Player[] players;
    private int playersCount; // playersCount >=2 && playersCount <= 4
    private Island[] islands;
    private int islandCount; // islandCount <=12 islandCount >= 2
    private int motherNaturePosition; // motherNaturePosition (condition) do we want to enum from 1 or 0?
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

    public Island getIsland(int index){ return islands[index];}

    public void setIsland(Island island, int index){ this.islands[index] = island;}

    public void shiftIslands(int index){
        System.arraycopy(islands, index + 1, islands, index, islands.length - index - 1);
    }

    public int getIslandsCount(){ return playersCount;}

    public void setIslandCount(int islandCount){ this.islandCount = islandCount;}

    public Player getPlayer(int index){ return players[index];}

    public void setPlayer(Player player, int index){ this.players[index] = player;}

    public int getPlayersCount(){ return playersCount;}

    public void setPlayersCount(int playersCount){ this.playersCount = playersCount;}

    public int getMotherNaturePosition(){ return motherNaturePosition;}

    public void setMotherNaturePosition(int motherNaturePosition){ this.motherNaturePosition = motherNaturePosition;}

    public Optional<Integer> getCoinPool(){ return coinPool;}

    public void setCoinPool(int coinPool){ this.coinPool = Optional.of(coinPool);}

    public boolean getExpertMode(){ return expertMode;}

    public void setExpertMode(boolean expertMode){ this.expertMode = expertMode;}
}
