package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.GameCommand;
import it.polimi.ingsw.server.controller.command.GameCommandSendInfo;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.*;
import java.util.function.Function;

import static it.polimi.ingsw.common.utils.ModuloNat.mod;

/**
 * State representing the operations on the islands: control, conquest and unification
 * @author Giovanni Manfredi, Mattia Martelli & Sebastiano Meneghin
 */
public final class GameStateComputeIsland implements GameStateActionPhase {

    // region Fields

    private       Player  winner;
    private final int     islandIndex;

    // endregion

    // region Constructors

    public GameStateComputeIsland() {
        this(ControllerData.getInstance().getGameModel().getMotherNaturePosition());
    }

    public GameStateComputeIsland(int islandIndex) {
        this.islandIndex = islandIndex;
             winner      = null;
    }

    // endregion

    // region DFA operations

    @Override
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

    @Override
    public void executeState() {
        ControllerData data          = ControllerData.getInstance();
        GameModel      model         = data.getGameModel();
        Island         currentIsland = model.getIsland(islandIndex);

        // If there is at least one no entry tile on this island, return it to the card and exit the current state
        if (data.getExpertMode() && currentIsland.getNoEntryTileCount() != null && currentIsland.getNoEntryTileCount() > 0) {
            currentIsland.setNoEntryTileCount(currentIsland.getNoEntryTileCount() - 1);

            // Find the Herbalist card, which uses the tiles, in the array of character cards
            CharacterCardNoEntry card = (CharacterCardNoEntry)
                Arrays.stream(data.getGameModel().getCharacterCards())
                    .reduce((c1, c2) -> c1.getCharacter() == Character.HERBALIST ? c1 : c2)
                    .orElseThrow();

            card.setNoEntryCount(card.getNoEntryCount() + 1);

            // Updates all the players about the variation on IslandArray, MN and CharacterCardArray
            // caused by the movement of motherNature on an Island with a NoEntryTile
            try {
                GameCommand foundNoEntryUpdate = createNoentryMap(model);

                // Updates all the players
                for (Player playerToUpdate : model.getPlayers())
                    data.getPlayerView(playerToUpdate).sendMessage(foundNoEntryUpdate);
            }

            catch (Exception e) {
                // Fatal error: print the stack trace to help debug
                e.printStackTrace();
            }

            return;
        }

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
            data.setWinTrigger();
    }

    // endregion

    // region Control, conquer and unify

    /**
     * Main routine for the control of an empty island
     * @param data   The ControllerData instance
     * @param model  The GameModel instance
     * @param island The island to possibly control
     */
    private void controlIsland(ControllerData data, GameModel model, Island island) {
        // Get each player's influence over the island
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

    /**
     * Main routine for the conquest of an already controlled island
     * @param data   The ControllerData instance
     * @param model  The GameModel instance
     * @param island The island to possibly conquest
     */
    private void conquerIsland(ControllerData data, GameModel model, Island island) {
        // Get each player's influence over the island
        Map<Player, Integer> influencePoints = getIslandInfluences(data, model, island);

        // Add the +1 influence to the player controlling the island iff:
        //      - the game is not in expert mode
        //      - if the game is in expert mode, if the "ignore towers" flag, triggered by a character card, is not active
        if (!data.getExpertMode() || !data.getCharacterCardFlag(ControllerData.Flags.ignoreTowersFlag))
            addTowerInfluence(influencePoints, model, island);

        // If multiple players have the same influence points over the island, nobody conquers it
        if (contested(influencePoints))
            return;

        // Find the data of both the current controlling player and the one that's trying to conquer
        Player maxPlayer = getMaxPlayer(influencePoints);
        Player curPlayer = getTowerPlayer(model, island);
        SchoolBoard maxPlayerBoard = maxPlayer.getSchoolBoard();
        SchoolBoard curPlayerBoard = curPlayer.getSchoolBoard();

        // If the player who's trying to conquest is the one that's already doing so, don't do anything
        if (maxPlayer.equals(curPlayer))
            return;

        // Flag to see if enough towers can be moved, to avoid decreasing a player's count past 0
        boolean notEnoughTower = maxPlayer.getSchoolBoard().getTowerCount() < island.getMultiplicity();

        // Change the island ownership
        island.setTowerColor(maxPlayerBoard.getTowerColor());
        maxPlayerBoard.decreaseTowerCount(notEnoughTower ? maxPlayer.getSchoolBoard().getTowerCount() : island.getMultiplicity());
        curPlayerBoard.increaseTowerCount(island.getMultiplicity());

        // If the current player has no more towers, call the end game trigger
        if (maxPlayerBoard.getTowerCount() == 0)
            endGame(maxPlayer);

    }

    /**
     * Main routine for the unification of multiple islands
     * @param model            The GameModel instance
     * @param localIslandIndex The index of the island to merge
     */
    private void unifyIslands(GameModel model, int localIslandIndex) {
        // A customized modulo to be used in this function
        Function<Integer, Integer> islandMod = n -> mod(model.getIslandsCount(), n);

        // Get the indexes of the neighbor islands
        int previousIndex  = islandMod.apply(localIslandIndex - 1),
            successorIndex = islandMod.apply(localIslandIndex + 1);

        // If the current and previous are both controlled by someone and can be merged, then do so
        if (model.getIsland(previousIndex).getTowerColor() != null &&
            model.getIsland(previousIndex).getTowerColor().equals(model.getIsland(localIslandIndex).getTowerColor())) {

            mergeIslandsData(model.getIsland(previousIndex), model.getIsland(localIslandIndex));
            model.shiftIslands(localIslandIndex);

            if (model.getMotherNaturePosition() >= localIslandIndex)
                model.setMotherNaturePosition(islandMod.apply(model.getMotherNaturePosition() - 1));

            // Correct the indexes after the merge
            successorIndex   = islandMod.apply(localIslandIndex);
            localIslandIndex = previousIndex == model.getIslandsCount() ? previousIndex - 1 : previousIndex;
        }

        // If the current and successor islands are both controlled by someone and can be merged, then do so
        if (model.getIsland(localIslandIndex).getTowerColor() != null  &&
            model.getIsland(localIslandIndex).getTowerColor().equals(model.getIsland(successorIndex).getTowerColor())) {

            mergeIslandsData(model.getIsland(localIslandIndex), model.getIsland(successorIndex));

            model.shiftIslands(successorIndex);

            if (model.getMotherNaturePosition() > localIslandIndex || model.getMotherNaturePosition() == model.getIslandsCount())
                model.setMotherNaturePosition(model.getMotherNaturePosition() - 1);
        }
    }

    // endregion

    // region Support methods

    /**
     * Get the current island influences
     * @param data   The ControllerData instance
     * @param model  The GameModel instance
     * @param island The island to evaluate
     * @return A map containing the influence for each player
     */
    private Map<Player, Integer> getIslandInfluences(ControllerData data, GameModel model, Island island) {
        Map<Player, Integer> mpi =
            model.getPlayersCount() == 4 ?
                getIslandInfluencesFourPlayers    (data, model, island):
                getIslandInfluencesTwoThreePlayers(data, model, island);

        if (data.getExpertMode() && data.getCharacterCardFlag(ControllerData.Flags.extraInfluenceFlag))
            mpi.replace(data.getCurrentPlayer(), mpi.get(data.getCurrentPlayer()) + 2);

        return mpi;
    }

    /**
     * Get the current island influences, adjusted for four players
     * @param data   The ControllerData instance
     * @param model  The GameModel instance
     * @param island The island to evaluate
     * @return A map containing the influence for each team
     */
    private Map<Player, Integer> getIslandInfluencesFourPlayers(ControllerData data, GameModel model, Island island) {
        // If there are four players, calculate the influence for each one and then fuse the teams
        Player[]             players          = model.getPlayers();
        Map<Player, Integer> influencesSingle = getIslandInfluencesTwoThreePlayers(data, model, island);

        // Create a map that will contain just two players: both teams' tower holders and sum the elements of the original map
        Map<Player, Integer> influencesTeams = new HashMap<>();
        influencesTeams.put(players[0], influencesSingle.get(players[0]) + influencesSingle.get(players[2]));
        influencesTeams.put(players[1], influencesSingle.get(players[1]) + influencesSingle.get(players[3]));

        return influencesTeams;
    }

    /**
     * Get the current island influences, adjusted for two and three players
     * @param data   The ControllerData instance
     * @param model  The GameModel instance
     * @param island The island to evaluate
     * @return A map containing the influence for each player
     */
    private Map<Player, Integer> getIslandInfluencesTwoThreePlayers(ControllerData data, GameModel model, Island island) {
        // Create and initialize a new map to represent the players' influences
        Map<Player, Integer> influences = new HashMap<>();
        for (Player player : model.getPlayers())
            influences.put(player, 0);

        // Iterate for each color on the island and add influence equal to the number of students to the player who controls the professor
        // Note that professorLocation is empty checked because it is null if the professor's not controlled by anyone
        for (Color color : Color.values()) {
            if (data.getExpertMode() && data.getCharacterCardFlag(ControllerData.Flags.excludeColorFlag) && data.getExcludedColor() == color)
                continue;

            // If there are students of the current color, add their influence to the player who's controlling the professor, if he exists
            if (island.getStudentCounters(color) > 0)
                model.getGlobalProfessorTable()
                     .getProfessorLocation(color)
                     .ifPresent(p -> influences.replace(p, influences.get(p) + island.getStudentCounters(color)));
        }

        return influences;
    }

    /**
     * Check if someone has any influence on the island
     * @param influences The calculated Map of the influences
     * @return <code>true</code> if at least one player has some influence, <code>false</code> otherwise
     */
    private boolean noInfluencePoints(Map<Player, Integer> influences) {
        // Cycle through all entries and return false if at least one of them as an influence of at least 1
        for (Map.Entry<Player, Integer> entry : influences.entrySet())
            if (entry.getValue() > 0)
                return false;

        // If the function hasn't returned yet, it means that all influences are 0
        return true;
    }

    /**
     * Add the tower influence to the controlling player
     * @param influences The Map of the influences
     * @param model      The GameModel instance
     * @param island     The current island
     */
    private void addTowerInfluence(Map<Player, Integer> influences, GameModel model, Island island) {
        Player towerPlayer = getTowerPlayer(model, island);

        // Add +1 to the player possessing the tower
        influences.replace(towerPlayer, influences.get(towerPlayer) + 1);
    }

    /**
     * Get the player currently controlling the island
     * @param model  The GameModel instance
     * @param island The current island
     * @return The desired player
     */
    private Player getTowerPlayer(GameModel model, Island island) {
        Player towerPlayer =
            // Transform the array of players into a stream to ease filtering
            Arrays.stream(model.getPlayers())
                // Reduce the stream to the player controlling the island
                .reduce((a, b) -> {
                    if (a.getSchoolBoard().getTowerColor() != island.getTowerColor())
                        return b;

                    if (a.getSchoolBoard().getTowerColor() == b.getSchoolBoard().getTowerColor())
                        return a.getSchoolBoard().getTowerCount() != 0 ? a : b;

                    return a;
                })
                // Unpack the value from the optional, throwing an exception if it's empty
                // Note: if the optional is empty it means that the model wasn't populated correctly
                .orElseThrow();

        // Check whether the player is really the correct one, to be safe
        // If that's not the case then the model has been wrongly initialized
        if (towerPlayer.getSchoolBoard().getTowerColor() != island.getTowerColor())
            throw new IllegalStateException();

        return towerPlayer;
    }

    /**
     * Check whether an island is currently contested between multiple players
     * @param players The Map of influences
     * @return <code>true</code> if it's contested, <code>false</code> otherwise
     */
    private boolean contested(Map<Player, Integer> players) {
        PriorityQueue<Integer> testQueue = new PriorityQueue<>(players.size(), Comparator.reverseOrder());
        players.forEach((p, i) -> testQueue.add(i));

        Integer head = testQueue.poll();

        return head != null && head.equals(testQueue.peek());
    }

    /**
     * Get the player with the most influence
     * @param players The Map of influences
     * @return The desired player
     */
    private Player getMaxPlayer(Map<Player, Integer> players) {
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

    /**
     * Merge the data of two islands, without morphing the array
     * @param finalIsland The island that will contain the merged data
     * @param oldIsland   The island that will be absorbed
     */
    private void mergeIslandsData(Island finalIsland, Island oldIsland) {
        finalIsland.setMultiplicity(finalIsland.getMultiplicity() + oldIsland.getMultiplicity());
        if (ControllerData.getInstance().getExpertMode())
            finalIsland.setNoEntryTileCount(finalIsland.getNoEntryTileCount() + oldIsland.getNoEntryTileCount());
        for (Color color : Color.values())
            finalIsland.setStudentCounters(color, finalIsland.getStudentCounters(color) + oldIsland.getStudentCounters(color));
    }

    /**
     * Signal the end of the game
     * @param winner The winner of the game
     */
    private void endGame(Player winner) {
        this.winner = winner;
        ControllerData.getInstance().setWinTrigger();
    }

    /**
     * Create the map that will be sent to the players
     * @param model The GameModel instance
     * @return The command to send
     */
    private GameCommand createMap(GameModel model) {
        InfoMap map = new InfoMap();
        map.put(GameValues.ISLANDARRAY,  model.getIslands());
        map.put(GameValues.PLAYERARRAY,  model.getPlayers());
        map.put(GameValues.MOTHERNATURE, model.getMotherNaturePosition());
        return new GameCommandSendInfo(map);
    }

    /**
     * Create a lighter map that will be sent to the players
     * @param model The GameModel instance
     * @return The command to send
     */
    private GameCommand createLightMap(GameModel model) {
        InfoMap map = new InfoMap();
        map.put(GameValues.ISLANDARRAY,  model.getIslands());
        map.put(GameValues.MOTHERNATURE, model.getMotherNaturePosition());
        return new GameCommandSendInfo(map);
    }

    /**
     * Create the map that will be sent to the players after an Island with a noEntryTile
     * on it has been encountered by the current player
     * @param model The GameModel instance
     * @return The command to send
     */
    private GameCommand createNoentryMap(GameModel model) {
        InfoMap map = new InfoMap();
        map.put(GameValues.ISLANDARRAY,        model.getIslands());
        map.put(GameValues.MOTHERNATURE,       model.getMotherNaturePosition());
        map.put(GameValues.CHARACTERCARDARRAY, model.getCharacterCards());
        return new GameCommandSendInfo(map);
    }

    /**
     * Add the winning team to the list
     * @param winner The winner to put in the List
     * @return A list containing the provided player and his teammate
     */
    private List<Player> putWinners (Player winner) {
        List<Player> winners = new ArrayList<>();

        winners.add(winner);

        if (winner instanceof PlayerTeam t)
            winners.add(ControllerData.getInstance().getGameModel().getPlayer(t.getTeamMemberID()));

        else
            winners.add(ControllerData.getInstance().getGameModel().getPlayer(((PlayerTeamExpert) winner).getTeamMemberID()));

        return winners;
    }

    // endregion

}
