package it.polimi.ingsw.common.viewRecord;

/**
 * Record representing the game's rules
 * @param numOfPlayers The number of players the game will have
 * @param expertMode   <code>true</code> if the game will be played with expert ruels, <code>false</code> otherwise
 * @author Mattia Martelli
 */
public record GameRules(int numOfPlayers, boolean expertMode) {}
