package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.state.GameState;

/**
 * Record that represents the serialized saved data
 * @author Mattia Martelli
 */
public record SaveData (ControllerData controllerData, GameState gameState) {}
