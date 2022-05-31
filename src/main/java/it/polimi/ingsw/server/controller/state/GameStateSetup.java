package it.polimi.ingsw.server.controller.state;

/**
 * Interface fo the bigger setup phase
 * @author Mattia Martelli
 */
public sealed interface GameStateSetup
    extends GameState
    permits GameStateModelInitialization,
            GameStatePlaceTokens,
            GameStateExpertInitialization {}
