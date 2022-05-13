package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing method that manage the CharacterCard utilization
 * @author Sebastiano Meneghin
 */
public class CharacterCardManager {
    /**
     * Gets the card that are playable by the current player
     * @param player The player who could play a CharacterCard
     * @return An array of CharacterCard containing the playable CharacterCards
     */
    public static CharacterCard[] getPlayableCharacterCard (Player player) {
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
    private static boolean checkCharacterCardTokens(CharacterCard characterCard, Player player) {
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