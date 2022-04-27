package it.polimi.ingsw.model;

import java.util.Arrays;

/**
 * Class representing a player
 * @author Mattia Martelli
 */
public class Player {
    private       AssistantCard[] assistantDeck;
    private       AssistantCard   lastPlayedCard;
    private final int             playerID;
    private final Wizard          playerWizard;
    private final SchoolBoard     schoolBoard;
    private final String          username;

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

        for (int i = 0; i < 10; ++i) {
            assistantDeck[i] = new AssistantCard(i + 1, (i / 2) + 1);
        }
    }

    /**
     * Return the selected card, deleting it from the deck in the process
     * @param index The index of the card to be removed
     * @return A record representing the card
     */
    public AssistantCard getAssistantCard(int index) throws IllegalArgumentException {
        if (index < 0 || index >= assistantDeck.length)
            throw new IllegalArgumentException("Index out of bounds");

        AssistantCard tempCard = assistantDeck[index];

        // Shifts the array and then shrinks it
        System.arraycopy(assistantDeck, index + 1, assistantDeck, index, assistantDeck.length - index - 1);
        assistantDeck = Arrays.copyOf(assistantDeck, assistantDeck.length - 1);

        return tempCard;
    }

    /**
     * Returns the entire assistant deck
     * @return An array containing the deck
     */
    public AssistantCard[] getAssistantDeck() {
        return Arrays.copyOf(assistantDeck, assistantDeck.length);
    }

    /**
     * Returns the player's ID
     * @return An integer containing the ID
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Returns the player's chosen wizard
     * @return An enumeration constant representing the choice
     */
    public Wizard getPlayerWizard() {
        return playerWizard;
    }

    /**
     * Returns the schoolBoard associated with the player
     * @return A pointer to the desired schoolBoard
     */
    public SchoolBoard getSchoolBoard() {
        return schoolBoard;
    }

    /**
     * Returns the player's username
     * @return A string containing the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the last played card
     * @param lastPlayedCard The card to be set
     */
    public void setLastPlayedCard(AssistantCard lastPlayedCard) throws IllegalArgumentException {
        // Check whether the first field is in the valid range and the second field follows the correct pattern
        if (lastPlayedCard.cardValue() < 0 || lastPlayedCard.cardValue() > 10 || lastPlayedCard.movementPoints() != (((lastPlayedCard.cardValue() - 1) / 2) + 1))
            throw new IllegalArgumentException("Invalid assistant card");

        this.lastPlayedCard = lastPlayedCard;
    }

    /**
     * Get the last played card
     * @return Last played card
     */
    public AssistantCard getLastPlayedCard() {
        return lastPlayedCard;
    }
}
