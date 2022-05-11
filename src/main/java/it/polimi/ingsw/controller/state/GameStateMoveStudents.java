package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.virtualView.VirtualView;

import java.util.HashMap;
import java.util.Map;

/**
 * State representing the student movements made by each player at the beginning of his ActionPhase
 * @author Sebastiano Meneghin
 */
public class GameStateMoveStudents implements GameStateActionPhase{
    public GameState nextState() { return new GameStateMoveMotherNature(); }

    public void executeState() {
        try {
            Player player = updateCurrentPlayer();
            VirtualView playerView = ControllerData.getInstance().getPlayerView(player);
            int numOfMovements = 0;

            switch (ControllerData.getInstance().getNumOfPlayers()) {
                case 2, 4 -> numOfMovements = 3;
                case 3 -> numOfMovements = 4;
            }

            for (int i = 0; i < numOfMovements; i++)
                moveOneStudent(player, playerView);
        }

        catch (Exception e){
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    /**
     * Determines and set the currentPlayer at the beginning of his ActionPhase
     * @return A Player representing the current player of this ActionPhase turn
     */
    private Player updateCurrentPlayer() {
        Player updatedCurrentPlayer = null;
        Player previousCurrentPlayer = ControllerData.getInstance().getCurrentPlayer();

        // If it is the first turn, set currentPlayer to the firstPlayer of the PlayersOrderArray
        if (previousCurrentPlayer == null)
            updatedCurrentPlayer = ControllerData.getInstance().getPlayersOrder(0);

        // If not, set currentPlayer to the nextPlayer of the PlayersOrderArray
        else
            for (int i = 0; i < ControllerData.getInstance().getNumOfPlayers(); i ++)
                if (previousCurrentPlayer.equals(ControllerData.getInstance().getPlayersOrder(i))) {
                    updatedCurrentPlayer = ControllerData.getInstance().getPlayersOrder(i + 1);
                    break;
                }

        return updatedCurrentPlayer;
    }

    /**
     * Asks the current player which player to move. Once received it adds the student to the right Island/DiningRoomTable and check if studentMovement related GameModel fields need to be updated
     * @param player Current Player that has to move a student
     * @param playerView VirtualView of the current player
     * @throws Exception Can be thrown by GameCommands or by the Network
     */
    private void moveOneStudent(Player player, VirtualView playerView) throws Exception {
        Color[] movableStudents = player.getSchoolBoard().getEntrance().getStudents();
        Map<Color[], Boolean[]> moveStudentsInfo = setMoveStudentsInfo(player, movableStudents);
        Map<GameCommandValues, Object> updateInfo = new HashMap<>();

        // Send a RequestAction to the player is playing, requesting him to move students from the provided movableStudents to an Island or to a DiningRoomTable (if it is possible)
        GameCommand request = new GameCommandRequestAction(GameCommandActions.MOVESTUDENT, moveStudentsInfo);
        GameCommand response = playerView.sendRequest(request);

        // If the player responded with the students he wants to move
        if (response instanceof GameCommandMoveStudent c) {
            // Executes the Command and saves the returned values
            Object[] commandReturnInfo = (Object[]) c.executeCommand();
            boolean toDiningRoom = (boolean) commandReturnInfo[0];
            Color   movedStudent = (Color) commandReturnInfo[1];

            if (!toDiningRoom)
                // Adds IslandArray value to the updateInfo Map that will be sent to all the players
                updateInfo.put(GameCommandValues.ISLANDARRAY, ControllerData.getInstance().getGameModel().getIslands());

            else {
                // If the current player is playing an ExpertMode Game it checks if the current player will receive a Coin adding a student to a specific DiningRoomTable
                if (ControllerData.getInstance().getExpertMode())
                    if (checkAndAddCoin(player, movedStudent)) {
                        // Adds globalCoinPool and playerArray values to the updateInfo Map that will be sent to all the players
                        updateInfo.put(GameCommandValues.COINPOOL, ControllerData.getInstance().getGameModel().getCoinPool());
                        updateInfo.put(GameCommandValues.PLAYERARRAY, ControllerData.getInstance().getGameModel().getPlayer());
                    }

                // Checks if the current player has more student on a specific color's DiningRoomTable than the player which is controlling the Professor of the same color. If necessary, it changes the ProfessorLocation
                Player playerControllingProfessor = ControllerData.getInstance().getGameModel().getGlobalProfessorTable().getProfessorLocation(movedStudent);
                if (!player.equals(playerControllingProfessor) && player.getSchoolBoard().getDiningRoom().getStudentCounters(movedStudent) > playerControllingProfessor.getSchoolBoard().getDiningRoom().getStudentCounters(movedStudent)) {
                    ControllerData.getInstance().getGameModel().getGlobalProfessorTable().setProfessorLocation(movedStudent, player);
                    updateInfo.put(GameCommandValues.GLOBALPROFESSORTABLE, ControllerData.getInstance().getGameModel().getGlobalProfessorTable());
                }

                // Adds current player DiningRoom value to the updateInfo Map that will be sent to all the players
                updateInfo.put(GameCommandValues.DININGROOM, player.getSchoolBoard().getDiningRoom());
            }

            // Adds current player Entrance value to the updateInfo Map that will be sent to all the players
            updateInfo.put(GameCommandValues.ENTRANCE, player.getSchoolBoard().getEntrance());

            // Sends to all the player the updateInfo Map containing all the GameModel's fields which need to be updated after the movement of the student
            for (Player playerToUpdate : ControllerData.getInstance().getPlayersOrder()) {
                VirtualView playerToUpdateView = ControllerData.getInstance().getPlayerView(playerToUpdate);

                GameCommand update = new GameCommandSendInfo(updateInfo);
                playerToUpdateView.sendMessage(update);
            }
        }

        // If the player decided to play a CharacterCard
        else if (response instanceof GameCommandPlayCharacterCard c) {
            Character calledCharacter = (Character) c.executeCommand();

            /* TODO: [CharacterCard] Insert here the real name of the function which manage the CharacterCardUse */
            // CharacterCardUse.useCharacterCard(calledCharacter);

            // After the CharacterCard usage, it recalls the same function and makes the player choose a student once again
            moveOneStudent(player, playerView);
        }
    }

    /**
     * Calculates which DiningRoomTable have free-seats for new students. Then inserts in a Map the movableStudents and the diningRoomTableFreeSeatAvailable flag for each DiningRoomTableColor
     * @param player The player that has to move a student
     * @param movableStudents The students, currently in the player's entrance, that can be moved
     * @return A Map containing the movableStudents and an Array of boolean representing the diningRoomTableFreeSeatAvailable flags
     */
    private Map<Color[], Boolean[]> setMoveStudentsInfo (Player player, Color[] movableStudents) {
        Boolean[] diningRoomTableFreeSeatAvailable = {false, false, false, false, false};
        Map<Color[], Boolean[]> moveStudentsInfo = new HashMap<>();

        // For each color checks if the correspondent DiningRoomTable has free seat(s)
        for (Color color : Color.values())
            if (player.getSchoolBoard().getDiningRoom().getStudentCounters(color) < 10)
                diningRoomTableFreeSeatAvailable[color.ordinal()] = true;

        moveStudentsInfo.put(movableStudents, diningRoomTableFreeSeatAvailable);
        return moveStudentsInfo;
    }

    /**
     * #ExpertModeOnly - Checks if the current player will gain a Coin after the movement of the students on a DiningRoomTable
     * @param player The player who moved the student
     * @param movedStudent Color representing the color of the moved student
     * @return Boolean representing the need to update the gameBoard (true if the Coin has been added to the player's coinCount)
     */
    private boolean checkAndAddCoin(Player player, Color movedStudent) {
        // If a student has been added in to the 3rd, 6th or 9th seat on a table and (global)coinPool > 0, adds a coin to the player coinCount
        if (player.getSchoolBoard().getDiningRoom().getStudentCounters(movedStudent) % 3 == 0 && ControllerData.getInstance().getGameModel().getCoinPool() > 0) {
            if (player instanceof PlayerExpert moverExpert)
                moverExpert.setCoinCount(moverExpert.getCoinCount() + 1);

            else if (player instanceof PlayerTeamExpert moverTeamExpert)
                moverTeamExpert.setCoinCount(moverTeamExpert.getCoinCount() + 1);

            ControllerData.getInstance().getGameModel().decreaseCoinPool(1);
            return true;
        }

        return false;
    }
}

