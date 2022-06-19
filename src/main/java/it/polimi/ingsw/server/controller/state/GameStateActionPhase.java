package it.polimi.ingsw.server.controller.state;

/**
 * Interface for the bigger action phase
 * @author Mattia Martelli
 */
public sealed interface GameStateActionPhase
    extends GameState
    permits GameStateMoveStudents,
            GameStateMoveMotherNature,
            GameStateComputeIsland,
            GameStateChooseCloud,
            GameStateEndOfTurn {}
