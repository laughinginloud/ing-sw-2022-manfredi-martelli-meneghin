package it.polimi.ingsw.server.controller.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.server.controller.ControllerData;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameSave {
    private static final Gson saveBuilder = new GsonBuilder().setPrettyPrinting().create();

    private static final File SAVE_FOLDER = new File("./saves/");

    public static void saveGame(String fileName) {
        Map<SaveData, Object> data = new HashMap<>();
        data.put(SaveData.GameModel, ControllerData.getInstance().getGameModel());
        data.put(SaveData.ControllerData, ControllerData.getInstance());
        //TODO
    }

    public static void loadGame(File save) {

    }

    public static Optional<File> findSavedGame(String username) {
        for (File savedGame : SAVE_FOLDER.listFiles())
            if (savedGame.getName().contains(username))
                return Optional.of(savedGame);

        return Optional.empty();
    }

    public static boolean checkFolderPermissions() {
        return SAVE_FOLDER.exists() ?
            SAVE_FOLDER.canRead() && SAVE_FOLDER.canWrite() :
            SAVE_FOLDER.mkdir();
    }
}
