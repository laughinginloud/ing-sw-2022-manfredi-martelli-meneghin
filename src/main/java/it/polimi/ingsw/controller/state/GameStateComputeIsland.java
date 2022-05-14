package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.GameCommand;
import it.polimi.ingsw.controller.command.GameCommandSendInfo;
import it.polimi.ingsw.controller.command.GameCommandValues;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualView.VirtualView;

import java.io.IOException;
import java.util.*;

/**
 * State representing the operations on the islands: control, conquest and unification
 * @author Mattia Martelli
 */
public class GameStateComputeIsland implements GameStateActionPhase {
    private boolean notEnoughTowerTrigger = false;
    private Integer remainingTower        = null;
    private Player  winner                = null;
    //TODO: winner list team

    //TODO: ripulire
    public GameState nextState() {
        if (ControllerData.getInstance().checkWinTrigger())
            return winner == null ?
                new GameStateEndGame() :
                ControllerData.getInstance().getGameModel().getPlayersCount() == 4 ?
                    new GameStateEndGame(putWinners(winner)) :
                    new GameStateEndGame(winner);

        return new GameStateChooseCloud();
    }

    public void executeState() {
        ControllerData data          = ControllerData.getInstance();
        GameModel      model         = data.getGameModel();
        int            islandIndex   = model.getMotherNaturePosition();
        Island         currentIsland = model.getIsland(islandIndex);

        // If the island doesn't have a tower on it launch the control routine, otherwise launch the conquest one
        if (currentIsland.getTowerColor() == null)
            controlIsland(data, model, currentIsland);
        else
            conquerIsland(data, model, currentIsland);

        // Updates all the players about the variation on IslandArray caused by the execution of controlIsland or conquerIsland
        try {
            Map<GameCommandValues, Object> controlAndConquerInfo = new HashMap<>();
            GameCommand controlAndConquerUpdate;

            // If there aren't enough towers to fill tre recolored island, saves into the map the num of remainingTower, the conquered Island
            if (notEnoughTowerTrigger) {
                controlAndConquerInfo.put(GameCommandValues.REMAININGTOWER, remainingTower);
                controlAndConquerInfo.put(GameCommandValues.ISLANDNUM, islandIndex);
            }

            // Then save anyway the update IslandArray and PlayerArray into the map
            controlAndConquerInfo.put(GameCommandValues.ISLANDARRAY, model.getIslands());
            controlAndConquerInfo.put(GameCommandValues.PLAYERARRAY, data.getPlayersOrder());
            controlAndConquerUpdate = new GameCommandSendInfo(controlAndConquerInfo);

            // Updates all the players
            for (Player playerToUpdate : data.getPlayersOrder())
                data.getPlayerView(playerToUpdate).sendMessage(controlAndConquerUpdate);

        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }

        // No point in continuing if the winner has already been decided
        if (data.checkWinTrigger())
            return;

        unifyIslands(model, islandIndex);

        // Update every player about the GameBoard's changes
        try {
            for (Player playerToUpdate : data.getPlayersOrder()) {
                VirtualView playerToUpdateView = data.getPlayerView(playerToUpdate);
                playerToUpdateView.sendMessage(createLightMap(model));
            }
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (model.getIslandsCount() <= 3)
            data.setWinTrigger(true);
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

        if (!ControllerData.getInstance().getCharacterCardFlag(ControllerData.Flags.ignoreTowersFlag))
            addTowerInfluence(influencePoints, model, island);

        Player      maxPlayer      = getMaxPlayer(influencePoints);
        Player      curPlayer      = getTowerPlayer(model, island);
        SchoolBoard maxPlayerBoard = maxPlayer.getSchoolBoard();
        SchoolBoard curPlayerBoard = curPlayer.getSchoolBoard();

        if (maxPlayerBoard != curPlayerBoard) {
            boolean notEnoughTower = maxPlayer.getSchoolBoard().getTowerCount() < island.getMultiplicity();

            // If the current player has not enough towers to replace all the towers previous present on the conquered island
            if (notEnoughTower) {
                notEnoughTowerTrigger = true;
                remainingTower = maxPlayerBoard.getTowerCount();
            }

            island.setTowerColor(maxPlayerBoard.getTowerColor());
            maxPlayerBoard.decreaseTowerCount(notEnoughTower ? maxPlayer.getSchoolBoard().getTowerCount() : island.getMultiplicity());
            curPlayerBoard.increaseTowerCount(island.getMultiplicity());

            // If the current player has no more towers, call the end game trigger
            if (maxPlayerBoard.getTowerCount() == 0)
                endGame(maxPlayer);
        }
    }

    private void unifyIslands(GameModel model, int islandIndex) {
        int previousIndex  = islandIndex - 1 % model.getIslandsCount(),
            successorIndex = islandIndex + 1 % model.getIslandsCount();

        if (model.getIsland(previousIndex).getTowerColor() == model.getIsland(islandIndex).getTowerColor()) {
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
        Map<Player, Integer> mpi = model.getPlayersCount() == 4 ? getIslandInfluencesFourPlayers(data, model, island) : getIslandInfluencesTwoThreePlayers(data, model, island);

        // TODO: noInfluenceColor needs to be checked
        if (data.getCharacterCardFlag(ControllerData.Flags.extraInfluenceFlag))
            mpi.replace(data.getCurrentPlayer(), mpi.get(data.getCurrentPlayer()) + 2);

        return mpi;
    }

    private Map<Player, Integer> getIslandInfluencesFourPlayers(ControllerData data, GameModel model, Island island) {
        // If there are four players, calculate the influence for each one and then fuse the teams
        Player[]             players          = model.getPlayer();
        Map<Player, Integer> influencesSingle = getIslandInfluencesTwoThreePlayers(data, model, island);

        // Create a map that will contain just two players: both teams' tower holders and sum the elements of the original map
        Map<Player, Integer> influencesTeams  = new HashMap<>();
        influencesTeams.put(players[0], influencesSingle.get(players[0]) + influencesSingle.get(players[2]));
        influencesTeams.put(players[1], influencesSingle.get(players[1]) + influencesSingle.get(players[3]));

        return influencesTeams;
    }

    private Map<Player, Integer> getIslandInfluencesTwoThreePlayers(ControllerData data, GameModel model, Island island) {
        // Create and initialize a new map to represent the players' influences
        Map<Player, Integer> influences = new HashMap<>();
        for (Player player : model.getPlayer())
            influences.put(player, 0);

        // Iterate for each color on the island and add +1 influence to the player who controls the professor
        // Note that professorLocation is null checked because it is null if the professor's not controlled by anyone
        for (Color color : Color.values()) {
            if (data.getCharacterCardFlag(ControllerData.Flags.excludeColorFlag) && data.getExcludedColor() == color)
                continue;

            if (island.getStudentCounters(color) > 0) {
                Player professorLocation = model.getGlobalProfessorTable().getProfessorLocation(color);

                if (professorLocation != null)
                    influences.replace(professorLocation, influences.get(professorLocation) + 1);
            }
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

    private void addTowerInfluence(Map<Player, Integer> influences, GameModel model, Island island) {
        Player towerPlayer = getTowerPlayer(model, island);

        // Add +1 to the player possessing the tower
        influences.replace(towerPlayer, influences.get(towerPlayer) + 1);
    }

    private Player getTowerPlayer(GameModel model, Island island) throws IllegalStateException, NoSuchElementException {
        Player towerPlayer =
            // Transform the array of players into a stream to ease filtering
            Arrays.stream(model.getPlayer())
                // Remove from the stream all players that do not meet the criteria
                .filter(p -> p.getSchoolBoard().getTowerColor() == island.getTowerColor())
                // Take the first element, which will be the one with the same tower color
                .findFirst()
                // Unpack the value from the optional, throwing an exception if it's empty
                // Note: if the optional is empty it means that the model wasn't populated correctly
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
        ControllerData.getInstance().setWinTrigger(true);
    }

    private GameCommand createMap(ControllerData data, GameModel model) {
        Map<GameCommandValues, Object> map = new HashMap<>();
        map.put(GameCommandValues.ISLANDARRAY, model.getIslands());
        map.put(GameCommandValues.PLAYERARRAY, data.getPlayersOrder());
        return new GameCommandSendInfo(map);
    }

    private GameCommand createLightMap(GameModel model) {
        Map<GameCommandValues, Object> map = new HashMap<>();
        map.put(GameCommandValues.ISLANDARRAY, model.getIslands());
        return new GameCommandSendInfo(map);
    }

    private List<Player> putWinners (Player winner) {
        int numOfPlayer = ControllerData.getInstance().getNumOfPlayers();
        Player[] players = ControllerData.getInstance().getGameModel().getPlayer();
        List<Player> winners = new ArrayList<>();
        winners.add(winner);

        for (int i = 0; i < numOfPlayer; i++)
            if (players[i].equals(winner))
                winners.add(players[(i + 2) % 4]);

        return winners;
    }
}
