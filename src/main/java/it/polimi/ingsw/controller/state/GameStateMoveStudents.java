package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.virtualView.VirtualView;

import java.util.*;

/**
 * State representing the student movements made by each player at the beginning of his ActionPhase
 * @author Sebastiano Meneghin
 */
public class GameStateMoveStudents implements GameStateActionPhase {
    public GameState nextState() { return new GameStateMoveMotherNature(); }

    public void executeState() {
        try {
            Player player = updateCurrentPlayer();
            VirtualView playerView = ControllerData.getInstance().getPlayerView(player);
            boolean expertMode = ControllerData.getInstance().getExpertMode();

            // If it's an expertMode game, set the flag hasPlayedCard to "false"
            if (expertMode)
                ControllerData.getInstance().resetPlayedCard();

            int numOfMovements = 0;
            // Sets the num of students the player is required to move, according to the number of players in the match
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
        Map<GameCommandValues, Object> moveStudentsInfo = setMoveStudentsInfo(player, movableStudents);
        Map<GameCommandValues, Object> updateInfo = new HashMap<>();
        boolean expertMode = ControllerData.getInstance().getExpertMode();
        boolean canPlayCharacterCard = false;

        // If the player hasn't played a card yet
        if (expertMode && !ControllerData.getInstance().checkPlayedCard()) {
            // Get all the playableCharacterCard, according to previous CharacterCards utilization and to current player's coin pool
            CharacterCard[] playableCharacterCard = getPlayableCharacterCard(player);

            // If there are CharacterCard playable by the current player, adds them to the moveStudentsInfo
            if (playableCharacterCard != null) {
                moveStudentsInfo.put(GameCommandValues.CHARACTERCARDARRAY, playableCharacterCard);

                //Set the flag to true to change the GameCommandRequestAction's type during its initialization
                canPlayCharacterCard = true;
            }
        }

        // Send a RequestAction to the player is playing, requesting him to move students from the provided movableStudents to an Island or to a DiningRoomTable (if it is possible)
        // If the player is allowed to, send also the CharacterCard he could play
        GameCommand request = canPlayCharacterCard ?
            new GameCommandRequestAction(GameCommandActions.MOVESTUDENTORPLAYCARD, moveStudentsInfo):
            new GameCommandRequestAction(GameCommandActions.MOVESTUDENT, moveStudentsInfo);
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

                GlobalProfessorTable gpt = ControllerData.getInstance().getGameModel().getGlobalProfessorTable();
                // Checks if the current player has more students on a specific color's DiningRoomTable than the player which is controlling the Professor of the same color. If necessary, it changes the ProfessorLocation
                Player playerControllingProfessor = gpt.getProfessorLocation(movedStudent);
                if (!player.equals(playerControllingProfessor) && canMoveProfessor(player, playerControllingProfessor, movedStudent)) {
                    gpt.setProfessorLocation(movedStudent, player);
                    updateInfo.put(GameCommandValues.GLOBALPROFESSORTABLE, gpt);
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
            // If the player already used a CharacterCard during this turn, throws an exception
            if(ControllerData.getInstance().checkPlayedCard())
                throw new IllegalStateException("CharacterCard has been already used by the current player!");

            // Executes the command received and set to "true" the flag hasPlayedCard stored in ControllerData
            Character calledCharacter = (Character) c.executeCommand();
            ControllerData.getInstance().setPlayedCard();

            /* TODO: [CharacterCard] Insert here the real name of the function which manage the CharacterCardUse */
            // CharacterCardUse.useCharacterCard(calledCharacter);

            // After the CharacterCard usage, it recalls the same function and makes the player choose a student once again
            moveOneStudent(player, playerView);
        }
    }

    public boolean canMoveProfessor(Player newPlayer, Player controllingPlayer, Color student) {
        // TODO: More readable?
        return ControllerData.getInstance().getCharacterCardFlag(ControllerData.Flags.equalStudentsFlag) ?
            newPlayer.getSchoolBoard().getDiningRoom().getStudentCounters(student) >= controllingPlayer.getSchoolBoard().getDiningRoom().getStudentCounters(student) :
            newPlayer.getSchoolBoard().getDiningRoom().getStudentCounters(student) >  controllingPlayer.getSchoolBoard().getDiningRoom().getStudentCounters(student) ;
    }

    /**
     * Calculates which DiningRoomTable have free-seats for new students. Then inserts in a Map the movableStudents and the diningRoomTableFreeSeatAvailable flag for each DiningRoomTableColor
     * @param player The player that has to move a student
     * @param movableStudents The students, currently in the player's entrance, that can be moved
     * @return A Map containing the movableStudents and an Array of boolean representing the diningRoomTableFreeSeatAvailable flags
     */
    private Map<GameCommandValues, Object> setMoveStudentsInfo (Player player, Color[] movableStudents) {
        Boolean[] diningRoomTableFreeSeatAvailable = {false, false, false, false, false};
        Map<GameCommandValues, Object> moveStudentsInfo = new HashMap<>();

        // For each color checks if the correspondent DiningRoomTable has free seat(s)
        for (Color color : Color.values())
            if (player.getSchoolBoard().getDiningRoom().getStudentCounters(color) < 10)
                diningRoomTableFreeSeatAvailable[color.ordinal()] = true;

        moveStudentsInfo.put(GameCommandValues.COLORARRAY,movableStudents);
        moveStudentsInfo.put(GameCommandValues.BOOLARRAY, diningRoomTableFreeSeatAvailable);
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

    /**
     * Gets the card that are playable by the current player
     * @param player The player who could play a CharacterCard
     * @return An array of CharacterCard containing the playable CharacterCards
     */
    private CharacterCard[] getPlayableCharacterCard(Player player) {
        PlayerExpert playerExpert = (PlayerExpert) player;
        CharacterCard[]     presentCharacterCard = ControllerData.getInstance().getGameModel().getCharacterCards();
        CharacterCard[]     characterCardsToSend;
        List<CharacterCard> playableCharacterCard = new ArrayList<>();

        // Select which CharacterCard can be played by the player
        for (CharacterCard characterCard : presentCharacterCard)
            if (playerExpert.getCoinCount() >= characterCard.getCost() && checkCharacterCardTokens(characterCard, playerExpert))
                playableCharacterCard.add(characterCard);

        // If there are no playable CharacterCards
        if (playableCharacterCard.isEmpty())
            return null;

        // If there's at least one playable CharacterCard
        else {
            characterCardsToSend = new CharacterCard[playableCharacterCard.size()];
            for (int i = 0; i < playableCharacterCard.size(); i++)
                characterCardsToSend[i] = playableCharacterCard.get(i);

            return characterCardsToSend;
        }
    }

    /**
     * Checks if with the current game-board condition specific CharacterCard can be played by the current player
     * @param characterCard The CharacterCard which need a game-board situation compatibility check
     * @param player The player who could use the CharacterCard
     * @return A boolean representing the playability of the card
     */
    private boolean checkCharacterCardTokens(CharacterCard characterCard, Player player) {
        Character character = characterCard.getCharacter();
        boolean   characterCardPlayability = false;

        // MONK, BARD: Check if there is at least one student still on the CharacterCard, that can be then moved
        if (character == Character.MONK || character == Character.PRINCESS) {
            CharacterCardStudent characterCardStudent = (CharacterCardStudent) characterCard;
            Color[] studentColors = characterCardStudent.getStudents();

            for (Color studentColor : studentColors)
                if (studentColor != null) {
                    characterCardPlayability = true;
                    break;
                }
        }

        // FARMER: Check if there is at least on Professor which has been moved to a player's DiningRoom
        else if (character == Character.FARMER) {
            GlobalProfessorTable gpt = ControllerData.getInstance().getGameModel().getGlobalProfessorTable();

            for (Color color : Color.values())
                if (gpt.getProfessorLocation(color) != null)
                    characterCardPlayability = true;
        }

        // HERBALIST: Check if there is at least on noEntryTile still on the card
        else if (character == Character.HERBALIST) {
            CharacterCardNoEntry characterCardNoEntry = (CharacterCardNoEntry) characterCard;
            if (characterCardNoEntry.getNoEntryCount() > 0)
                characterCardPlayability = true;
        }

        // BARD: Check if there is at least one student in the current player DiningRoom
        else if (character == Character.BARD) {
            DiningRoom currentPlayerDiningRoom = player.getSchoolBoard().getDiningRoom();
            for (Color color : Color.values())
                if (currentPlayerDiningRoom.getStudentCounters(color) > 0)
                    characterCardPlayability = true;
        }

        // THIEF: Check if there is at least one student in one of all players' DiningRoom
        else if (character == Character.THIEF) {
            Player[]     players     = ControllerData.getInstance().getPlayersOrder();
            DiningRoom[] diningRooms = new DiningRoom[players.length];

            // Gets the players' diningRoom
            for (int i = 0; i < players.length; i++)
                diningRooms[i] = players[i].getSchoolBoard().getDiningRoom();

            // Check every diningRoom and set characterCardPlayability to true if a student is found
            for (DiningRoom diningRoom : diningRooms)
                for (Color color : Color.values())
                    if (diningRoom.getStudentCounters(color) > 0)
                        characterCardPlayability = true;
        }

        // If this characterCar's utilization is always allowed
        else
            characterCardPlayability = true;

        return characterCardPlayability;
    }
}

