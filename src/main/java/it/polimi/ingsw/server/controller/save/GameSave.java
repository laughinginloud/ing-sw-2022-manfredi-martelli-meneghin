package it.polimi.ingsw.server.controller.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.common.utils.Constants;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.GameController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class containing static methods to manage saved games
 * @author Mattia Martelli
 */
public class GameSave {
    private static final Gson saveBuilder = Constants.jsonBuilder;

    private static final File SAVE_FOLDER = new File("./saves/");

    public static void saveGame(String fileName) throws IOException {
        Map<SaveData, Object> data = new HashMap<>();
        data.put(SaveData.ControllerData, ControllerData.getInstance());
        data.put(SaveData.GameState, GameController.saveGameState());

        File save = new File(SAVE_FOLDER, fileName);
        save.createNewFile();
        FileWriter saveWriter = new FileWriter(save);
        saveWriter.write(saveBuilder.toJson(data));
        saveWriter.close();
    }

    public static void loadGame(File save) throws IOException {
        Map<SaveData, Object> data = saveBuilder.fromJson(Files.readString(save.toPath()), Map.class);
        ControllerData.loadData((ControllerData) data.get(SaveData.ControllerData));
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
