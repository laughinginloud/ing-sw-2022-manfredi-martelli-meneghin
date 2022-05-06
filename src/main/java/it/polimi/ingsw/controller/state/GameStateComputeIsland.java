package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.GameCommand;
import it.polimi.ingsw.controller.command.GameCommandSendInfo;
import it.polimi.ingsw.controller.command.GameCommandValues;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualView.VirtualView;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * State representing the operations on the islands: control, conquest and unification
 * @author Mattia Martelli
 */
public class GameStateComputeIsland implements GameStateActionPhase {
    boolean winTrigger = false;
    Player  winner     = null;

    public GameState nextState() {
        //return winTrigger ? new GameStateEndGame(winner) : new GameStateChooseCloud(); //TODO: uncomment when GameStateEnd has been created
        return null;
    }

    public void executeState() {
        ControllerData data          = ControllerData.getInstance();
        GameModel      model         = ControllerData.getInstance().getGameModel();
        int            islandIndex   = model.getMotherNaturePosition();
        Island         currentIsland = model.getIsland(islandIndex);
        VirtualView    player        = data.getPlayerView(data.getCurrentPlayer());

        // If the island doesn't have a tower on it launch the control routine, otherwise launch the conquest one
        if (currentIsland.getTowerColor() == null)
            controlIsland(data, model, currentIsland);
        else
            conquerIsland(data, model, currentIsland);

        // No point in continuing if the winner has already been decided
        if (winTrigger)
            return;

        unifyIslands(model, islandIndex);

        //TODO: modificare in aggiornamenti più repentini
        try {
            player.sendMessage(createMap(data, model));
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void controlIsland(ControllerData data, GameModel model, Island island) {
        Map<Player, Integer> influencePoints = getIslandInfluences(data, model, island);

        // If nobody has any influence then nobody controls the island
        if (noInfluencePoints(influencePoints))
            return;

        // Find the school board associated with the controlling player
        Player      maxPlayer   = getMaxPlayer(influencePoints);
        SchoolBoard schoolBoard = maxPlayer.getSchoolBoard();


        // Set the color of the tower on the island and decrease the count in the school board
        island.setTowerColor(schoolBoard.getTowerColor());
        schoolBoard.decreaseTowerCount();

        // If the current player has no more towers, call the end game trigger
        if (schoolBoard.getTowerCount() == 0)
            endGame(maxPlayer);
    }

    private void conquerIsland(ControllerData data, GameModel model, Island island) {
        Map<Player, Integer> influencePoints = getIslandInfluences(data, model, island);
        addTowerInfluence(influencePoints, data, model, island);

        Player      maxPlayer      = getMaxPlayer(influencePoints);
        SchoolBoard maxPlayerBoard = maxPlayer.getSchoolBoard();
        SchoolBoard curPlayerBoard = getTowerPlayer(data, island).getSchoolBoard();

        if (maxPlayerBoard != curPlayerBoard) {
            island.setTowerColor(maxPlayerBoard.getTowerColor());
            maxPlayerBoard.decreaseTowerCount();
            curPlayerBoard.increaseTowerCount();

            // If the current player ha no more towers, call the end game trigger
            if (maxPlayerBoard.getTowerCount() == 0)
                endGame(maxPlayer);
        }
    }

    private void unifyIslands(GameModel model, int islandIndex) {
        int previousIndex  = islandIndex - 1 % model.getIslandsCount(),
            successorIndex = islandIndex + 1 % model.getIslandsCount();

        if (model.getIsland(previousIndex).getTowerColor() == model.getIsland(islandIndex).getTowerColor()){
            mergeIslandsData(model.getIsland(previousIndex), model.getIsland(islandIndex));
            model.shiftIslands(islandIndex);
            successorIndex = islandIndex;
            islandIndex    = previousIndex;
        }

        if (model.getIsland(islandIndex).getTowerColor() == model.getIsland(successorIndex).getTowerColor()) {
            mergeIslandsData(model.getIsland(islandIndex), model.getIsland(successorIndex));
            model.shiftIslands(successorIndex);
        }
    }

    private Map<Player, Integer> getIslandInfluences(ControllerData data, GameModel model, Island island) {
        // Create and initialize a new map to represent the players' influences
        Map<Player, Integer> influences = new HashMap<>();
        for (Player player : data.getPlayersOrder())
            influences.put(player, 0);

        // Iterate for each color on the island and add +1 influence to the player who controls the professor
        // Note that pofessorLocation is null checked because it is null if the professor's not controlled by anyone
        for (Color color : Color.values())
            if (island.getStudentCounters(color) > 0) {
                Player professorLocation = model.getGlobalProfessorTable().getProfessorLocation(color);

                if (professorLocation != null)
                    influences.replace(professorLocation, influences.get(professorLocation) + 1);
            }

        return influences;
    }

    private boolean noInfluencePoints(Map<Player, Integer> influences) {
        // Cycle through all entries and return false if at least one of them as an influence of at least 1
        for (Map.Entry<Player, Integer> entry : influences.entrySet())
            if (entry.getValue() > 0)
                return false;

        // If the function hasn't returned yet, it means that all influences are 0
        return true;
    }

    private void addTowerInfluence(Map<Player, Integer> influences, ControllerData data, GameModel model, Island island) {
        Player towerPlayer = getTowerPlayer(data, island);

        // Add +1 to the player possessing the tower
        influences.replace(towerPlayer, influences.get(towerPlayer) + 1);
    }

    private Player getTowerPlayer(ControllerData data, Island island) throws IllegalStateException, NoSuchElementException {
        Player towerPlayer =
            // Transform the array of players into a stream to ease filtering
            Arrays.stream(data.getPlayersOrder())
                // Remove from the stream all players that do not meet the criteria
                .filter(p -> p.getSchoolBoard().getTowerColor() == island.getTowerColor())
                // Take the first element, which will be the one with the same tower color
                .findFirst()
                // Unpack the value from the optional, throwing an exception if it's empty
                // Note: if the optional's empty it means that the model wasn't populated correctly
                .orElseThrow();

        // Check whether the player is really the correct one, to be safe
        // If that's not the case then the model has been wrongly initialized
        if (towerPlayer.getSchoolBoard().getTowerColor() != island.getTowerColor())
            throw new IllegalStateException();

        return towerPlayer;
    }

    private Player getMaxPlayer(Map<Player, Integer> players) throws NoSuchElementException {
        return players
            // Get the set representing the map
            .entrySet()
            // Transform in into a stream to ease operations
            .stream()
            // Reduce the stream to an optional containing the desired (Player, Integer)
            .reduce((a, b) -> a.getValue() > b.getValue() ? a : b)
            // Check whether a value is really present, just to be safe
            .orElseThrow()
            // Return the desired player
            .getKey();
    }

    private void mergeIslandsData(Island finalIsland, Island oldIsland) {
        finalIsland.setMultiplicity(finalIsland.getMultiplicity() + oldIsland.getMultiplicity());
        finalIsland.addBackgroundID(oldIsland.getBackgroundID());
        finalIsland.setNoEntryTileCount(finalIsland.getNoEntryTileCount() + oldIsland.getNoEntryTileCount());
        for (Color color : Color.values())
            finalIsland.setStudentCounters(color, finalIsland.getStudentCounters(color) + oldIsland.getStudentCounters(color));
    }

    private void endGame(Player winner) {
        this.winner = winner;
        winTrigger  = true;
    }

    private GameCommand createMap(ControllerData data, GameModel model) {
        Map<GameCommandValues, Object> map = new HashMap<>();
        map.put(GameCommandValues.ISLANDARRAY, model.getIslands());
        map.put(GameCommandValues.PLAYERARRAY, data.getPlayersOrder());
        return new GameCommandSendInfo(map);
    }
}
