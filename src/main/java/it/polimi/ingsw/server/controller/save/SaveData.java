package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.state.GameState;

/**
 * Record that represents the serialized saved data
 * @param controllerData The ControllerData instance
 * @param gameState      The next state of the DFA, to be executed at the start of the loaded game
 * @author Mattia Martelli
 */
public record SaveData (ControllerData controllerData, GameState gameState) {}
