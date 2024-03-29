package it.polimi.ingsw.server.controller.state;

/**
 * Interface for the bigger plan phase
 * @author Sebastiano Meneghin
 */
public sealed interface GameStatePlanPhase
    extends GameState
    permits GameStateFillClouds,
            GameStatePlayCard,
            GameStateSelectTurnOrder {}
