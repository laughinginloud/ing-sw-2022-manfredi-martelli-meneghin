package it.polimi.ingsw.common.model;


/**
 * Data type dedicated to storing information regarding the assistant cards
 * @author Mattia Martelli
 * @param cardValue the card value (an integer from 1 to 10)
 * @param movementPoints the movement points that Mother Nature can move (an integer from 1 to 5)
 */
public record AssistantCard(int cardValue, int movementPoints) {}
