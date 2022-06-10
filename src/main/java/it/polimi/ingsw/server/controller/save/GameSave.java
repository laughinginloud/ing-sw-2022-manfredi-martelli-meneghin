package it.polimi.ingsw.server.controller.save;

import com.google.gson.Gson;
import it.polimi.ingsw.common.json.Constants;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.GameController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
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
        SaveData data = new SaveData(ControllerData.getInstance(), GameController.saveGameState());

        File save = new File(SAVE_FOLDER, fileName);

        if (!save.createNewFile())
            if (!save.delete() || !save.createNewFile())
                throw new IOException("Save already exists and cannot be replaced");

        FileWriter saveWriter = new FileWriter(save);
        saveWriter.write(saveBuilder.toJson(data));
        saveWriter.close();
    }

    public static void loadGame(File save) throws IOException {
        SaveData data = saveBuilder.fromJson(Files.readString(save.toPath()), SaveData.class);
        GameController.loadGameState(data.gameState());
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
