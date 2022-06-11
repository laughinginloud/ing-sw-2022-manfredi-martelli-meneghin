package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardManager;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardStrategy;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.*;

/**
 * State representing the student movements made by each player at the beginning of his ActionPhase
 * @author Sebastiano Meneghin
 */
public final class GameStateMoveStudents implements GameStateActionPhase {
    public GameState nextState() {
        return
            // Check if the game has been won already
            ControllerData.getInstance().checkWinTrigger() ?
                // If the game has ended, return a plain end phase, which will decide who won
                new GameStateEndGame() :
                // Otherwise, go to the next phase
                new GameStateMoveMotherNature();
    }

    public void executeState() {
        try {
            ControllerData data          = ControllerData.getInstance();
            Player         currentPlayer = updateCurrentPlayer();
            VirtualView    playerView    = data.getPlayerView(currentPlayer);
            boolean        expertMode    = data.getExpertMode();

            // If it's an expertMode game, set the flag hasPlayedCard to "false"
            if (expertMode)
                data.resetPlayedCard();

            // Sets the num of students the player is required to move, according to the number of players in the match
            int numOfMovements = switch (data.getNumOfPlayers()) {
                case 2, 4 -> numOfMovements = 3;
                case 3    -> numOfMovements = 4;
                default   -> throw new IllegalStateException();
            };

            // Notify all the not-current Players about the beginning of the currentPlayer ActionTurn
            Player[] players = data.getGameModel().getPlayer();
            for (Player playerToUpdate : players) {
                if (playerToUpdate.getUsername() != currentPlayer.getUsername()) {
                    VirtualView playerToUpdateView = data.getPlayerView(playerToUpdate);
                    GameCommand update             = new GameCommandNotifyBeginningTurn(currentPlayer.getUsername());
                    playerToUpdateView.sendMessage(update);
                }
            }

            for (int i = 0; i < numOfMovements && !data.checkWinTrigger(); i++)
                moveOneStudent(currentPlayer, playerView);
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
        ControllerData data = ControllerData.getInstance();

        // Creates a new player, that will be the updatedCurrentPlayer and gets the current player
        Player updatedCurrentPlayer  = null;
        Player previousCurrentPlayer = data.getCurrentPlayer();

        // If it is the first turn, set currentPlayer to the firstPlayer of the PlayersOrderArray
        if (previousCurrentPlayer == null)
            updatedCurrentPlayer = data.getPlayersOrder(0);

        // If not, set currentPlayer to the nextPlayer of the PlayersOrderArray
        else
            for (int i = 0; i < data.getNumOfPlayers(); i ++)
                if (previousCurrentPlayer.equals(data.getPlayersOrder(i))) {
                    updatedCurrentPlayer = data.getPlayersOrder(i + 1);
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
        ControllerData data            = ControllerData.getInstance();
        GameModel      model           = data.getGameModel();
        Player[]       players         = model.getPlayer();

        // Gets the entrance's students, the flag expertMode and sets useful flags to "false"
        Color[] movableStudents      = player.getSchoolBoard().getEntrance().getStudents();
        boolean expertMode           = data.getExpertMode();
        boolean canPlayCharacterCard = false;

        // Creates a map that will be filled by the fields to send to the player in order to let him move a student
        Map<GameValues, Object> moveStudentsInfo = setMoveStudentsInfo(player, movableStudents);

        // If the player hasn't played a card yet
        if (expertMode && !data.checkPlayedCard()) {
            // Get all the playableCharacterCard, according to previous CharacterCards utilization and to current player's coin pool
            CharacterCard[] playableCharacterCard = CharacterCardManager.getPlayableCharacterCard(player);

            // If there are CharacterCard playable by the current player, adds them to the moveStudentsInfo
            if (playableCharacterCard != null) {
                moveStudentsInfo.put(GameValues.CHARACTERCARDARRAY, playableCharacterCard);

                //Set the flag to true to change the GameCommandRequestAction's type during its initialization
                canPlayCharacterCard = true;
            }
        }

        // Send a RequestAction to the player is playing, requesting him to move students from the provided movableStudents to an Island or to a DiningRoomTable (if it is possible)
        // If the player is allowed to, send also the CharacterCard he could play
        GameCommand request = canPlayCharacterCard ?
            new GameCommandRequestAction(GameActions.MOVESTUDENTORPLAYCARD, moveStudentsInfo):
            new GameCommandRequestAction(GameActions.MOVESTUDENT, moveStudentsInfo);
        GameCommand response = playerView.sendRequest(request);

        // If the player responded with the students he wants to move
        if (response instanceof GameCommandMoveStudent c) {
            // Executes the Command and saves the returned values
            Object[] commandReturnInfo = (Object[]) c.executeCommand();
            boolean  toDiningRoom      = (boolean)  commandReturnInfo[0];
            Color    movedStudent      = (Color)    commandReturnInfo[1];

            // Create a Map where to store updatedFields in order to send them to the players
            InfoMap updateInfo = new InfoMap();

            if (!toDiningRoom)
                // Adds IslandArray value to the updateInfo Map that will be sent to all the players
                updateInfo.put(GameValues.ISLANDARRAY, model.getIslands());

            else {
                // If the current player is playing an ExpertMode Game it checks if the current player will receive
                // a Coin adding a student to a specific DiningRoomTable
                if (data.getExpertMode())
                    if (checkAndAddCoin(player, movedStudent)) {
                        int coinPool = model.getCoinPool();

                        // Adds globalCoinPool and playerArray values to the updateInfo Map that will be sent to all the players
                        updateInfo.put(GameValues.COINPOOL,    coinPool);
                        updateInfo.put(GameValues.PLAYERARRAY, model.getPlayer());
                    }

                // Checks if the current player has more students on a specific color's DiningRoomTable than the player
                // which is controlling the Professor of the same color. If necessary, it changes the ProfessorLocation
                GlobalProfessorTable gpt = model.getGlobalProfessorTable();
                Player playerControllingProfessor = gpt.getProfessorLocation(movedStudent);
                if (!player.equals(playerControllingProfessor) && checkProfessorMovement(player, playerControllingProfessor, movedStudent)) {
                    gpt.setProfessorLocation(movedStudent, player);
                    updateInfo.put(GameValues.GLOBALPROFESSORTABLE, gpt);
                }

                // Gets the DiningRoom of each player and save it a DiningRoomArray
                DiningRoom[] updatedDiningRooms = new DiningRoom[players.length];
                for (int i = 0; i < players.length; i++)
                    updatedDiningRooms[i] = players[i].getSchoolBoard().getDiningRoom();

                // Adds current player DiningRoom value to the updateInfo Map that will be sent to all the players
                updateInfo.put(GameValues.DININGROOMARRAY, updatedDiningRooms);
            }

            // Gets the entrance of each player and save it an entranceArray
            Entrance[] updatedEntrances = new Entrance[players.length];
            for (int i = 0; i < players.length; i++)
                updatedEntrances[i] = players[i].getSchoolBoard().getEntrance();

            // Adds current player Entrance value to the updateInfo Map that will be sent to all the players
            updateInfo.put(GameValues.ENTRANCEARRAY, updatedEntrances);

            // Sends to all the player the updateInfo Map containing all the GameModel's fields which need to be updated after the movement of the student
            for (Player playerToUpdate : players) {
                VirtualView playerToUpdateView = data.getPlayerView(playerToUpdate);

                GameCommand update = new GameCommandSendInfo(updateInfo);
                playerToUpdateView.sendMessage(update);
            }
        }

        // If the player decided to play a CharacterCard
        else if (response instanceof GameCommandPlayCharacterCard c) {
            // If the player already used a CharacterCard during this turn, throws an exception
            if(data.checkPlayedCard())
                throw new IllegalStateException("CharacterCard has been already used by the current player!");

            // Executes the command received
            Character chosenCharacter = (Character) c.executeCommand();

            // Gets the characterCardStrategy linked to the CharacterCard chosen by the player
            CharacterCardStrategy chosenCardStrategy = CharacterCardManager.getChosenCharacterCardStrategy(chosenCharacter);

            // Calls the selected characterCard's strategy effect
            chosenCardStrategy.playCharacterCard();

            // After the CharacterCard usage, it recalls the same function and makes the player choose a student once again
            if (!data.checkWinTrigger())
                moveOneStudent(player, playerView);
        }
    }

    /**
     * Calculates if the professor needs to be moved from the current position to a new one.
     * @param newPlayer the player that could become the new controllingPlayer
     * @param controllingPlayer the current player who's controlling the Professor
     * @param student the Color of the student Dining room it's checking
     * @return if the professorNeeds to be moved (true or false)
     */
    public static boolean checkProfessorMovement(Player newPlayer, Player controllingPlayer, Color student) {

        ControllerData data                        = ControllerData.getInstance();
        DiningRoom     diningRoomNewPlayer         = newPlayer.getSchoolBoard().getDiningRoom();
        DiningRoom     diningRoomControllingPlayer = controllingPlayer.getSchoolBoard().getDiningRoom();

        // If the flag 'equalStudentsFlag' is set the professor moves if the number of students in the dining room
        // is >= to the player is currently holding the professor. Otherwise, only if the number of students is >.
        return data.getCharacterCardFlag(ControllerData.Flags.equalStudentsFlag) ?
            diningRoomNewPlayer.getStudentCounters(student) >= diningRoomControllingPlayer.getStudentCounters(student) :
            diningRoomNewPlayer.getStudentCounters(student) >  diningRoomControllingPlayer.getStudentCounters(student) ;
    }

    /**
     * Calculates which DiningRoomTable have free-seats for new students. Then inserts in a Map the movableStudents and the diningRoomTableFreeSeatAvailable flag for each DiningRoomTableColor
     * @param player The player that has to move a student
     * @param movableStudents The students, currently in the player's entrance, that can be moved
     * @return A Map containing the movableStudents and an Array of boolean representing the diningRoomTableFreeSeatAvailable flags
     */
    private Map<GameValues, Object> setMoveStudentsInfo (Player player, Color[] movableStudents) {
        boolean[] diningRoomTableFreeSeatAvailable = new boolean[5];
        Map<GameValues, Object> moveStudentsInfo = new HashMap<>();

        // For each color checks if the correspondent DiningRoomTable has free seat(s)
        for (Color color : Color.values())
            if (player.getSchoolBoard().getDiningRoom().getStudentCounters(color) < 10)
                diningRoomTableFreeSeatAvailable[color.ordinal()] = true;

        moveStudentsInfo.put(GameValues.COLORARRAY, movableStudents);
        moveStudentsInfo.put(GameValues.BOOLARRAY, diningRoomTableFreeSeatAvailable);
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

