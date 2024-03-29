package it.polimi.ingsw.common.model;

import java.util.Arrays;

import static it.polimi.ingsw.common.utils.Methods.copyOf;

/**
 * Class representing a player
 * @author Mattia Martelli
 */
public sealed class Player permits PlayerExpert, PlayerTeam, PlayerTeamExpert {
    private       AssistantCard[] assistantDeck;
    private       AssistantCard   lastPlayedCard;
    private final int             playerID;
    private final Wizard          playerWizard;
    private       SchoolBoard     schoolBoard;
    private final String          username;

    /**
     * Constructor of the class 'Player'
     * @param playerID the player ID (positive integer between 0 and 3)
     * @param username the username of the player
     * @param playerWizard the Wizard associated with the player (not null)
     * @param schoolBoard the schoolBoard of the player (not null)
     */
    public Player(int playerID, String username, Wizard playerWizard, SchoolBoard schoolBoard) {
        assistantDeckSetup();
        this.playerID     = playerID;
        this.username     = username;
        this.playerWizard = playerWizard;
        this.schoolBoard  = schoolBoard;
        lastPlayedCard    = null;
    }

    /**
     * Creates the assistant deck and populates it with all the cards
     */
    private void assistantDeckSetup() {
        assistantDeck = new AssistantCard[10];

        for (int i = 0; i < 10; ++i)
            assistantDeck[i] = new AssistantCard(i + 1, (i / 2) + 1);
    }

    // region Getter

    /**
     * Gets the selected card, deleting it from the deck in the process
     * @param index The index of the card to be removed (positive integer between 0 and 9)
     * @return A record representing the card
     */
    public AssistantCard getAssistantCard(int index) {
        assert index >= 0 && index < assistantDeck.length: "Index out of bounds, since index is:" + index;

        AssistantCard tempCard = assistantDeck[index];

        // Shifts the array and then shrinks it
        System.arraycopy(assistantDeck, index + 1, assistantDeck, index, assistantDeck.length - index - 1);
        assistantDeck = Arrays.copyOf(assistantDeck, assistantDeck.length - 1);

        return tempCard;
    }

    /**
     * Gets the entire assistant deck
     * @return An array containing the deck
     */
    public AssistantCard[] getAssistantDeck() {
        return copyOf(assistantDeck);
    }

    /**
     * Gets the player's ID
     * @return An integer containing the ID
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Gets the player's chosen wizard
     * @return An enumeration constant representing the choice
     */
    public Wizard getPlayerWizard() {
        return playerWizard;
    }

    /**
     * Gets the schoolBoard associated with the player
     * @return A pointer to the desired schoolBoard
     */
    public SchoolBoard getSchoolBoard() {
        return schoolBoard;
    }

    /**
     * Gets the player's username
     * @return A string containing the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the last played card
     * @return Last played card
     */
    public AssistantCard getLastPlayedCard() {
        return lastPlayedCard;
    }

    // endregion Getter

    // region Setter

    /**
     * Sets the last played card
     * @param lastPlayedCard The card to be set (not null)
     */
    public void setLastPlayedCard(AssistantCard lastPlayedCard) {
        assert lastPlayedCard.cardValue() >= 1 && lastPlayedCard.cardValue() <= 10 && lastPlayedCard.movementPoints() == (((lastPlayedCard.cardValue() - 1) / 2) + 1): "Invalid assistant card";

        this.lastPlayedCard = lastPlayedCard;
    }

    /**
     * Set the school board
     * @param schoolBoard The pointer to the school board
     */
    public void setSchoolBoard(SchoolBoard schoolBoard) {
        this.schoolBoard = schoolBoard;
    }

    // endregion Setter
}
