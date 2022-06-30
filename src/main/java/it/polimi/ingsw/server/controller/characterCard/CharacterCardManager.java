package it.polimi.ingsw.server.controller.characterCard;

import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.server.controller.ControllerData;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing method that manage the CharacterCard utilization
 *
 * @author Sebastiano Meneghin
 */
public class CharacterCardManager {
    /**
     * Gets the card that are playable by the current player
     *
     * @param player The player who could play a CharacterCard
     * @return An array of CharacterCard containing the playable CharacterCards
     */
    public static CharacterCard[] getPlayableCharacterCard (Player player) {
        CharacterCard[]     presentCharacterCard  = ControllerData.getInstance().getGameModel().getCharacterCards();
        List<CharacterCard> playableCharacterCard = new ArrayList<>();

        if (player instanceof PlayerExpert p)
            // Select which CharacterCard can be played by the player
            for (CharacterCard characterCard : presentCharacterCard)
                if (p.getCoinCount() >= characterCard.getCost() && checkCharacterCardTokens(characterCard, p))
                    playableCharacterCard.add(characterCard);

        if (player instanceof PlayerTeamExpert p)
            // Select which CharacterCard can be played by the player
            for (CharacterCard characterCard : presentCharacterCard)
                if (p.getCoinCount() >= characterCard.getCost() && checkCharacterCardTokens(characterCard, p))
                    playableCharacterCard.add(characterCard);

        // If there are no playable CharacterCards
        if (playableCharacterCard.isEmpty())
            return null;

        // If there's at least one playable CharacterCard
        else
            return playableCharacterCard.toArray(CharacterCard[]::new);
    }

    /**
     * Checks if with the current game-board condition specific CharacterCard can be played by the current player
     *
     * @param characterCard The CharacterCard which need a game-board situation compatibility check
     * @param player        The player who could use the CharacterCard
     * @return A boolean representing the playability of the card
     */
    private static boolean checkCharacterCardTokens(CharacterCard characterCard, Player player) {
        Character character = characterCard.getCharacter();
        boolean   characterCardPlayability = false;

        switch (character) {
            // MONK, PRINCESS: Check if there is at least one student still on the CharacterCard, that can be then moved
            case MONK, PRINCESS -> {
                CharacterCardStudent characterCardStudent = (CharacterCardStudent) characterCard;
                Color[] studentColors = characterCardStudent.getStudents();
                for (Color studentColor : studentColors)
                    if (studentColor != null) {
                        characterCardPlayability = true;
                        break;
                    }
            }

            // FARMER: Check if there is at least on Professor which has been moved to a player's DiningRoom
            case FARMER -> {
                GlobalProfessorTable gpt = ControllerData.getInstance().getGameModel().getGlobalProfessorTable();
                for (Color color : Color.values())
                    if (gpt.getProfessorLocation(color).isPresent())
                        characterCardPlayability = true;
            }

            // HERBALIST: Check if there is at least on noEntryTile still on the card
            case HERBALIST -> {
                CharacterCardNoEntry characterCardNoEntry = (CharacterCardNoEntry) characterCard;
                if (characterCardNoEntry.getNoEntryCount() > 0)
                    characterCardPlayability = true;
            }

            // BARD: Check if there is at least one student in the current player DiningRoom
            case BARD -> {
                Color[] entranceStudents  = player.getSchoolBoard().getEntrance().getStudents();
                Color[] swappableStudents = BardStrategy.getSwappableStudents(player, entranceStudents);

                if (swappableStudents != null)
                    characterCardPlayability = true;
            }

            // THIEF: Check if there is at least one student in one of all players' DiningRoom
            case THIEF -> {
                Player[] players = ControllerData.getInstance().getPlayersOrder();
                DiningRoom[] diningRooms = new DiningRoom[players.length];

                // Gets the players' diningRoom
                for (int i = 0; i < players.length; i++)
                    diningRooms[i] = players[i].getSchoolBoard().getDiningRoom();

                // Check every diningRoom and set characterCardPlayability to true if a student is found
                for (DiningRoom diningRoom : diningRooms)
                    for (Color color : Color.values())
                        if (diningRoom.getStudentCounters(color) > 0) {
                            characterCardPlayability = true;
                            break;
                        }
            }

            // If this characterCar's utilization is always allowed
            default -> characterCardPlayability = true;
        }

        return characterCardPlayability;
    }

    /**
     * Gets the characterCardStrategy linked to the characterCard provided as parameter
     *
     * @param chosenCharacter The characterCard that needs to be linked to a characterCardStrategy
     * @return The characterCardStrategy linked to characterCard
     */
    public static CharacterCardStrategy getChosenCharacterCardStrategy(Character chosenCharacter) {
        // Gets the model's characterCards array and the controller's cardStrategies array, linked positionally in CharacterCard-CharacterCardStrategy
        CharacterCard[]         characterCards          = ControllerData.getInstance().getGameModel().getCharacterCards();
        CharacterCardStrategy[] characterCardStrategies = ControllerData.getInstance().getCardStrategies();
        CharacterCardStrategy   chosenCardStrategy      = null;

        // Finds the strategy linked to the characterCard chosen by the player
        for (int i = 0; i < characterCards.length; i ++)
            if ((characterCards[i].getCharacter()).equals(chosenCharacter))
                chosenCardStrategy = characterCardStrategies[i];

        if (chosenCardStrategy == null) {
            throw new IllegalStateException("A characterCard didn't accessed to his Strategy, it might not have been linked to");
        }

        // If there is a characterCardStrategy linked to the chosenCharacterCard, returns it
        else
            return chosenCardStrategy;
    }

    /**
     * Find the position of the CharacterCard into the model's characterCardArray
     *
     * @param character The character of which is searched the position
     * @return The position of the provided CharacterCard into the model's characterCardArray
     */
    public static int getCharacterCardPosition(Character character) {
        GameModel model = ControllerData.getInstance().getGameModel();
        CharacterCard[] characterCards = model.getCharacterCards();
        int position = 0;

        for (int i = 0; i < characterCards.length; i++)
            if (character == characterCards[i].getCharacter())
                position = i;

        return position;
    }
}
