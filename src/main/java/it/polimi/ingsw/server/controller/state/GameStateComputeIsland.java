package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.GameCommand;
import it.polimi.ingsw.server.controller.command.GameCommandSendInfo;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.*;
import java.util.function.Function;

import static it.polimi.ingsw.common.utils.ModuloNat.mod;

/**
 * State representing the operations on the islands: control, conquest and unification
 * @author Mattia Martelli
 */
public final class GameStateComputeIsland implements GameStateActionPhase {
    private       Player  winner;
    private final int     islandIndex;

    public GameStateComputeIsland() {
        this(ControllerData.getInstance().getGameModel().getMotherNaturePosition());
    }

    public GameStateComputeIsland(int islandIndex) {
        this.islandIndex = islandIndex;
             winner      = null;
    }

    public GameState nextState() {
        return
            // Check if the game has been won already (negated, to help readability)
            !ControllerData.getInstance().checkWinTrigger() ?
                // If the game hasn't ended yet, go to the next phase
                new GameStateChooseCloud() :
                // Otherwise, check if a winner has already been decided
                winner == null ?
                    // If no player has been elected as winner, return a plain end phase, which will decide who won
                    new GameStateEndGame() :
                    // Otherwise, check whether there are four players
                    ControllerData.getInstance().getGameModel().getPlayersCount() == 4 ?
                        // If that's true, create the end state with the winning team
                        new GameStateEndGame(putWinners(winner)) :
                        // Otherwise, create the end state with the single winner
                        new GameStateEndGame(winner);
    }

    public void executeState() {
        ControllerData data          = ControllerData.getInstance();
        GameModel      model         = data.getGameModel();
        Island         currentIsland = model.getIsland(islandIndex);

        // If the island doesn't have a tower on it launch the control routine, otherwise launch the conquest one
        if (currentIsland.getMultiplicity() == 0)
            controlIsland(data, model, currentIsland);
        else
            conquerIsland(data, model, currentIsland);

        // Updates all the players about the variation on IslandArray caused by the execution of controlIsland or conquerIsland
        try {
            GameCommand controlAndConquerUpdate = createMap(model);

            // Updates all the players
            for (Player playerToUpdate : model.getPlayers())
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
        for (Player playerToUpdate : model.getPlayers()) {
            VirtualView playerToUpdateView = data.getPlayerView(playerToUpdate);
            playerToUpdateView.sendMessage(createLightMap(model));
        }

        if (model.getIslandsCount() <= 3)
            data.setWinTrigger(true);
    }

    private void controlIsland(ControllerData data, GameModel model, Island island) {
        Map<Player, Integer> influencePoints = getIslandInfluences(data, model, island);

        // If nobody has any influence or multiple players have the same influence then nobody controls the island
        if (noInfluencePoints(influencePoints) || contested(influencePoints))
            return;

        // Find the school board associated with the controlling player
        Player      maxPlayer   = getMaxPlayer(influencePoints);
        SchoolBoard schoolBoard = maxPlayer.getSchoolBoard();

        // Set the color of the tower on the island and decrease the count in the school board
        island.setTowerColor(schoolBoard.getTowerColor());
        island.setMultiplicity(1);
        schoolBoard.decreaseTowerCount();

        // If the current player has no more towers, call the end game trigger
        if (schoolBoard.getTowerCount() == 0)
            endGame(maxPlayer);
    }

    private void conquerIsland(ControllerData data, GameModel model, Island island) {
        Map<Player, Integer> influencePoints = getIslandInfluences(data, model, island);

        if (!data.getCharacterCardFlag(ControllerData.Flags.ignoreTowersFlag))
            addTowerInfluence(influencePoints, model, island);

        if (contested(influencePoints))
            return;

        Player      maxPlayer      = getMaxPlayer(influencePoints);
        Player      curPlayer      = getTowerPlayer(model, island);
        SchoolBoard maxPlayerBoard = maxPlayer.getSchoolBoard();
        SchoolBoard curPlayerBoard = curPlayer.getSchoolBoard();

        if (maxPlayerBoard != curPlayerBoard) {
            boolean notEnoughTower = maxPlayer.getSchoolBoard().getTowerCount() < island.getMultiplicity();

            island.setTowerColor(maxPlayerBoard.getTowerColor());
            maxPlayerBoard.decreaseTowerCount(notEnoughTower ? maxPlayer.getSchoolBoard().getTowerCount() : island.getMultiplicity());
            curPlayerBoard.increaseTowerCount(island.getMultiplicity());

            // If the current player has no more towers, call the end game trigger
            if (maxPlayerBoard.getTowerCount() == 0)
                endGame(maxPlayer);
        }
    }

    private void unifyIslands(GameModel model, int localIslandIndex) {
        Function<Integer, Integer> islandMod = n -> mod(model.getIslandsCount(), n);

        int previousIndex  = islandMod.apply(localIslandIndex - 1),
            successorIndex = islandMod.apply(localIslandIndex + 1);

        if (model.getIsland(previousIndex).getTowerColor() != null &&
            model.getIsland(previousIndex).getTowerColor().equals(model.getIsland(localIslandIndex).getTowerColor())) {
            mergeIslandsData(model.getIsland(previousIndex), model.getIsland(localIslandIndex));
            model.shiftIslands(localIslandIndex);
            successorIndex   = islandMod.apply(localIslandIndex);
            localIslandIndex = islandMod.apply(previousIndex);
            model.setMotherNaturePosition(islandMod.apply(model.getMotherNaturePosition() - 1));
        }

        if (model.getIsland(localIslandIndex).getTowerColor() != null  &&
            model.getIsland(localIslandIndex).getTowerColor().equals(model.getIsland(successorIndex).getTowerColor())) {
            mergeIslandsData(model.getIsland(localIslandIndex), model.getIsland(successorIndex));
            model.shiftIslands(successorIndex);
        }
    }

    private Map<Player, Integer> getIslandInfluences(ControllerData data, GameModel model, Island island) {
        Map<Player, Integer> mpi = model.getPlayersCount() == 4 ? getIslandInfluencesFourPlayers(data, model, island) : getIslandInfluencesTwoThreePlayers(data, model, island);

        if (data.getCharacterCardFlag(ControllerData.Flags.extraInfluenceFlag))
            mpi.replace(data.getCurrentPlayer(), mpi.get(data.getCurrentPlayer()) + 2);

        return mpi;
    }

    private Map<Player, Integer> getIslandInfluencesFourPlayers(ControllerData data, GameModel model, Island island) {
        // If there are four players, calculate the influence for each one and then fuse the teams
        Player[]             players          = model.getPlayers();
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
        for (Player player : model.getPlayers())
            influences.put(player, 0);

        // Iterate for each color on the island and add +1 influence to the player who controls the professor
        // Note that professorLocation is empty checked because it is null if the professor's not controlled by anyone
        for (Color color : Color.values()) {
            if (data.getCharacterCardFlag(ControllerData.Flags.excludeColorFlag) && data.getExcludedColor() == color)
                continue;

            if (island.getStudentCounters(color) > 0)
                model.getGlobalProfessorTable()
                     .getProfessorLocation(color)
                     .ifPresent(p -> influences.replace(p, influences.get(p) + 1));
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
            Arrays.stream(model.getPlayers())
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

    private boolean contested(Map<Player, Integer> players) {
        PriorityQueue<Integer> testQueue = new PriorityQueue<>(players.size(), Comparator.reverseOrder());

        players.forEach((p, i) -> testQueue.add(i));

        Integer head = testQueue.poll();

        return head != null && head.equals(testQueue.peek());
    }

    private Player getMaxPlayer(Map<Player, Integer> players) throws NoSuchElementException {
        return players
            // Get the set representing the map
            .entrySet()
            // Transform in into a stream to ease operations
            .stream()
            // Reduce the stream to an optional containing the desired (Player, Integer)
            .reduce((a, b) -> a.getValue() >= b.getValue() ? a : b)
            // Check whether a value is really present, just to be safe
            .orElseThrow()
            // Return the desired player
            .getKey();
    }

    private void mergeIslandsData(Island finalIsland, Island oldIsland) {
        finalIsland.setMultiplicity(finalIsland.getMultiplicity() + oldIsland.getMultiplicity());
        if (ControllerData.getInstance().getExpertMode())
            finalIsland.setNoEntryTileCount(finalIsland.getNoEntryTileCount() + oldIsland.getNoEntryTileCount());
        for (Color color : Color.values())
            finalIsland.setStudentCounters(color, finalIsland.getStudentCounters(color) + oldIsland.getStudentCounters(color));
    }

    private void endGame(Player winner) {
        this.winner = winner;
        ControllerData.getInstance().setWinTrigger(true);
    }

    private GameCommand createMap(GameModel model) {
        InfoMap map = new InfoMap();
        map.put(GameValues.ISLANDARRAY, model.getIslands());
        map.put(GameValues.PLAYERARRAY, model.getPlayers());
        return new GameCommandSendInfo(map);
    }

    private GameCommand createLightMap(GameModel model) {
        InfoMap map = new InfoMap();
        map.put(GameValues.ISLANDARRAY, model.getIslands());
        return new GameCommandSendInfo(map);
    }

    private List<Player> putWinners (Player winner) {
        List<Player> winners = new ArrayList<>();

        winners.add(winner);

        if (winner instanceof PlayerTeam t)
            winners.add(ControllerData.getInstance().getGameModel().getPlayer(t.getTeamMember()));

        else
            winners.add(ControllerData.getInstance().getGameModel().getPlayer(((PlayerTeamExpert) winner).getTeamMember()));

        return winners;
    }
}
