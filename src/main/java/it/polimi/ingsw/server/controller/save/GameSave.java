package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.server.controller.ControllerData;

import java.util.HashMap;
import java.util.Map;

public class GameSave {
    public static void saveGame(String fileName) {
        Map<SaveData, Object> data = new HashMap<>();
        data.put(SaveData.GameModel, ControllerData.getInstance().getGameModel());
        data.put(SaveData.ControllerData, ControllerData.getInstance());
        //TODO
    }

    public static void loadGame(String fileName) {

    }
}
